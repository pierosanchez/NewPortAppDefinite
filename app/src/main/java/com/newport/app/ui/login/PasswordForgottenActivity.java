package com.newport.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.UserRegisterResponse;
import com.newport.app.ui.BaseActivity;

public class PasswordForgottenActivity extends BaseActivity implements LoginContract.ViewChangePassword, View.OnClickListener {

    private ChangePasswordPresenter changePasswordPresenter;

    private EditText edtSapCode;
    private EditText edtCorreo;
    private TextView lblMailDomain;

    private Button btnChangePassword;

    private ProgressBar prgChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgotten);
        init();
    }

    private void init() {
        edtSapCode = findViewById(R.id.edtSapCode);
        edtCorreo = findViewById(R.id.edtCorreo);

        lblMailDomain = findViewById(R.id.lblMailDomain);

        btnChangePassword = findViewById(R.id.btnChangePassword);

        prgChangePassword = findViewById(R.id.prgChangePassword);

        changePasswordPresenter = new ChangePasswordPresenter();
        changePasswordPresenter.attachedView(this);

        btnChangePassword.setOnClickListener(this);
    }

    private boolean validateEditText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(NewPortApplication.getAppContext().getString(R.string.required_field));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChangePassword) {
            if (validateEditText(edtSapCode) && validateEditText(edtCorreo)) {
                UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
                userRegisterRequest.setCod_sap(edtSapCode.getText().toString());
                userRegisterRequest.setMail(edtCorreo.getText().toString() + lblMailDomain.getText().toString());
                changePasswordPresenter.changeUserPassword(userRegisterRequest);
            }
        }
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
            Toast.makeText(NewPortApplication.getAppContext(), "Contraseña reestablecida exitosamente. Se le enviará la nueva contraseña a su correo.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(PasswordForgottenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void changeUserPasswordError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }
}
