package com.newport.app.ui.profile;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.ui.SplashActivity;
import com.newport.app.ui.boletaspago.BoletasPagoActivity;
import com.newport.app.ui.boletaspago.BoletasPagoContract;
import com.newport.app.ui.boletaspago.ValidateAccessBoletaPagoPresenter;
import com.newport.app.ui.boletaspago.VerificationUserAllowBoletaPagoPresenter;
import com.newport.app.ui.chats.channels.ChannelsActivity;
import com.newport.app.util.PreferencesHeper;
import com.newport.app.widget.DatePickerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, ProfileContract.View,
        BoletasPagoContract.ViewValidateAccessBoletaPago, BoletasPagoContract.ViewVerificationUserAllowBoletaPago {

    private ProfilePresenter profilePresenter;
    private ValidateAccessBoletaPagoPresenter validateAccessBoletaPagoPresenter;
    private VerificationUserAllowBoletaPagoPresenter verificationUserAllowBoletaPagoPresenter;

    private CoordinatorLayout crdProfile;

    private TextView lblNamePerfil;
    private TextView lblCodeSapPerfil;

    private TextView lblArea;
    private TextView lblPosition;
    private TextView lblDateAdmission;
    private TextView lblCarnetSanidad;
    private TextView lblLinkWebMail;

    private LinearLayout btnBoletasPago;

    private Button btnLogout;
    private Button btnChatChannels;

    private ProfileLateDaysAdapter profileLateDaysAdapter;
    private ProfileLateLaunchAdapter profileLateLaunchAdapter;

    private NestedScrollView nstScrollProfile;
    private ProgressBar prgProfile;
    private AlertDialog dialog;

    private View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        return rootView;
    }

    private void init() {
        btnLogout = rootView.findViewById(R.id.btnLogout);
        prgProfile = rootView.findViewById(R.id.prgProfile);
        crdProfile = rootView.findViewById(R.id.crdProfile);
        nstScrollProfile = rootView.findViewById(R.id.nstScrollProfile);
        lblNamePerfil = rootView.findViewById(R.id.lblNamePerfil);
        lblCodeSapPerfil = rootView.findViewById(R.id.lblCodeSapPerfil);
        lblArea = rootView.findViewById(R.id.lblArea);
        lblPosition = rootView.findViewById(R.id.lblPosition);
        lblLinkWebMail = rootView.findViewById(R.id.lblLinkWebMail);
        lblDateAdmission = rootView.findViewById(R.id.lblDateAdmission);
        btnBoletasPago = rootView.findViewById(R.id.btnBoletasPago);
        lblCarnetSanidad = rootView.findViewById(R.id.lblCarnetSanidad);

        btnChatChannels = rootView.findViewById(R.id.btnChatChannels);

        btnBoletasPago.setOnClickListener(this);
        lblCarnetSanidad.setOnClickListener(this);
        lblLinkWebMail.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        profileLateDaysAdapter = new ProfileLateDaysAdapter();
        profileLateLaunchAdapter = new ProfileLateLaunchAdapter();

        RecyclerView rcvLateEntry = rootView.findViewById(R.id.rcvLateEntry);
        rcvLateEntry.setHasFixedSize(true);
        rcvLateEntry.setAdapter(profileLateDaysAdapter);

        RecyclerView rcvLateLaunch = rootView.findViewById(R.id.rcvLateLaunch);
        rcvLateLaunch.setHasFixedSize(true);
        rcvLateLaunch.setAdapter(profileLateLaunchAdapter);

        verificationUserAllowBoletaPagoPresenter = new VerificationUserAllowBoletaPagoPresenter();
        verificationUserAllowBoletaPagoPresenter.attachedView(this);

        validateAccessBoletaPagoPresenter = new ValidateAccessBoletaPagoPresenter();
        validateAccessBoletaPagoPresenter.attachedView(this);

        profilePresenter = new ProfilePresenter();
        profilePresenter.attachedView(this);

        int dayCarnet = PreferencesHeper.getDayExpiration(NewPortApplication.getAppContext());
        int monthCarnet = PreferencesHeper.getMonthExpiration(NewPortApplication.getAppContext());
        int yearCarnet = PreferencesHeper.getYearExpiration(NewPortApplication.getAppContext());

        if (dayCarnet != 0 && (monthCarnet + 1) != 0 && yearCarnet != 0) {
            final String selectedDate = dayCarnet + " / " + (monthCarnet + 1) + " / " + yearCarnet;
            lblCarnetSanidad.setText(selectedDate);
        }

        btnChatChannels.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePresenter.getUserInfo();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.lblCarnetSanidad) {
            DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    PreferencesHeper.setDayExpiration(NewPortApplication.getAppContext(), day);
                    PreferencesHeper.setMonthExpiration(NewPortApplication.getAppContext(), month);
                    PreferencesHeper.setYearExpiration(NewPortApplication.getAppContext(), year);

                    final String selectedDate = day + " / " + (month + 1) + " / " + year;
                    lblCarnetSanidad.setText(selectedDate);
                }
            });
            newFragment.show(getFragmentManager(), "datePicker");

        } else if (view.getId() == R.id.btnLogout) {
            PreferencesHeper.setDniUser(NewPortApplication.getAppContext(), "");
            PreferencesHeper.setSapCodeUser(NewPortApplication.getAppContext(), "");
            PreferencesHeper.setKeyDeviceToken(NewPortApplication.getAppContext(), "");
            Intent intent = new Intent(NewPortApplication.getAppContext(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        } else if (view.getId() == R.id.lblLinkWebMail) {
            Uri webMail = Uri.parse(lblLinkWebMail.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, webMail);
            startActivity(intent);
        } else if (view.getId() == R.id.btnBoletasPago) {
            prgProfile.setVisibility(View.VISIBLE);
            verificationUserAllowBoletaPagoPresenter.verificationUserAllowBoletaPago();
        } else if (view.getId() == R.id.btnChatChannels) {
            startActivity(new Intent(NewPortApplication.getAppContext().getApplicationContext(), ChannelsActivity.class));
        }
    }

    private void showValidateAccessBoletaPagoDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        final View mView = this.getActivity().getLayoutInflater().inflate(R.layout.dialog_accessing_boleta_pago, null);
        final EditText txtPasswordUser = mView.findViewById(R.id.txtPasswordUser);
        Button btnAccessBoletaPago = mView.findViewById(R.id.btnAccessBoletaPago);
        TextView btnClosePopUp = mView.findViewById(R.id.btnClosePopUp);


        btnAccessBoletaPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtPasswordUser.getText().toString().isEmpty()) {
                    Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), "Por favor, ingrese su contraseña.", Toast.LENGTH_SHORT).show();
                } else {
                    validateAccessBoletaPagoPresenter.validateAccessBoletaPago(txtPasswordUser.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        btnClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(mView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showLoading() {
        prgProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        prgProfile.setVisibility(View.GONE);
    }

    @Override
    public void showUserInfo(UserResponse userResponse) {
        lblNamePerfil.setText(userResponse.getName());
        lblCodeSapPerfil.setText(String.valueOf(userResponse.getSap_code()));
        lblArea.setText(userResponse.getArea());
        lblPosition.setText(userResponse.getPosition());
        lblDateAdmission.setText(userResponse.getDate_entry());

        profileLateDaysAdapter.addData(userResponse.getTardiness().getEntry());
        profileLateLaunchAdapter.addData(userResponse.getTardiness().getLunch());

        if (PreferencesHeper.getScrollProfileStatus(NewPortApplication.getAppContext())) {
            nstScrollProfile.fullScroll(View.FOCUS_DOWN);
        }

        PreferencesHeper.setScrollProfileStatus(NewPortApplication.getAppContext(), false);
    }

    @Override
    public void showUserInfoError(String error) {
        Snackbar snackbar = Snackbar
                .make(crdProfile, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_request, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        profilePresenter.getUserInfo();
                    }
                });

        snackbar.show();
    }

    @Override
    public void showValidateAccessBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        if (boletasPagoResponse.getResponse().equals("access_granted")) {
            Intent intent = new Intent(this.getActivity(), BoletasPagoActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), "Contraseña incorrecta, vuelva a introducir por favor.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showValidateAccessBoletaPagoError(String error) {
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVerificationUserAllowBoletaPagoSuccess(BoletasPagoResponse boletasPagoResponse) {
        if (boletasPagoResponse.getResponse().equals("allowed")) {
            prgProfile.setVisibility(View.GONE);
            showValidateAccessBoletaPagoDialog();
        } else {
            prgProfile.setVisibility(View.GONE);
            Intent intent = new Intent(this.getActivity(), BoletasPagoActivity.class);
            intent.putExtra("statusSeeBoletaPago", 1);
            startActivity(intent);
            //Toast.makeText(NewPortApplication.getAppContext(), "Lo sentimos. Para poder acceder a esta opcion debes de haber habilitado la visualizacion de boleta electronica", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showVerificationUserAllowBoletaPagoError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }
}
