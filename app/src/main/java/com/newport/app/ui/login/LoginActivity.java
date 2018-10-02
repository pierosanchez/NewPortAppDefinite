package com.newport.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.main.MainActivity;
import com.newport.app.util.Helper;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginPresenter loginPresenter;
    private FirebaseAnalytics mFirebaseAnalytics;

    private EditText edtDni;
    private EditText edtPassword;
    private TextView lblPasswordForgotten;
    private Button btnAccess;
    private ProgressBar progressBar;
    /*private EditText edtSap;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        loginPresenter = new LoginPresenter();
        loginPresenter.attachedView(this);

        edtDni = findViewById(R.id.edtDni);
        btnAccess = findViewById(R.id.btnAccess);
        progressBar = findViewById(R.id.progressBar);

        lblPasswordForgotten = findViewById(R.id.lblPasswordForgotten);

        edtPassword = findViewById(R.id.edtPassword);
        /*edtSap = findViewById(R.id.edtSap);*/
    }

    public void callForgottenPasswordActivity(View view) {
        Intent intent = new Intent(NewPortApplication.getAppContext(), PasswordForgottenActivity.class);
        startActivity(intent);
        finish();
    }

    public void accessAndValidate(View view) {

        if (Helper.validateDniEditText(edtDni) /*&& validateEdt(edtSap)*/&& Helper.validatePasswordEditText(edtPassword)) {
            loginPresenter.login(edtDni.getText().toString(), edtPassword.getText().toString());
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
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
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