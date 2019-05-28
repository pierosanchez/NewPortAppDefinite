package com.newport.app.ui.boletaspago;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import android.Manifest;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.util.DownloadFilesFromServer;
import com.newport.app.util.PreferencesHeper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import static com.newport.app.util.Constant.REQUEST_GROUP_PERMISSIONS;

public class BoletasPagoActivity extends BaseActivity implements BoletasPagoContract.View, View.OnClickListener {

    private BoletasPagoPresenter boletasPagoPresenter;

    private PDFView pdfViewer;
    private String pdfUrl;
    private RelativeLayout layProgress;
    private ProgressDialog pb;
    private Button btnDownloadVoucherPDF;


    private int statusSeeBoletaPago;

    private LinearLayout llErrorBoletaPagoNotAccess;
    private ImageView imvBoletaPagoAccess;

    // Google Analytics variables
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletas_pago);
        init();
    }

    private void init() {
        pdfViewer = findViewById(R.id.pdfViewer);
        layProgress = findViewById(R.id.layProgress);
        llErrorBoletaPagoNotAccess = findViewById(R.id.llErrorBoletaPagoNotAccess);
        btnDownloadVoucherPDF = findViewById(R.id.btnDownloadVoucherPDF);
        imvBoletaPagoAccess = findViewById(R.id.imvBoletaPagoAccess);

        Intent intent = getIntent();
        statusSeeBoletaPago = intent.getIntExtra("statusSeeBoletaPago", 0);

        boletasPagoPresenter = new BoletasPagoPresenter();
        boletasPagoPresenter.attachedView(this);

        mTracker = ((NewPortApplication) getApplication()).getTracker(NewPortApplication.TrackerName.APP_TRACKER);
        mTracker.setScreenName("BoletasPagoActivity");

        if (statusSeeBoletaPago == 1) {
            imvBoletaPagoAccess.setImageResource(R.drawable.boletas_pago_error_newport_app);
            llErrorBoletaPagoNotAccess.setVisibility(View.VISIBLE);
        } else {
            boletasPagoPresenter.getBoletasPago();
        }

        btnDownloadVoucherPDF.setOnClickListener(this);
    }

    @Override
    public void showBoletasPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        pdfUrl = boletasPagoResponse.getResponse();
        //getUrlWithNewMonth(pdfUrl);
        new BoletasPagoActivity.RetrievePDFStream().execute(pdfUrl);
    }

    @Override
    public void showBoletasPagoError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDownloadVoucherPDF) {
            ActivityCompat.requestPermissions(BoletasPagoActivity.this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 100);

            int permissionCheck = ContextCompat.checkSelfPermission(BoletasPagoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                new DownloadFilesFromServer(NewPortApplication.getAppContext().getApplicationContext(), pdfUrl);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GROUP_PERMISSIONS) {
            String result = "";
            int i = 0;
            int numberPermissions = permissions.length;
            for (String perm : permissions) {
                String status;
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    status = "GRANTED";
                    numberPermissions--;
                } else {
                    status = "DENIED";
                }
                result = result + "\n" + perm + " : " + status;
                i++;
            }

            if (numberPermissions == 0) {
                new DownloadFilesFromServer(NewPortApplication.getAppContext().getApplicationContext(), pdfUrl);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permisos");
                builder.setMessage(result);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new DownloadFilesFromServer(NewPortApplication.getAppContext().getApplicationContext(), pdfUrl);
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(BoletasPagoActivity.this, "Cargando...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream == null) {
                //Toast.makeText(BoletasPagoActivity.this, "No se encontró su boleta de pago", Toast.LENGTH_SHORT).show();
                new BoletasPagoActivity.RetrievePDFStreamOptional().execute(pdfUrl);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Boleta de Pago: " + PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))
                        .setAction("No se mostró la boleta de Pago")
                        .setLabel("1er intento not showed")
                        .build()
                );
            }
            pdfViewer.fromStream(inputStream).swipeHorizontal(true).spacing(5).load();
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Boleta de Pago: " + PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))
                    .setAction("Se Mostró la boleta de Pago")
                    .setLabel("1er intento showed")
                    .build()
            );
        }
    }

    class RetrievePDFStreamOptional extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            /* validación nueva URL de la boleta de pago */
            String boleta = "";
            String boletaMonth = "";

            String[] monthArray = strings[0].split("_");

            String[] boletaArray = strings[0].split("B");
            boleta = boletaArray[1];

            String[] boletaMonthArray = boleta.split("_");
            boletaMonth = boletaMonthArray[2];

            int newMonth = Integer.parseInt(boletaMonth) - 1;

            if (String.valueOf(newMonth).length() == 1) {
                String boletaYear = boletaMonthArray[1];

                if (String.valueOf(newMonth).equals("0")) {
                    int newYear = Integer.parseInt(boletaYear) - 1;
                    monthArray[3] = String.valueOf(newYear);
                    monthArray[4] = "12";
                } else {
                    monthArray[4] = "0" + newMonth;
                }
            } else {
                monthArray[4] = String.valueOf(newMonth);
            }

            String newUrl = monthArray[0] + "_" + monthArray[1] + "_" + monthArray[2] + "_" + monthArray[3] + "_" + monthArray[4] + "_" + monthArray[5];
            /* Fin de la validación */

            try {
                URL url = new URL(newUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream == null) {
                Toast.makeText(BoletasPagoActivity.this, "No se encontró su boleta de pago", Toast.LENGTH_SHORT).show();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Boleta de Pago: " + PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))
                        .setAction("No se mostró la boleta de Pago")
                        .setLabel("2do intento not showed")
                        .build()
                );
            }
            pdfViewer.fromStream(inputStream).swipeHorizontal(true).spacing(5).load();
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Boleta de Pago: " + PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))
                    .setAction("Se Mostró la boleta de Pago")
                    .setLabel("2do intento showed")
                    .build()
            );
        }
    }
}
