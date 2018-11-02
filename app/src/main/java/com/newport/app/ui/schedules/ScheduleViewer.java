package com.newport.app.ui.schedules;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.newport.app.R;
import com.newport.app.ui.BaseActivity;
import com.newport.app.util.OnSwipeTouchListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScheduleViewer extends BaseActivity {

    private PDFView pdfViewer;
    private String pdfUrl;
    private RelativeLayout layProgress;
    private ProgressDialog pb;

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
        layProgress = (RelativeLayout) findViewById(R.id.layProgress);
        Log.d("pdfUrl", pdfUrl);

        new RetrievePDFStream().execute(pdfUrl);
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ScheduleViewer.this, "Cargando...", Toast.LENGTH_SHORT).show();
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
            pdfViewer.fromStream(inputStream).swipeHorizontal(true).spacing(5).load();
        }
    }
}


