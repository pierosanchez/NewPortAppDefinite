package com.newport.app.ui.scheduleprocess;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.SwitchSchedulesPendingRequestResponse;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.List;


public class SeeSwitchScheduleRequestFragment extends Fragment implements SwitchScheduleContract.View,
        SwitchScheduleContract.View6, SwitchSchedulePendingRequestAdapter.OnClickPendingRequestListener,
        View.OnClickListener, SwitchScheduleContract.View7, SwitchScheduleContract.View8 {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwitchSchedulePresenter switchSchedulePresenter;
    private SwitchSchedule6Presenter switchSchedule6Presenter;
    private SwitchSchedule7Presenter switchSchedule7Presenter;
    private SwitchSchedule8Presenter switchSchedule8Presenter;

    private SwitchSchedulePendingRequestAdapter switchSchedulePendingRequestAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String userPosition;
    private String mailerAddress;
    private String mailer;
    private String toAddress;
    private String to;
    private String dayToChange;
    private String mailerSchedule;
    private int status;
    private String bossAddress;
    private String bossName;
    private int id;
    private String managerAddress;
    private String managerName;

    private String emailForTests;

    private SwitchSchedulesPendingRequestResponse lastUserToSendRequestResponse;

    private View rootView;

    private LinearLayout llCambioDe;
    private LinearLayout llSolicitante;
    private LinearLayout llSolicitanteHorario;
    private LinearLayout llCambiaCon;
    private LinearLayout llCambiaConHorario;
    private LinearLayout llUsersArea;
    private LinearLayout llFechaCambio;
    private LinearLayout llValidadoPor;

    private LinearLayout llDatosSolicitante;
    private LinearLayout llDatosSegundoUsuario;
    private LinearLayout llDatosGenerales;

    private TextView lblTipoCambio;
    private TextView lblName1;
    private Button lblSchedule;
    private TextView lblName2;
    private Button lblSchedulePersonToChange;
    private TextView lblArea;
    private TextView lblDayToSwitch;
    private TextView lblPersonWhoVerify;

    // Dialog
    private TextView lblLunesScheduleUser;
    private TextView lblMartesScheduleUser;
    private TextView lblMiercolesScheduleUser;
    private TextView lblJuevesScheduleUser;
    private TextView lblViernesScheduleUser;
    private TextView lblSabadoScheduleUser;
    private TextView lblDomingoScheduleUser;

    private Button btnAprove;
    private Button btnDecline;

    private LinearLayout llProgress;

    private RecyclerView lvRequests;

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

        lblTipoCambio = rootView.findViewById(R.id.lblTipoCambio);
        lblName1 = rootView.findViewById(R.id.lblName1);
        lblSchedule = rootView.findViewById(R.id.lblSchedule);
        lblName2 = rootView.findViewById(R.id.lblName2);
        lblSchedulePersonToChange = rootView.findViewById(R.id.lblSchedulePersonToChange);
        lblArea = rootView.findViewById(R.id.lblArea);
        lblDayToSwitch = rootView.findViewById(R.id.lblDayToSwitch);
        lblPersonWhoVerify = rootView.findViewById(R.id.lblPersonWhoVerify);

        llDatosSolicitante = rootView.findViewById(R.id.llDatosSolicitante);
        llDatosSegundoUsuario = rootView.findViewById(R.id.llDatosSegundoUsuario);
        llDatosGenerales = rootView.findViewById(R.id.llDatosGenerales);

        btnAprove = rootView.findViewById(R.id.btnAprove);
        btnDecline = rootView.findViewById(R.id.btnDecline);

        lvRequests = rootView.findViewById(R.id.lvRequests);

        switchSchedulePresenter = new SwitchSchedulePresenter();
        switchSchedulePresenter.attachedView(this);
        switchSchedule6Presenter = new SwitchSchedule6Presenter();
        switchSchedule6Presenter.attachedView(this);
        switchSchedule7Presenter = new SwitchSchedule7Presenter();
        switchSchedule7Presenter.attachedView(this);
        switchSchedule8Presenter = new SwitchSchedule8Presenter();
        switchSchedule8Presenter.attachedView(this);

        btnAprove.setOnClickListener(this);
        btnDecline.setOnClickListener(this);

        switchSchedulePendingRequestAdapter = new SwitchSchedulePendingRequestAdapter();
        lvRequests.setAdapter(switchSchedulePendingRequestAdapter);

        switchSchedulePendingRequestAdapter.setOnPendingRequestClickListener(this);

        lblSchedule.setOnClickListener(this);
        lblSchedulePersonToChange.setOnClickListener(this);
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
        userPosition = userResponse.getPosition();
        emailForTests = userResponse.getEmail();
        if (userResponse.getEmail().equals("DANI.ELA.CGC@GMAIL.COM")){
            llValidadoPor.setVisibility(View.GONE);
            switchSchedule6Presenter.getBossSwitchSchedulePendingRequests(userResponse.getEmail());
        } else if (userResponse.getEmail().equals("JROMANIGARCIA21@GMAIL.COM")) {
            switchSchedule6Presenter.getManagerSwitchSchedulePendingRequests(userResponse.getEmail());
        } else {
            if (userResponse.getPosition().equals("Jefe de Sala")) {
                llValidadoPor.setVisibility(View.GONE);
                switchSchedule6Presenter.getBossSwitchSchedulePendingRequests(userResponse.getEmail());
            } else if (!userResponse.getPosition().equals("Gerente de Sala")
                    && !userResponse.getPosition().equals("Jefe de Sala")
                    && !userResponse.getPosition().equals("Sub Gerente de Sala")
                    && !userResponse.getPosition().equals("Sub-Gerente  de Sala")) {
                llCambiaCon.setVisibility(View.GONE);
                llCambiaConHorario.setVisibility(View.GONE);
                llValidadoPor.setVisibility(View.GONE);
                llUsersArea.setVisibility(View.GONE);
                switchSchedule6Presenter.getUserSwitchSchedulePendingRequests(userResponse.getEmail());
            } else {
                switchSchedule6Presenter.getManagerSwitchSchedulePendingRequests(userResponse.getEmail());
            }
        }

    }

    @Override
    public void showScheduleDataError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList) {
        if (switchScheduleEmailResponseList.size() == 0) {
            Toast.makeText(NewPortApplication.getAppContext(), "No tiene solicitudes pendientes", Toast.LENGTH_SHORT).show();
        } else {
            switchSchedulePendingRequestAdapter.addData(switchScheduleEmailResponseList);
        }
    }

    @Override
    public void showUserSwitchSchedulePendingRequestsError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBossSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList) {
        if (switchScheduleEmailResponseList.size() == 0) {
            Toast.makeText(NewPortApplication.getAppContext(), "No tiene solicitudes pendientes", Toast.LENGTH_SHORT).show();
        } else {
            switchSchedulePendingRequestAdapter.addData(switchScheduleEmailResponseList);
        }
    }

    @Override
    public void showBossSwitchSchedulePendingRequestsError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showManagerSwitchSchedulePendingRequestsSuccess(List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList) {
        if (switchScheduleEmailResponseList.size() == 0) {
            Toast.makeText(NewPortApplication.getAppContext(), "No tiene solicitudes pendientes", Toast.LENGTH_SHORT).show();
        } else {
            switchSchedulePendingRequestAdapter.addData(switchScheduleEmailResponseList);
        }
    }

    @Override
    public void showManagerSwitchSchedulePendingRequestsError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPendingRequestItemClick(SwitchSchedulesPendingRequestResponse lastUser) {
        lastUserToSendRequestResponse = lastUser;
        if (emailForTests.equals("DANI.ELA.CGC@GMAIL.COM")){
            lblSchedule.setVisibility(View.VISIBLE);
            lblSchedulePersonToChange.setVisibility(View.VISIBLE);
            llDatosSolicitante.setVisibility(View.VISIBLE);
            llDatosSegundoUsuario.setVisibility(View.VISIBLE);
            llDatosGenerales.setVisibility(View.VISIBLE);
            if (lastUser.getMailer_schedule().equals("OFF")) {
                lblTipoCambio.setText("Cambio de Dia Off");
            } else {
                lblTipoCambio.setText("Cambio de Turno");
            }
            lblName1.setText(lastUser.getMailer_name());
            lblSchedule.setText("Ver Horarios");
            lblDayToSwitch.setText(lastUser.getMailer_day_change());
            lblName2.setText(lastUser.getReceiver_name());
            lblSchedulePersonToChange.setText("Ver Horarios");
            lblArea.setText("Central");
        } else if (emailForTests.equals("JROMANIGARCIA21@GMAIL.COM")) {
            lblSchedule.setVisibility(View.VISIBLE);
            lblSchedulePersonToChange.setVisibility(View.VISIBLE);
            llDatosSolicitante.setVisibility(View.VISIBLE);
            llDatosSegundoUsuario.setVisibility(View.VISIBLE);
            llDatosGenerales.setVisibility(View.VISIBLE);
            if (lastUser.getMailer_schedule().equals("OFF")) {
                lblTipoCambio.setText("Cambio de Dia Off");
            } else {
                lblTipoCambio.setText("Cambio de Turno");
            }
            lblName1.setText(lastUser.getMailer_name());
            lblSchedule.setText("Ver Horarios");
            lblName2.setText(lastUser.getReceiver_name());
            lblSchedulePersonToChange.setText("Ver Horarios");
            lblArea.setText("Central");
            lblDayToSwitch.setText(lastUser.getMailer_day_change());
            lblPersonWhoVerify.setText(lastUser.getBoss_name());
        } else {
            if (userPosition.equals("Jefe de Sala")) {
                lblSchedule.setVisibility(View.VISIBLE);
                lblSchedulePersonToChange.setVisibility(View.VISIBLE);
                llDatosSolicitante.setVisibility(View.VISIBLE);
                llDatosSegundoUsuario.setVisibility(View.VISIBLE);
                llDatosGenerales.setVisibility(View.VISIBLE);
                if (lastUser.getMailer_schedule().equals("OFF")) {
                    lblTipoCambio.setText("Cambio de Dia Off");
                } else {
                    lblTipoCambio.setText("Cambio de Turno");
                }
                lblName1.setText(lastUser.getMailer_name());
                lblSchedule.setText("Ver Horarios");
                lblDayToSwitch.setText(lastUser.getMailer_day_change());
                lblName2.setText(lastUser.getReceiver_name());
                lblSchedulePersonToChange.setText("Ver Horarios");
                lblArea.setText(lastUser.getReceiver_name());
            } else if (!userPosition.equals("Gerente de Sala")
                    && !userPosition.equals("Jefe de Sala")
                    && !userPosition.equals("Sub Gerente de Sala")
                    && !userPosition.equals("Sub-Gerente  de Sala")) {
                lblSchedule.setVisibility(View.VISIBLE);
                llDatosGenerales.setVisibility(View.VISIBLE);
                if (lastUser.getMailer_schedule().equals("OFF")) {
                    lblTipoCambio.setText("Cambio de Dia Off");
                } else {
                    lblTipoCambio.setText("Cambio de Turno");
                }
                llDatosSolicitante.setVisibility(View.VISIBLE);
                lblName1.setText(lastUser.getMailer_name());
                lblSchedule.setText("Ver Horarios");
                lblDayToSwitch.setText(lastUser.getMailer_day_change());
                lblName2.setText(lastUser.getReceiver_name());
                lblSchedulePersonToChange.setText("Ver Horarios");
                lblArea.setText(lastUser.getReceiver_name());
                lblPersonWhoVerify.setText(lastUser.getBoss_name());
            } else {
                lblSchedule.setVisibility(View.VISIBLE);
                lblSchedulePersonToChange.setVisibility(View.VISIBLE);
                llDatosSolicitante.setVisibility(View.VISIBLE);
                llDatosSegundoUsuario.setVisibility(View.VISIBLE);
                llDatosGenerales.setVisibility(View.VISIBLE);
                if (lastUser.getMailer_schedule().equals("OFF")) {
                    lblTipoCambio.setText("Cambio de Dia Off");
                } else {
                    lblTipoCambio.setText("Cambio de Turno");
                }
                lblName1.setText(lastUser.getMailer_name());
                lblSchedule.setText("Ver Horarios");
                lblName2.setText(lastUser.getReceiver_name());
                lblSchedulePersonToChange.setText("Ver Horarios");
                lblArea.setText(lastUser.getMailer_day_change());
                lblDayToSwitch.setText(lastUser.getMailer_day_change());
                lblPersonWhoVerify.setText(lastUser.getBoss_name());
            }
        }

        mailerAddress = lastUser.getMailer_address();
        mailer = lastUser.getMailer_name();
        toAddress = lastUser.getReceiver_address();
        to = lastUser.getReceiver_name();
        dayToChange = lastUser.getMailer_day_change();
        mailerSchedule = lastUser.getMailer_schedule();
        bossAddress = lastUser.getBoss_address();
        bossName = lastUser.getBoss_name();
        id = lastUser.getId();
        managerAddress = lastUser.getManager_address();
        managerName = lastUser.getManager_name();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAprove) {
            if (emailForTests.equals("JROMANIGARCIA21@GMAIL.COM")) {
                switchSchedule7Presenter.sendMailManagerSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                        "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 4, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
            } else if (emailForTests.equals("DANI.ELA.CGC@GMAIL.COM")) {
                switchSchedule7Presenter.sendMailBossSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                        "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 3, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
            } else {
                if (userPosition.equals("Jefe de Sala")) {
                    switchSchedule7Presenter.sendMailBossSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                            "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 3, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
                /*switchSchedule7Presenter.sendMailBossSwitchSchedule(mailerAddress, mailer, toAddress,
                        to, dayToChange, mailerSchedule, 3, bossAddress,bossName, id, managerAddress, managerName);*/
                } else if (!userPosition.equals("Gerente de Sala")
                        && !userPosition.equals("Jefe de Sala")
                        && !userPosition.equals("Sub Gerente de Sala")
                        && !userPosition.equals("Sub-Gerente  de Sala")) {
                    switchSchedule7Presenter.sendMailCoWoSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                            "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 2, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
                /*switchSchedule7Presenter.sendMailCoWoSwitchSchedule(mailerAddress, mailer, toAddress,
                        to, dayToChange, mailerSchedule, 2, bossAddress,bossName, id, managerAddress, managerName);*/
                } else {
                    switchSchedule7Presenter.sendMailManagerSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                            "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 4, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
                /*switchSchedule7Presenter.sendMailManagerSwitchSchedule(mailerAddress, mailer, toAddress,
                        to, dayToChange, mailerSchedule, 4, bossAddress,bossName, id, managerAddress, managerName);*/
                }
            }
        } else if (v.getId() == R.id.btnDecline) {
            if (emailForTests.equals("JROMANIGARCIA21@GMAIL.COM")) {
                switchSchedule7Presenter.sendMailManagerSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                        "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 7, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
            } else if (emailForTests.equals("DANI.ELA.CGC@GMAIL.COM")) {
                switchSchedule7Presenter.sendMailBossSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                        "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 6, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
            } else {
                if (userPosition.equals("Jefe de Sala")) {
                    switchSchedule7Presenter.sendMailBossSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                            "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 6, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
                /*switchSchedule7Presenter.sendMailBossSwitchSchedule(mailerAddress, mailer, toAddress,
                        to, dayToChange, mailerSchedule, 6, bossAddress,bossName, id, managerAddress, managerName);*/
                } else if (!userPosition.equals("Gerente de Sala")
                        && !userPosition.equals("Jefe de Sala")
                        && !userPosition.equals("Sub Gerente de Sala")
                        && !userPosition.equals("Sub-Gerente  de Sala")) {
                    switchSchedule7Presenter.sendMailCoWoSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                            "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 5, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
                /*switchSchedule7Presenter.sendMailCoWoSwitchSchedule(mailerAddress, mailer, toAddress,
                        to, dayToChange, mailerSchedule, 5, bossAddress,bossName, id, managerAddress, managerName);*/
                } else {
                    switchSchedule7Presenter.sendMailManagerSwitchSchedule("sanchezpiero96@gmail.com", "SANCHEZ ARBILDO PIERO ALEJANDRO", "GESPINOZACARRANZA@GMAIL.COM",
                            "ESPINOZA CARRANZA GABRIELA", dayToChange, mailerSchedule, 7, "DANI.ELA.CGC@GMAIL.COM", "GAMARRA CAMARENA DANIELA CRISTINA", id, "JROMANIGARCIA21@GMAIL.COM", "ROMANI GARCIA JEAN CARLO");
                /*switchSchedule7Presenter.sendMailManagerSwitchSchedule(mailerAddress, mailer, toAddress,
                        to, dayToChange, mailerSchedule, 7, bossAddress,bossName, id, managerAddress, managerName);*/
                }
            }
        } else if (v.getId() == R.id.lblSchedule) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            View mView = this.getActivity().getLayoutInflater().inflate(R.layout.item_schedule_detail, null);
            lblLunesScheduleUser = mView.findViewById(R.id.lblLunesScheduleUser);
            lblMartesScheduleUser = mView.findViewById(R.id.lblMartesScheduleUser);
            lblMiercolesScheduleUser = mView.findViewById(R.id.lblMiercolesScheduleUser);
            lblJuevesScheduleUser = mView.findViewById(R.id.lblJuevesScheduleUser);
            lblViernesScheduleUser = mView.findViewById(R.id.lblViernesScheduleUser);
            lblSabadoScheduleUser = mView.findViewById(R.id.lblSabadoScheduleUser);
            lblDomingoScheduleUser = mView.findViewById(R.id.lblDomingoScheduleUser);

            builder.setView(mView);
            AlertDialog dialog = builder.create();
            dialog.show();

            switchSchedule8Presenter.getUserScheduleByName(lastUserToSendRequestResponse.getMailer_name());
        } else if (v.getId() == R.id.lblSchedulePersonToChange) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            View mView = this.getActivity().getLayoutInflater().inflate(R.layout.item_schedule_detail, null);
            lblLunesScheduleUser = mView.findViewById(R.id.lblLunesScheduleUser);
            lblMartesScheduleUser = mView.findViewById(R.id.lblMartesScheduleUser);
            lblMiercolesScheduleUser = mView.findViewById(R.id.lblMiercolesScheduleUser);
            lblJuevesScheduleUser = mView.findViewById(R.id.lblJuevesScheduleUser);
            lblViernesScheduleUser = mView.findViewById(R.id.lblViernesScheduleUser);
            lblSabadoScheduleUser = mView.findViewById(R.id.lblSabadoScheduleUser);
            lblDomingoScheduleUser = mView.findViewById(R.id.lblDomingoScheduleUser);

            builder.setView(mView);
            AlertDialog dialog = builder.create();
            dialog.show();

            switchSchedule8Presenter.getUserScheduleByName(lastUserToSendRequestResponse.getReceiver_name());
        }
    }

    @Override
    public void showSendMailCoWoSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        if (switchScheduleEmailResponse.getMessage().equals("success")) {
            Toast.makeText(NewPortApplication.getAppContext(), "Respuesta enviada satisfactoriamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewPortApplication.getAppContext(), "Ocurrio un error al enviar la solicitud", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSendMailCoWoSwitchScheduleError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendMailBossSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        if (switchScheduleEmailResponse.getMessage().equals("success")) {
            Toast.makeText(NewPortApplication.getAppContext(), "Respuesta enviada satisfactoriamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewPortApplication.getAppContext(), "Ocurrio un error al enviar la solicitud", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSendMailBossSwitchScheduleError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendMailManagerSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        if (switchScheduleEmailResponse.getMessage().equals("success")) {
            Toast.makeText(NewPortApplication.getAppContext(), "Respuesta enviada satisfactoriamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewPortApplication.getAppContext(), "Ocurrio un error al enviar la solicitud", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSendMailManagerSwitchScheduleError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserScheduleByNameSuccess(UserScheduleResponse userScheduleResponse) {
        lblLunesScheduleUser.setText(userScheduleResponse.getLun());
        lblMartesScheduleUser.setText(userScheduleResponse.getMar());
        lblMiercolesScheduleUser.setText(userScheduleResponse.getMie());
        lblJuevesScheduleUser.setText(userScheduleResponse.getJue());
        lblViernesScheduleUser.setText(userScheduleResponse.getVie());
        lblSabadoScheduleUser.setText(userScheduleResponse.getSab());
        lblDomingoScheduleUser.setText(userScheduleResponse.getDom());
    }

    @Override
    public void showUserScheduleByNameError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }
}
