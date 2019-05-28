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

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.ApplicationSQLiteDatabase.NewportAppBD;
import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.UserRegisterResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.main.MainActivity;
import com.newport.app.util.PreferencesHeper;

public class ChangePasswordActivity extends BaseActivity implements LoginContract.ViewChangePassword, View.OnClickListener {

    private ChangePasswordPresenter changePasswordPresenter;

    private EditText edtSapCode;
    private EditText edtCorreo;
    private TextView lblMailDomain;
    private EditText edtNewPassword;
    private EditText edtRepeatNewPassword;

    private Button btnChangePassword;

    private ProgressBar prgChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        edtSapCode = findViewById(R.id.edtSapCode);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtRepeatNewPassword = findViewById(R.id.edtRepeatNewPassword);

        lblMailDomain = findViewById(R.id.lblMailDomain);

        btnChangePassword = findViewById(R.id.btnChangePassword);

        prgChangePassword = findViewById(R.id.prgChangePassword);

        changePasswordPresenter = new ChangePasswordPresenter();
        changePasswordPresenter.attachedView(this);

        btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChangePassword) {
            if (/*validateEditText(edtSapCode) && validateEditText(edtCorreo) && */validateEditText(edtNewPassword) && validateEditText(edtRepeatNewPassword)) {
                if (edtNewPassword.getText().toString().equals(edtRepeatNewPassword.getText().toString())) {
                    UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
                    userRegisterRequest.setCod_sap(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()));
                    //userRegisterRequest.setMail(edtCorreo.getText().toString() + lblMailDomain.getText().toString());
                    userRegisterRequest.setPassword_user(edtNewPassword.getText().toString());
                    changePasswordPresenter.changeUserPassword(userRegisterRequest);
                } else {
                    Toast.makeText(NewPortApplication.getAppContext(), "Las contraseñas no coinciden, vuelva a ingresarlas por favor.", Toast.LENGTH_SHORT).show();
                    edtRepeatNewPassword.setText("");
                }
            } else {
                Toast.makeText(NewPortApplication.getAppContext(), "Por favor, ingrese su nueva contraseña.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateEditText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(NewPortApplication.getAppContext().getString(R.string.required_field));
            return false;
        }
        return true;
    }

    @Override
    public void showLoading() {
        prgChangePassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        prgChangePassword.setVisibility(View.GONE);
    }

    @Override
    public void changeUserPasswordSuccess(UserRegisterResponse userRegisterResponse) {
        if (userRegisterResponse.getMessage().equals("success")) {
            Toast.makeText(NewPortApplication.getAppContext(), "Guardado Correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void changeUserPasswordError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }
}
