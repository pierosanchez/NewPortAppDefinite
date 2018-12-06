package com.newport.app.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.newport.app.NewPortApplication;
import com.newport.app.ui.eventsgallery.PhotoGalleryActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFilesFromServer {

    private static final String TAG = "Download Task";
    private Context context;
    private String downloadUrl = "", downloadFileName = "";

    public DownloadFilesFromServer(Context context, String downloadUrl) {
        this.context = context;
        this.downloadUrl = downloadUrl;

        downloadFileName = "Boleta_" + PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()) + ".pdf";

        Log.e(TAG, downloadFileName);

        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Iniciando Descarga...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    Toast.makeText(context, "El archivo se guardo en la carpeta de Descargas", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Ocurrio un error durante la descarga", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Download Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);                                     //Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();     //Open Url Connection
                c.setRequestMethod("GET");                                          //Set Request Method to "GET" since we are grtting data
                c.connect();                                                        //connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());
                }

                //Get File if SD card is present
                if (Helper.isSDCardPresent()) {
                    apkStorage = new File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                } else {
                    //Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
                    apkStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()/* + "/" + Constant.DOWNLOADPDFDIRECTORY*/);
                }

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                    Log.e(TAG, apkStorage.getAbsolutePath());
                }

                outputFile = new File(apkStorage, downloadFileName);                //Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);            //Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();                                //Get InputStream for connection

                byte[] buffer = new byte[1024];                                     //Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);                                 //Write new file
                }

                MediaScannerConnection.scanFile(context, new String[] { outputFile.getPath() }, new String[] { "application/pdf" }, null);

                Log.e(TAG, apkStorage.getAbsolutePath());

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {
                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }
            return null;
        }
    }
}
