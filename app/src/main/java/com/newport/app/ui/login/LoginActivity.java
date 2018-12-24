package com.newport.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.main.MainActivity;
import com.newport.app.util.Helper;
import com.newport.app.util.PreferencesHeper;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginPresenter loginPresenter;
    private FirebaseAnalytics mFirebaseAnalytics;



    private EditText edtDni;
    private EditText edtPassword;
    private TextView lblPasswordForgotten;
    private TextView lblDomainMail;
    private Button btnAccess;
    private ProgressBar progressBar;

    private String firebase_token;

    /*private EditText edtSap;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        firebase_token = FirebaseInstanceId.getInstance().getToken();

        loginPresenter = new LoginPresenter();
        loginPresenter.attachedView(this);

        edtDni = findViewById(R.id.edtDni);
        btnAccess = findViewById(R.id.btnAccess);
        progressBar = findViewById(R.id.progressBar);

        lblPasswordForgotten = findViewById(R.id.lblPasswordForgotten);
        lblDomainMail = findViewById(R.id.lblDomainMail);

        edtPassword = findViewById(R.id.edtPassword);
        /*edtSap = findViewById(R.id.edtSap);*/



        //piero token -------> ePi5f4Sl0b8:APA91bHFo_EM0mA9QRrz8wbEQCtnvIoQNCI083fvPnkXYR9yJT9PL0_cgkn76oBgcxqBESEGKrJCXIaVZ88PRItTPp9_tbaC-bFA3WaWHZra86Z_19oIphW8XSMpGCgJCLtf2M85ZMqz
        //mariano token -------> d1WIUCab71M:APA91bG9WoxG-RPjzEXZxpgRLBm9n2a4E-rKj8e2OyJHUAyN6ME8TD44r2Z3i6J5dmBcQuRUTGIRGTp7o2WKyTCJCQxq8MJJJ-2cpLoKROqGQ5dPnjN7Gy8n34gFTmpHt6SnvcZcT5jv
        //gabi token ------> cgVTKiJDR-A:APA91bHRTsLP1YmNgr32Gk17-4dmiFUbN8NGDgo8Nn_um2QkIsAy6S7b94SwDs2aBU2hvXAeOCup5MoQALYuNIrp8ercDHhr-4y8jmuSG7KZYHbaID3vU5PER_AMCOyiK34ccqTjMCfx
    }

    public void callForgottenPasswordActivity(View view) {
        Intent intent = new Intent(NewPortApplication.getAppContext(), PasswordForgottenActivity.class);
        startActivity(intent);
        finish();
    }

    public void accessAndValidate(View view) {

        PreferencesHeper.setKeyDeviceToken(NewPortApplication.getAppContext(), firebase_token);

        if (Helper.validateDniEditText(edtDni) /*&& validateEdt(edtSap)*/ && Helper.validatePasswordEditText(edtPassword)) {
            String mail = edtDni.getText().toString() + lblDomainMail.getText().toString();
            loginPresenter.login(mail, edtPassword.getText().toString());
        }

    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(NewPortApplication.getAppContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void callVerifyAccountDialog() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        /*VerifyAccountFragment newFragment = VerifyAccountFragment.newInstance();
        newFragment.setOnItemClickListener(new VerifyAccountFragment.OnClickSelectorListener() {
            @Override
            public void onDialogItemClick(String optionVerify) {
                Toast.makeText(LoginActivity.this, "Se ha enviado un código de verificación a tu " + optionVerify, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                finish();
            }
        });
        newFragment.show(getFragmentManager(), Constant.DIALOG_VERIFY_ACCOUNT);*/
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        edtPassword.setEnabled(false);
        edtDni.setEnabled(false);
        btnAccess.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loginSucces() {

        //Track Event
        Bundle bundle = new Bundle();
        bundle.putString("dni", edtDni.getText().toString());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

        callVerifyAccountDialog();
    }

    @Override
    public void loginError(String error) {
        //Track Event
        if (error.equals("DNI no autorizado")) {
            PreferencesHeper.setKeyDeviceToken(NewPortApplication.getAppContext(), "");
            Toast.makeText(this, "Usuario no autorizado.", Toast.LENGTH_SHORT).show();
            edtDni.setText("");
            edtPassword.setText("");
            edtDni.setEnabled(true);
            edtPassword.setEnabled(true);
            btnAccess.setEnabled(true);
        } else if (error.equals("default_password")) {
            Toast.makeText(this, "Por sus seguridad cambie su contraseña por una nueva.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (error.equals("forgotten_password")) {
            Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (error.equals("incorrect_password")) {
            PreferencesHeper.setKeyDeviceToken(NewPortApplication.getAppContext(), "");
            Toast.makeText(this, "Contraseña incorrecta. Vuelva a ingresarla, por favor.", Toast.LENGTH_SHORT).show();
            edtDni.setText("");
            edtPassword.setText("");
            edtPassword.setEnabled(true);
            edtDni.setEnabled(true);
            btnAccess.setEnabled(true);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("dni", edtDni.getText().toString());
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN + "_failed", bundle);

            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            edtDni.setText("");
            edtPassword.setText("");
            edtPassword.setEnabled(true);
            edtDni.setEnabled(true);
            btnAccess.setEnabled(true);
        }
    }
}