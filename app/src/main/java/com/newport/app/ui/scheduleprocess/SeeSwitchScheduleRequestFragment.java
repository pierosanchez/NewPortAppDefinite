package com.newport.app.ui.scheduleprocess;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.UserResponse;


public class SeeSwitchScheduleRequestFragment extends Fragment implements SwitchScheduleContract.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwitchSchedulePresenter switchSchedulePresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private LinearLayout llCambioDe;
    private LinearLayout llSolicitante;
    private LinearLayout llSolicitanteHorario;
    private LinearLayout llCambiaCon;
    private LinearLayout llCambiaConHorario;
    private LinearLayout llUsersArea;
    private LinearLayout llFechaCambio;
    private LinearLayout llValidadoPor;

    private LinearLayout llProgress;

    public SeeSwitchScheduleRequestFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SeeSwitchScheduleRequestFragment newInstance(/*String param1, String param2*/) {
        SeeSwitchScheduleRequestFragment fragment = new SeeSwitchScheduleRequestFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_see_switch_schedule_request, container, false);
        init();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchSchedulePresenter.getScheduleData();
    }

    private void init() {
        llCambioDe = rootView.findViewById(R.id.llCambioDe);
        llSolicitante = rootView.findViewById(R.id.llSolicitante);
        llSolicitanteHorario = rootView.findViewById(R.id.llSolicitanteHorario);
        llCambiaCon = rootView.findViewById(R.id.llCambiaCon);
        llCambiaConHorario = rootView.findViewById(R.id.llCambiaConHorario);
        llUsersArea = rootView.findViewById(R.id.llUsersArea);
        llFechaCambio = rootView.findViewById(R.id.llFechaCambio);
        llValidadoPor = rootView.findViewById(R.id.llValidadoPor);
        llProgress = rootView.findViewById(R.id.llProgress);

        switchSchedulePresenter = new SwitchSchedulePresenter();
        switchSchedulePresenter.attachedView(this);
    }

    @Override
    public void showLoading() {
        llProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        llProgress.setVisibility(View.GONE);
    }

    @Override
    public void showScheduleData(UserResponse userResponse) {
        if (userResponse.getPosition().equals("Jefe de Sala")) {
            llValidadoPor.setVisibility(View.GONE);
        } else if (!userResponse.getPosition().equals("Gerente de Sala")
                && !userResponse.getPosition().equals("Jefe de Sala")) {
            llCambiaCon.setVisibility(View.GONE);
            llCambiaConHorario.setVisibility(View.GONE);
            llValidadoPor.setVisibility(View.GONE);
            llUsersArea.setVisibility(View.GONE);
        }
    }

    @Override
    public void showScheduleDataError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }
}
