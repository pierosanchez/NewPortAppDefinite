package com.newport.app.ui.directory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.DirectoryResponse;
import com.newport.app.util.PreferencesHeper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryFragment extends Fragment implements DirectoryContract.View {

    private DirectoryPresenter directoryPresenter;
    private DirectoryAdapter adapter;

    private View rootView;
    private CoordinatorLayout crdDirectory;
    private ProgressBar progressBar;

    // Google Analytics variables
    private Tracker mTracker;

    public DirectoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_directory, container, false);
        init();
        return rootView;
    }

    private void init() {
        crdDirectory = rootView.findViewById(R.id.crdDirectory);

        directoryPresenter = new DirectoryPresenter();
        directoryPresenter.attachedView(this);

        RecyclerView rcvDirectorio = rootView.findViewById(R.id.rcvDirectorio);
        progressBar = rootView.findViewById(R.id.progressBar);

        //Instantiate Google Analytics
        mTracker = ((NewPortApplication) this.getActivity().getApplication()).getTracker(NewPortApplication.TrackerName.APP_TRACKER);
        mTracker.setScreenName("InternalPolicies");

        adapter = new DirectoryAdapter(getActivity(), rcvDirectorio);
        rcvDirectorio.setAdapter(adapter);

        //Sending Tracker to Google Analytics
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Directorio Corporativo")
                .setAction(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()) + " entró al directorio corporativo")
                .setLabel("Click")
                .build()
        );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        directoryPresenter.makeDirectoryQuery();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDirectory(List<DirectoryResponse> sectionDirectoryList) {
        adapter.addData(sectionDirectoryList);
    }

    @Override
    public void showDirectoryError(String message) {
        Snackbar snackbar = Snackbar
                .make(crdDirectory, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_request, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        directoryPresenter.makeDirectoryQuery();
                    }
                });

        snackbar.show();
    }


}