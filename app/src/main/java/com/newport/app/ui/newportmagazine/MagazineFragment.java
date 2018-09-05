package com.newport.app.ui.newportmagazine;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.newport.app.NewPortApplication;
import com.newport.app.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MagazineFragment extends Fragment {

    private PDFView pdfViewer;
    private RelativeLayout layProgress;
    private ProgressDialog pb;
    private View rootView;

    public MagazineFragment() {
        // Required empty public constructor
    }

    public static MagazineFragment newInstance() {
        MagazineFragment fragment = new MagazineFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_magazine, container, false);
        init();
        return rootView;
    }

    private void init() {
        pdfViewer = rootView.findViewById(R.id.pdfViewer);
        layProgress = rootView.findViewById(R.id.layProgress);

        new RetrievePDFStream().execute("http://208.68.36.216/storage/files/revista_newport_julio_2018.pdf");
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(NewPortApplication.getAppContext(), "Cargando...", Toast.LENGTH_LONG).show();
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
            pdfViewer.fromStream(inputStream).swipeHorizontal(true).spacing(10).load();
        }
    }

}
