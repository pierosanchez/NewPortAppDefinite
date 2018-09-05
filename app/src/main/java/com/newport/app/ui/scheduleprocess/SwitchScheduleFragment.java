package com.newport.app.ui.scheduleprocess;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SwitchScheduleFragment extends Fragment implements SwitchScheduleContract.View2,
        SwitchScheduleContract.View, AdapterView.OnItemSelectedListener, SwitchScheduleContract.View3,
        SwitchScheduleOffsAdapter.OnClickUserListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwitchSchedule3Presenter switchSchedule3Presenter;
    private SwitchSchedule2Presenter switchSchedule2Presenter;
    private SwitchSchedulePresenter switchSchedulePresenter;

    private SwitchScheduleOffsAdapter switchScheduleOffsAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] dayToSwitch;

    private Spinner spDayToSwitch;
    private Spinner spCompDay;

    private View rootView;
    private Button btnSendSwitchRequest;
    private RecyclerView lvCoworkerSchedule;
    private TextView lblScheduleCoworker;
    private TextView lblUserMail;
    private TextView lblUserName;
    //private TextView lblUserSchedule;

    public SwitchScheduleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SwitchScheduleFragment newInstance() {
        SwitchScheduleFragment fragment = new SwitchScheduleFragment();
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
        rootView = inflater.inflate(R.layout.fragment_switch_schedule, container, false);
        init();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        spDayToSwitch = rootView.findViewById(R.id.spDayToSwitch);
        spCompDay = rootView.findViewById(R.id.spCompDay);

        lvCoworkerSchedule = rootView.findViewById(R.id.lvCoworkerSchedule);

        btnSendSwitchRequest = rootView.findViewById(R.id.btnSendSwitchRequest);

        lblScheduleCoworker = rootView.findViewById(R.id.lblScheduleCoworker);
        lblUserMail = rootView.findViewById(R.id.lblUserMail);
        lblUserName = rootView.findViewById(R.id.lblUserName);
        //lblUserSchedule = rootView.findViewById(R.id.lblUserSchedule);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, loadDates());
        spCompDay.setAdapter(adapter);

        switchScheduleOffsAdapter = new SwitchScheduleOffsAdapter();

        switchSchedule2Presenter = new SwitchSchedule2Presenter();
        switchSchedule2Presenter.attachedView(this);
        switchSchedulePresenter = new SwitchSchedulePresenter();
        switchSchedulePresenter.attachedView(this);
        switchSchedule3Presenter = new SwitchSchedule3Presenter();
        switchSchedule3Presenter.attachedView(this);

        switchSchedule2Presenter.getUserSchedule();
        spCompDay.setOnItemSelectedListener(this);

        switchScheduleOffsAdapter.setOnUserClickListener(this);

        lvCoworkerSchedule.setAdapter(switchScheduleOffsAdapter);
    }

    private List<String> loadDates() {
        Calendar calendar = Calendar.getInstance();

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        List<String> listOfDates = new ArrayList<String>();
        String valor = "";

        for (int i = dayOfMonth + 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            Calendar calendario = Calendar.getInstance();
            calendario.set(year, month, i);

            int dayOfWeek = calendario.get(Calendar.DAY_OF_WEEK);
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

    private void loadOffDates(int diaOff, String day) {
        Calendar calendar = Calendar.getInstance();

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        List<String> listOfDates = new ArrayList<String>();

        for (int i = dayOfMonth + 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            Calendar calendario = Calendar.getInstance();
            calendario.set(year, month, i);

            int dayOfWeek = calendario.get(Calendar.DAY_OF_WEEK);
            if (diaOff == dayOfWeek) {
                listOfDates.add(day + String.valueOf(i));
            }
        }

        if (listOfDates.size() == 0) {
            listOfDates.add("Seleccione");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listOfDates);
        spDayToSwitch.setAdapter(adapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showScheduleData(UserResponse userResponse) {

    }

    @Override
    public void showScheduleDataError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSchedulesSuccess(UserScheduleResponse userScheduleResponse) {
        lblUserName.setText(userScheduleResponse.getUser_name());
        if (userScheduleResponse.getLun().equals("OFF")) {
            loadOffDates(2, "Lun - ");
        }
        if (userScheduleResponse.getMar().equals("OFF")) {
            loadOffDates(3, "Mar - ");
        }
        if (userScheduleResponse.getMie().equals("OFF")) {
            loadOffDates(4, "Mie - ");
        }
        if (userScheduleResponse.getJue().equals("OFF")) {
            loadOffDates(5, "Jue - ");
        }
        if (userScheduleResponse.getVie().equals("OFF")) {
            loadOffDates(6, "Vie - ");
        }
        if (userScheduleResponse.getSab().equals("OFF")) {
            loadOffDates(7, "Sab - ");
        }
        if (userScheduleResponse.getDom().equals("OFF")) {
            loadOffDates(1, "Dom - ");
        }
    }

    @Override
    public void showUserSchedulesError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUsersOffSuccess(List<UserScheduleResponse> userScheduleResponseList) {
        switchScheduleOffsAdapter.addData(userScheduleResponseList);
    }

    @Override
    public void ShowUsersOffError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getAdapter().getItem(position).toString();
        dayToSwitch = item.split(" ");
        switchSchedule3Presenter.getUsersOff(dayToSwitch[0]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        lblScheduleCoworker.setText(schedule);
        if (lastUser.getEMAIL().equals("")){
            lblUserMail.setText("NO REGISTRA CORREO");
        }
    }
}
