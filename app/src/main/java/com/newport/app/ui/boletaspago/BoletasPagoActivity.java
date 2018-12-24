package com.newport.app.ui.boletaspago;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import android.Manifest;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.util.DownloadFilesFromServer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletas_pago);
        init();
    }

    private void init() {
        pdfViewer = (PDFView) findViewById(R.id.pdfViewer);

        layProgress = (RelativeLayout) findViewById(R.id.layProgress);
        llErrorBoletaPagoNotAccess = (LinearLayout) findViewById(R.id.llErrorBoletaPagoNotAccess);
        btnDownloadVoucherPDF = (Button) findViewById(R.id.btnDownloadVoucherPDF);

        Intent intent = getIntent();
        statusSeeBoletaPago = intent.getIntExtra("statusSeeBoletaPago", 0);

        boletasPagoPresenter = new BoletasPagoPresenter();
        boletasPagoPresenter.attachedView(this);

        if (statusSeeBoletaPago == 1) {
            llErrorBoletaPagoNotAccess.setVisibility(View.VISIBLE);
        } else {
            boletasPagoPresenter.getBoletasPago();
        }

        btnDownloadVoucherPDF.setOnClickListener(this);
    }

    @Override
    public void showBoletasPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        pdfUrl = boletasPagoResponse.getResponse();

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
        /*switch (requestCode) {
            case 100:
                if (grantResults.length > 0
                        || grantResults[0] == PackageManager.PERMISSION_GRANTED
                        || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    new DownloadFilesFromServer(NewPortApplication.getAppContext().getApplicationContext(), pdfUrl);
                } else {
                    Toast.makeText(BoletasPagoActivity.this, "Permisos denegados", Toast.LENGTH_SHORT).show();
                }
        }*/
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
                Toast.makeText(BoletasPagoActivity.this, "No se encontró su boleta de pago", Toast.LENGTH_SHORT).show();
            }
            pdfViewer.fromStream(inputStream).swipeHorizontal(true).spacing(5).load();
        }
    }
}
