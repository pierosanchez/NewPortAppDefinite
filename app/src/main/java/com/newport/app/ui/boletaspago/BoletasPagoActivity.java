package com.newport.app.ui.boletaspago;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.newport.app.R;
import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.ui.BaseActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoletasPagoActivity extends BaseActivity implements BoletasPagoContract.View {

    private BoletasPagoPresenter boletasPagoPresenter;

    private PDFView pdfViewer;
    private String pdfUrl;
    private RelativeLayout layProgress;
    private ProgressDialog pb;

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

        Intent intent = getIntent();
        statusSeeBoletaPago = intent.getIntExtra("statusSeeBoletaPago", 0);

        boletasPagoPresenter = new BoletasPagoPresenter();
        boletasPagoPresenter.attachedView(this);

        if (statusSeeBoletaPago == 1) {
            llErrorBoletaPagoNotAccess.setVisibility(View.VISIBLE);
        } else {
            boletasPagoPresenter.getBoletasPago();
        }
    }

    @Override
    public void showBoletasPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        pdfUrl = boletasPagoResponse.getResponse();

        Log.d("pdfUrl", pdfUrl);
        new BoletasPagoActivity.RetrievePDFStream().execute(pdfUrl);
    }

    @Override
    public void showBoletasPagoError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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
