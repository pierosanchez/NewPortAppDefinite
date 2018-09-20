package com.newport.app.ui.scheduleprocess;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SwitchTurnFragment extends Fragment implements SwitchScheduleContract.View2,
        SwitchScheduleContract.View4, AdapterView.OnItemSelectedListener, SwitchScheduleContract.View,
        SwitchScheduleOffsAdapter.OnClickUserListener, View.OnClickListener, SwitchScheduleContract.View5 {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwitchSchedulePresenter switchSchedulePresenter;
    private SwitchSchedule2Presenter switchSchedule2Presenter;
    private SwitchSchedule4Presenter switchSchedule4Presenter;
    private SwitchSchedule5Presenter switchSchedule5Presenter;

    private SwitchScheduleOffsAdapter switchScheduleOffsAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] dayToSwitch;
    private String mailToSendEmail;
    private String nameToSendEmail;
    private String nameWhoSendEmail;
    private String mailWhoSendEmail;
    private String userScheduleForEmail;
    private String otherUserSchedule;
    private String userSala;
    private String userArea;

    private View rootView;
    private Spinner spDayToSwitch;
    private TextView lblUserSchedule;
    private TextView lblUserName;
    private TextView lblScheduleCoworker;
    private TextView lblUserMail;
    private Button btnSendSwitchRequest;

    private RecyclerView lvCoworkerSchedule;

    public SwitchTurnFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SwitchTurnFragment newInstance() {
        SwitchTurnFragment fragment = new SwitchTurnFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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
        rootView = inflater.inflate(R.layout.fragment_switch_turn, container, false);
        init();
        return rootView;
    }

    private void init() {
        spDayToSwitch = rootView.findViewById(R.id.spDayToSwitch);

        lvCoworkerSchedule = rootView.findViewById(R.id.lvCoworkerSchedule);

        lblUserSchedule = rootView.findViewById(R.id.lblUserSchedule);
        lblUserName = rootView.findViewById(R.id.lblUserName);
        lblScheduleCoworker = rootView.findViewById(R.id.lblScheduleCoworker);
        lblUserMail = rootView.findViewById(R.id.lblUserMail);

        btnSendSwitchRequest = rootView.findViewById(R.id.btnSendSwitchRequest);

        switchSchedulePresenter = new SwitchSchedulePresenter();
        switchSchedulePresenter.attachedView(this);
        switchSchedule2Presenter = new SwitchSchedule2Presenter();
        switchSchedule2Presenter.attachedView(this);
        switchSchedule4Presenter = new SwitchSchedule4Presenter();
        switchSchedule4Presenter.attachedView(this);
        switchSchedule5Presenter = new SwitchSchedule5Presenter();
        switchSchedule5Presenter.attachedView(this);

        switchScheduleOffsAdapter = new SwitchScheduleOffsAdapter();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, loadDates());
        spDayToSwitch.setAdapter(adapter);

        lvCoworkerSchedule.setAdapter(switchScheduleOffsAdapter);

        switchSchedulePresenter.getScheduleData();

        switchScheduleOffsAdapter.setOnUserClickListener(this);
        spDayToSwitch.setOnItemSelectedListener(this);
        btnSendSwitchRequest.setOnClickListener(this);
    }

    private List<String> loadDates() {
        Calendar calendar = Calendar.getInstance();

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        List<String> listOfDates = new ArrayList<String>();
        String valor = "";

        for (int i = dayOfMonth + 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            Calendar calendario = Calendar.getInstance();
            calendario.set(year, month, i);

            int dayOfWeek =  calendario.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case Calendar.MONDAY:
                    valor = "Lun - " + String.valueOf(i);
                    break;
                case Calendar.TUESDAY:
                    valor = "Mar - " + String.valueOf(i);
                    break;
                case Calendar.WEDNESDAY:
                    valor = "Mie - " + String.valueOf(i);
                    break;
                case Calendar.THURSDAY:
                    valor = "Jue - " + String.valueOf(i);
                    break;
                case Calendar.FRIDAY:
                    valor = "Vie - " + String.valueOf(i);
                    break;
                case Calendar.SATURDAY:
                    valor = "Sab - " + String.valueOf(i);
                    break;
                case Calendar.SUNDAY:
                    valor = "Dom - " + String.valueOf(i);
                    break;
            }
            listOfDates.add(valor);
        }
        return listOfDates;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getAdapter().getItem(position).toString();
        dayToSwitch = item.split(" ");
        switchSchedule2Presenter.getUserSchedule();
        switchSchedule4Presenter.getUsersWork(dayToSwitch[0]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showScheduleData(UserResponse userResponse) {
        mailWhoSendEmail = userResponse.getEmail();
        userSala = userResponse.getTexo();
        userArea = userResponse.getArea();
    }

    @Override
    public void showScheduleDataError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSchedulesSuccess(UserScheduleResponse userScheduleResponse) {
        String schedule = "";
        switch (dayToSwitch[0]) {
            case "Lun":
                schedule = userScheduleResponse.getLun();
                break;
            case "Mar":
                schedule = userScheduleResponse.getMar();
                break;
            case "Mie":
                schedule = userScheduleResponse.getMie();
                break;
            case "Jue":
                schedule = userScheduleResponse.getJue();
                break;
            case "Vie":
                schedule = userScheduleResponse.getVie();
                break;
            case "Sab":
                schedule = userScheduleResponse.getSab();
                break;
            case "Dom":
                schedule = userScheduleResponse.getDom();
                break;
        }

        userScheduleForEmail = schedule;
        lblUserSchedule.setText(schedule);
        lblUserName.setText(userScheduleResponse.getUser_name());

        nameWhoSendEmail = userScheduleResponse.getUser_name();
    }

    @Override
    public void showUserSchedulesError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserWorkSuccess(List<UserScheduleResponse> userScheduleResponseList) {
        switchScheduleOffsAdapter.addData(userScheduleResponseList);
    }

    @Override
    public void showUsersWorkError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserItemClick(UserScheduleResponse lastUser) {
        String schedule = "";
        switch (dayToSwitch[0]) {
            case "Lun":
                schedule = lastUser.getLun();
                break;
            case "Mar":
                schedule = lastUser.getMar();
                break;
            case "Mie":
                schedule = lastUser.getMie();
                break;
            case "Jue":
                schedule = lastUser.getJue();
                break;
            case "Vie":
                schedule = lastUser.getVie();
                break;
            case "Sab":
                schedule = lastUser.getSab();
                break;
            case "Dom":
                schedule = lastUser.getDom();
                break;
        }

        mailToSendEmail = lastUser.getEMAIL();
        nameToSendEmail = lastUser.getUser_name();
        otherUserSchedule = lastUser.getUser_schedule();

        lblScheduleCoworker.setText(schedule);
        lblUserMail.setText(lastUser.getUser_name());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendSwitchRequest) {
            switchSchedule5Presenter.sendMailSwitchSchedule(mailWhoSendEmail, nameWhoSendEmail, mailToSendEmail, nameToSendEmail, getDate(dayToSwitch[2]), userScheduleForEmail, otherUserSchedule, 1, /*"", "", "", "",*/ userSala, userArea);
        }
    }

    @Override
    public void showSendMailSwitchScheduleSuccess(SwitchScheduleEmailResponse switchScheduleEmailResponse) {
        if (switchScheduleEmailResponse.getMessage().equals("success")) {
            Toast.makeText(NewPortApplication.getAppContext(), "Solicitud enviada satisfactoriamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewPortApplication.getAppContext(), "Ocurrio un error al enviar la solicitud", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSendMailSwitchScheduleError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    private String getDate(String selected_day) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String date = "";
        date = selected_day + "/" + month + "/" + year;
        return date;
    }
}
