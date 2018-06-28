package com.newport.app.ui.mundialevent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.StatusMundialActivity;
import com.newport.app.data.models.response.MatchsResponse;
import com.newport.app.data.models.response.UserElectionResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.main.MainActivity;
import com.newport.app.util.PreferencesHeper;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MundialEventActivity extends BaseActivity implements MundialEventContract.View {
    private FirebaseAnalytics mFirebaseAnalytics;
    private MundialEventPresenter mundialEventPresenter;
    private List<MatchsResponse> listaMatchs;
    private List<MatchsResponse> currentMatchs;

    private TextView group1Country1Label;
    private TextView group1Country2Label;
    private TextView group2Country1Label;
    private TextView group2Country2Label;
    private TextView group3Country1Label;
    private TextView group3Country2Label;
    private TextView group4Country1Label;
    private TextView group4Country2Label;

    private ImageView group1Country1Image;
    private ImageView group1Country2Image;
    private ImageView group2Country1Image;
    private ImageView group2Country2Image;
    private ImageView group3Country1Image;
    private ImageView group3Country2Image;
    private ImageView group4Country1Image;
    private ImageView group4Country2Image;
    private ImageView imgBannerError;

    private RadioButton group1Country1Checkbox;
    private RadioButton group1Country2Checkbox;
    private RadioButton group1EmpateCheckbox;
    private RadioButton group2Country1Checkbox;
    private RadioButton group2Country2Checkbox;
    private RadioButton group2EmpateCheckbox;
    private RadioButton group3Country1Checkbox;
    private RadioButton group3Country2Checkbox;
    private RadioButton group3EmpateCheckbox;
    private RadioButton group4Country1Checkbox;
    private RadioButton group4Country2Checkbox;
    private RadioButton group4EmpateCheckbox;
    private RadioButton radioButton;

    private RadioGroup rdgrpPartido1;
    private RadioGroup rdgrpPartido2;
    private RadioGroup rdgrpPartido3;
    private RadioGroup rdgrpPartido4;

    private String election1 = "-";
    private String election2 = "-";
    private String election3 = "-";
    private String election4 = "-";
    private String idPartido1 = "-";
    private String idPartido2 = "-";
    private String idPartido3 = "-";
    private String idPartido4 = "-";

    private LinearLayout partido1Layer;
    private LinearLayout partido2Layer;
    private LinearLayout partido3Layer;
    private LinearLayout partido4Layer;
    private LinearLayout generalContainerLayout;

    private ProgressBar progressBar;
    private Button btnSave;
    private Button btnSeeStatusMundial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mundial_event);
        init();
    }

    private void init() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mundialEventPresenter = new MundialEventPresenter();
        mundialEventPresenter.attachedView(this);

        group1Country1Label = findViewById(R.id.group1Country1Label);
        group1Country2Label = findViewById(R.id.group1Country2Label);
        group2Country1Label = findViewById(R.id.group2Country1Label);
        group2Country2Label = findViewById(R.id.group2Country2Label);
        group3Country1Label = findViewById(R.id.group3Country1Label);
        group3Country2Label = findViewById(R.id.group3Country2Label);
        group4Country1Label = findViewById(R.id.group4Country1Label);
        group4Country2Label = findViewById(R.id.group4Country2Label);

        group1Country1Image = findViewById(R.id.group1Country1Image);
        group1Country2Image = findViewById(R.id.group1Country2Image);
        group2Country1Image = findViewById(R.id.group2Country1Image);
        group2Country2Image = findViewById(R.id.group2Country2Image);
        group3Country1Image = findViewById(R.id.group3Country1Image);
        group3Country2Image = findViewById(R.id.group3Country2Image);
        group4Country1Image = findViewById(R.id.group4Country1Image);
        group4Country2Image = findViewById(R.id.group4Country2Image);
        imgBannerError = findViewById(R.id.imgBannerError);

        group1Country1Checkbox = findViewById(R.id.group1Country1Checkbox);
        group1Country2Checkbox = findViewById(R.id.group1Country2Checkbox);
        group1EmpateCheckbox = findViewById(R.id.group1EmpateCheckbox);
        group2Country1Checkbox = findViewById(R.id.group2Country1Checkbox);
        group2Country2Checkbox = findViewById(R.id.group2Country2Checkbox);
        group2EmpateCheckbox = findViewById(R.id.group2EmpateCheckbox);
        group3Country1Checkbox = findViewById(R.id.group3Country1Checkbox);
        group3Country2Checkbox = findViewById(R.id.group3Country2Checkbox);
        group3EmpateCheckbox = findViewById(R.id.group3EmpateCheckbox);
        group4Country1Checkbox = findViewById(R.id.group4Country1Checkbox);
        group4Country2Checkbox = findViewById(R.id.group4Country2Checkbox);
        group4EmpateCheckbox = findViewById(R.id.group4EmpateCheckbox);

        partido1Layer = findViewById(R.id.partido1Layer);
        partido2Layer = findViewById(R.id.partido2Layer);
        partido3Layer = findViewById(R.id.partido3Layer);
        partido4Layer = findViewById(R.id.partido4Layer);
        generalContainerLayout = findViewById(R.id.generalContainerLayout);

        rdgrpPartido4 = findViewById(R.id.rdgrpPartido4);
        rdgrpPartido3 = findViewById(R.id.rdgrpPartido3);
        rdgrpPartido2 = findViewById(R.id.rdgrpPartido2);
        rdgrpPartido1 = findViewById(R.id.rdgrpPartido1);

        progressBar = findViewById(R.id.prgMundial);
        btnSave = findViewById(R.id.btnSave);
        btnSeeStatusMundial = findViewById(R.id.btnSeeStatusMundial);

        mundialEventPresenter.getMundialEventMatches();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId1 = rdgrpPartido1.getCheckedRadioButtonId();
                int selectedId2 = rdgrpPartido2.getCheckedRadioButtonId();
                int selectedId3 = rdgrpPartido3.getCheckedRadioButtonId();
                int selectedId4 = rdgrpPartido4.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId1);
                radioButton = (RadioButton) findViewById(selectedId2);
                radioButton = (RadioButton) findViewById(selectedId3);
                radioButton = (RadioButton) findViewById(selectedId4);

                if (currentMatchs.size() == 1) {
                    if (selectedId1 == R.id.group1Country1Checkbox) {
                        election1 = "1";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else if (selectedId1 == R.id.group1Country2Checkbox) {
                        election1 = "3";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else {
                        election1 = "2";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    }
                } else if (currentMatchs.size() == 2) {
                    if (selectedId1 == R.id.group1Country1Checkbox) {
                        election1 = "1";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else if (selectedId1 == R.id.group1Country2Checkbox) {
                        election1 = "3";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else {
                        election1 = "2";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    }

                    if (selectedId2 == R.id.group2Country1Checkbox) {
                        election2 = "1";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    } else if (selectedId2 == R.id.group2Country2Checkbox) {
                        election2 = "3";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    } else {
                        election2 = "2";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    }
                } else if (currentMatchs.size() == 3) {
                    if (selectedId1 == R.id.group1Country1Checkbox) {
                        election1 = "1";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else if (selectedId1 == R.id.group1Country2Checkbox) {
                        election1 = "3";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else {
                        election1 = "2";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    }

                    if (selectedId2 == R.id.group2Country1Checkbox) {
                        election2 = "1";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    } else if (selectedId2 == R.id.group2Country2Checkbox) {
                        election2 = "3";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    } else {
                        election2 = "2";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    }

                    if (selectedId3 == R.id.group3Country1Checkbox) {
                        election3 = "1";
                        idPartido3 = currentMatchs.get(2).getId_partido();
                    } else if (selectedId3 == R.id.group3Country2Checkbox) {
                        election3 = "3";
                        idPartido3 = currentMatchs.get(2).getId_partido();
                    } else {
                        election3 = "2";
                        idPartido3 = currentMatchs.get(2).getId_partido();
                    }
                } else if (currentMatchs.size() == 4) {

                    if (selectedId1 == R.id.group1Country1Checkbox) {
                        election1 = "1";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else if (selectedId1 == R.id.group1Country2Checkbox) {
                        election1 = "3";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    } else {
                        election1 = "2";
                        idPartido1 = currentMatchs.get(0).getId_partido();
                    }

                    if (selectedId2 == R.id.group2Country1Checkbox) {
                        election2 = "1";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    } else if (selectedId2 == R.id.group2Country2Checkbox) {
                        election2 = "3";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    } else {
                        election2 = "2";
                        idPartido2 = currentMatchs.get(1).getId_partido();
                    }

                    if (selectedId3 == R.id.group3Country1Checkbox) {
                        election3 = "1";
                        idPartido3 = currentMatchs.get(2).getId_partido();
                    } else if (selectedId3 == R.id.group3Country2Checkbox) {
                        election3 = "3";
                        idPartido3 = currentMatchs.get(2).getId_partido();
                    } else {
                        election3 = "2";
                        idPartido3 = currentMatchs.get(2).getId_partido();
                    }

                    if (selectedId4 == R.id.group4Country1Checkbox) {
                        election4 = "1";
                        idPartido4 = currentMatchs.get(3).getId_partido();
                    } else if (selectedId4 == R.id.group4Country2Checkbox) {
                        election4 = "3";
                        idPartido4 = currentMatchs.get(3).getId_partido();
                    } else {
                        election4 = "2";
                        idPartido4 = currentMatchs.get(3).getId_partido();
                    }
                }

                if (!group1Country1Checkbox.isChecked() && !group1Country2Checkbox.isChecked() && !group1EmpateCheckbox.isChecked() &&
                        !group2Country1Checkbox.isChecked() && !group2Country2Checkbox.isChecked() && !group2EmpateCheckbox.isChecked() &&
                        !group3Country1Checkbox.isChecked() && !group3Country2Checkbox.isChecked() && !group3EmpateCheckbox.isChecked() &&
                        !group4Country1Checkbox.isChecked() && !group4Country2Checkbox.isChecked() && !group4EmpateCheckbox.isChecked()) {
                    Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                } else {
                    if (currentMatchs.size() == 1) {
                        if (!group1Country1Checkbox.isChecked() && !group1Country2Checkbox.isChecked() && !group1EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else {
                            onClickButtonSave();
                        }
                    } else if (currentMatchs.size() == 2) {
                        if (!group1Country1Checkbox.isChecked() && !group1Country2Checkbox.isChecked() && !group1EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else if (!group2Country1Checkbox.isChecked() && !group2Country2Checkbox.isChecked() && !group2EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else {
                            onClickButtonSave();
                        }
                    } else if (currentMatchs.size() == 3) {
                        if (!group1Country1Checkbox.isChecked() && !group1Country2Checkbox.isChecked() && !group1EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else if (!group2Country1Checkbox.isChecked() && !group2Country2Checkbox.isChecked() && !group2EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else if (!group3Country1Checkbox.isChecked() && !group3Country2Checkbox.isChecked() && !group3EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else {
                            onClickButtonSave();
                        }
                    } else {
                        if (!group1Country1Checkbox.isChecked() && !group1Country2Checkbox.isChecked() && !group1EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else if (!group2Country1Checkbox.isChecked() && !group2Country2Checkbox.isChecked() && !group2EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else if (!group3Country1Checkbox.isChecked() && !group3Country2Checkbox.isChecked() && !group3EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else if (!group4Country1Checkbox.isChecked() && !group4Country2Checkbox.isChecked() && !group4EmpateCheckbox.isChecked()) {
                            Toast.makeText(NewPortApplication.getAppContext(), "Seleccione alguna casilla, por favor.", Toast.LENGTH_SHORT).show();
                        } else {
                            onClickButtonSave();
                        }
                    }
                }
            }
        });

        btnSeeStatusMundial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MundialEventActivity.this, StatusMundialActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
    public void showValidateUserElectionSucces(UserElectionResponse userElectionResponse) {
        if (userElectionResponse.getMessage().equals("yaMarco")) {
            generalContainerLayout.setVisibility(View.GONE);
            imgBannerError.setVisibility(View.VISIBLE);
        } else {
            imgBannerError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showValidateUserElectionErorr(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMundialMatchesSuccess(List<MatchsResponse> matchsResponse) {
        currentMatchs = matchsResponse;
        Log.d("respuesta", matchsResponse.get(0).getPartido());

        List<MatchsResponse> listCurrentMatchs = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        DateFormat timeFormat = new SimpleDateFormat("HH:MM");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        /*for (int i = 0; i < matchsResponse.size(); i++) {
            try {
                Date dat = dateFormat.parse(matchsResponse.get(i).getFecha());
                Date systemDate = dateFormat.parse(date);
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(systemDate);
                cal2.setTime(dat);
                if (cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.DAY_OF_MONTH) > cal1.get(Calendar.DAY_OF_MONTH) - 1) {
                    Log.d("dateBefore", "" + cal1.get(Calendar.DAY_OF_MONTH));
                    Log.d("dateEquals", "" + (cal1.get(Calendar.DAY_OF_MONTH) - 1));
                    listCurrentMatchs.add(matchsResponse.get(i));

                    Log.d("listCurrentMatchs", String.valueOf(currentMatchs.size()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/

        if (matchsResponse.get(0).getPartido().equals("empty")){
            //Toast.makeText(this, "No hay partidos para el dia de hoy", Toast.LENGTH_SHORT).show();
            imgBannerError.setImageResource(R.drawable.nopartidosedited);
            generalContainerLayout.setVisibility(View.GONE);
            imgBannerError.setVisibility(View.VISIBLE);
        } else {
            if (matchsResponse.size() == 0) {
                Toast.makeText(this, "No hay partidos para el dia de hoy", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            if (matchsResponse.size() == 1) {
                mundialEventPresenter.validateUserElection(PreferencesHeper.getDniUser(NewPortApplication.getAppContext()), matchsResponse.get(0).getId_partido());

                group1Country1Label.setText(matchsResponse.get(0).getPais1());
                group1Country2Label.setText(matchsResponse.get(0).getPais2());

                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country2Image);
                partido2Layer.setVisibility(View.GONE);
                partido3Layer.setVisibility(View.GONE);
                partido4Layer.setVisibility(View.GONE);
            }

            if (matchsResponse.size() == 2) {
                mundialEventPresenter.validateUserElection(PreferencesHeper.getDniUser(NewPortApplication.getAppContext()), matchsResponse.get(0).getId_partido());

                group1Country1Label.setText(matchsResponse.get(0).getPais1());
                group1Country2Label.setText(matchsResponse.get(0).getPais2());
                group2Country1Label.setText(matchsResponse.get(1).getPais1());
                group2Country2Label.setText(matchsResponse.get(1).getPais2());

                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country2Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(1).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group2Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(1).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group2Country2Image);
                partido3Layer.setVisibility(View.GONE);
                partido4Layer.setVisibility(View.GONE);
            }

            if (matchsResponse.size() == 3) {
                mundialEventPresenter.validateUserElection(PreferencesHeper.getDniUser(NewPortApplication.getAppContext()), matchsResponse.get(0).getId_partido());

                group1Country1Label.setText(matchsResponse.get(0).getPais1());
                group1Country2Label.setText(matchsResponse.get(0).getPais2());
                group2Country1Label.setText(matchsResponse.get(1).getPais1());
                group2Country2Label.setText(matchsResponse.get(1).getPais2());
                group3Country1Label.setText(matchsResponse.get(2).getPais1());
                group3Country2Label.setText(matchsResponse.get(2).getPais2());

                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country2Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(1).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group2Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(1).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group2Country2Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(2).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group3Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(2).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group3Country2Image);
                partido4Layer.setVisibility(View.GONE);
            }

            if (matchsResponse.size() == 4) {
                mundialEventPresenter.validateUserElection(PreferencesHeper.getDniUser(NewPortApplication.getAppContext()), matchsResponse.get(0).getId_partido());

                group1Country1Label.setText(matchsResponse.get(0).getPais1());
                group1Country2Label.setText(matchsResponse.get(0).getPais2());
                group2Country1Label.setText(matchsResponse.get(1).getPais1());
                group2Country2Label.setText(matchsResponse.get(1).getPais2());
                group3Country1Label.setText(matchsResponse.get(2).getPais1());
                group3Country2Label.setText(matchsResponse.get(2).getPais2());
                group4Country1Label.setText(matchsResponse.get(3).getPais1());
                group4Country2Label.setText(matchsResponse.get(3).getPais2());

                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(0).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group1Country2Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(1).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group2Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(1).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group2Country2Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(2).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group3Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(2).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group3Country2Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(3).getPais1_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group4Country1Image);
                Picasso.with(NewPortApplication.getAppContext())
                        .load(matchsResponse.get(3).getPais2_image())
                        .placeholder(R.drawable.newport_gray)
                        .error(android.R.drawable.ic_dialog_alert)
                        .fit()
                        .into(group4Country2Image);
            }

        }
    }

    @Override
    public void showMundialMatchesError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserElectionSuccess(UserElectionResponse userElectionResponse) {
        if (userElectionResponse.getMessage().equals("yaMarco")) {
            Toast.makeText(NewPortApplication.getAppContext(), "No puede Volver a marcar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NewPortApplication.getAppContext(), "Guardado Correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showUserElectionError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }


    public void onClickButtonSave() {
        mundialEventPresenter.setUserElection(PreferencesHeper.getDniUser(NewPortApplication.getAppContext()), election1, election2, election3, election4,
                idPartido1, idPartido2, idPartido3, idPartido4);
    }
}
