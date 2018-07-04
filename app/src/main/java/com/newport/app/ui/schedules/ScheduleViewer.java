package com.newport.app.ui.schedules;

import com.github.barteksc.pdfviewer.PDFView;
import com.newport.app.R;
import com.newport.app.ui.BaseActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ScheduleViewer extends BaseActivity {

    PDFView pdfViewer;
    //WebView pdfViewer;
    String pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_viewer);
        init();
    }

    private void init() {
        Intent extra = getIntent();
        pdfUrl = extra.getStringExtra("pdfUrl");

        pdfViewer = (PDFView) findViewById(R.id.pdfViewer);

        new RetrievePDFStream().execute(pdfUrl);
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

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
            pdfViewer.fromStream(inputStream).load();
        }
    }
}
