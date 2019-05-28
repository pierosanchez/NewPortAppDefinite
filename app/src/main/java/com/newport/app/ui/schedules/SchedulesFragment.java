package com.newport.app.ui.schedules;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.ScheduleResponse;
import com.newport.app.data.models.response.UserScheduleResponse;
import com.newport.app.ui.newdetail.NewDetailFragment;
import com.newport.app.ui.scheduleprocess.SeeSwitchScheduleRequestFragment;
import com.newport.app.ui.scheduleprocess.SwitchScheduleFragment;
import com.newport.app.ui.scheduleprocess.SwitchTurnFragment;
import com.newport.app.util.Constant;
import com.newport.app.util.PreferencesHeper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchedulesFragment extends Fragment implements ScheduleContract.View,
        ScheduleAdapter.OnClickSchedulerListener, ScheduleAdapter.OnClickSchedulerSwitchListener,
        ScheduleAdapter.OnClickSeeScheduleRequest {

    private SchedulePresenter schedulePresenter;
    private ScheduleAdapter scheduleAdapter;
    private RelativeLayout rltProgress;
    private RecyclerView rcvScheduels;
    //private TextView lbluserSchedule;

    private FirebaseAnalytics mFirebaseAnalytics;
    // Google Analytics variables
    private Tracker mTracker;

    private View rootView;
    private AlertDialog dialog;

    public SchedulesFragment() {
        // Required empty public constructor
    }

    public static SchedulesFragment newInstance() {
        return new SchedulesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_schedules, container, false);
        init();
        return rootView;
    }

    private void init() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        rltProgress = rootView.findViewById(R.id.rltProgress);
        //lbluserSchedule = rootView.findViewById(R.id.lbluserSchedule);

        rcvScheduels = rootView.findViewById(R.id.rcvScheduels);
        rcvScheduels.setHasFixedSize(true);

        //Get width system
        int width = PreferencesHeper.getWidthSystem(getActivity());
        //Instantiate Google Analytics
        mTracker = ((NewPortApplication) this.getActivity().getApplication()).getTracker(NewPortApplication.TrackerName.APP_TRACKER);
        mTracker.setScreenName("Schedules");

        scheduleAdapter = new ScheduleAdapter(width);
        scheduleAdapter.setOnScheduleClickListener(this);
        scheduleAdapter.setOnScheduleSwitchClickListener(this);
        scheduleAdapter.setOnSeeScheduleRequestClickListener(this);
        rcvScheduels.setAdapter(scheduleAdapter);

        schedulePresenter = new SchedulePresenter();
        schedulePresenter.attachedView(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        schedulePresenter.getSchedules();
        //schedulePresenter.getUserSchedules(PreferencesHeper.getDniUser(NewPortApplication.getAppContext()));
    }

    @Override
    public void showLoading() {
        rltProgress.setVisibility(View.VISIBLE);
        rcvScheduels.setVisibility(View.GONE);
        //lbluserSchedule.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rltProgress.setVisibility(View.GONE);
        rcvScheduels.setVisibility(View.VISIBLE);
        //lbluserSchedule.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSchedules(List<ScheduleResponse> scheduleResponseList) {
        scheduleAdapter.addData(scheduleResponseList);
    }

    @Override
    public void showSchedulesError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSchedules(UserScheduleResponse userScheduleResponse) {
        //lbluserSchedule.setText(userScheduleResponse.getHorario());
        rltProgress.setVisibility(View.GONE);
    }

    @Override
    public void showUserSchedulesError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScheduleItemClick(ScheduleResponse scheduleResponse) {

        //Track Event
        Bundle bundle = new Bundle();
        bundle.putString("casino_id", scheduleResponse.getId() + "");

        if (scheduleResponse.getFile_url().equals("")) {
            Toast.makeText(getActivity(), "Horario aún no disponible", Toast.LENGTH_SHORT).show();
            bundle.putString("schedule", "not available");
        } else {
            Intent intent = new Intent(NewPortApplication.getAppContext(), ScheduleViewer.class);
            intent.putExtra("pdfUrl", scheduleResponse.getFile_url());
            startActivity(intent);
            /*bundle.putString("schedule", "available");

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(this.getResources().getColor(R.color.colorPrimary));
            builder.setShowTitle(true);
            builder.setStartAnimations(getActivity(), android.R.anim.fade_in, android.R.anim.fade_out);
            builder.setExitAnimations(getActivity(), android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            customTabsIntent.launchUrl(getActivity(), Uri.parse(scheduleResponse.getFile_url()));*/
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Horarios")
                .setAction("Imagen del Horario: " + scheduleResponse.getLogo())
                .setLabel("Clicked")
                .build()
        );

        mFirebaseAnalytics.logEvent("see_schedule", bundle);
    }

    @Override
    public void onScheduleSwtichItemClick() {
        final SchedulesFragment activity = this;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        final View mView = this.getActivity().getLayoutInflater().inflate(R.layout.dialog_switch_schedule, null);
        Button btnSwitchTurn = mView.findViewById(R.id.btnSwitchTurn);
        Button btnSwitchOff = mView.findViewById(R.id.btnSwitchOff);
        Button btnClosePopUp = mView.findViewById(R.id.btnClosePopUp);

        btnClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSwitchTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchTurnFragment switchTurnFragment = SwitchTurnFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.hide(activity);
                transaction.add(R.id.content_fragments, switchTurnFragment, Constant.FRAGMENT_NEWS_DETAIL);
                transaction.commit();

                dialog.dismiss();
            }
        });

        btnSwitchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchScheduleFragment switchScheduleFragment = SwitchScheduleFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.hide(activity);
                transaction.add(R.id.content_fragments, switchScheduleFragment, Constant.FRAGMENT_NEWS_DETAIL);
                transaction.commit();

                dialog.dismiss();
            }
        });

        builder.setView(mView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onSeeScheduleRequestItemClick() {
        SeeSwitchScheduleRequestFragment seeSwitchScheduleRequestFragment = SeeSwitchScheduleRequestFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.hide(this);
        transaction.add(R.id.content_fragments, seeSwitchScheduleRequestFragment, Constant.FRAGMENT_NEWS_DETAIL);
        transaction.commit();
    }
}