package com.newport.app.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.UserRegisterResponse;
import com.newport.app.ui.BaseActivity;

public class RegisterActivity extends BaseActivity implements LoginContract.ViewUserRegistration, View.OnClickListener {

    private RegisterPresenter registerPresenter;
    private FirebaseAnalytics mFirebaseAnalytics;

    private EditText edtCorreo;
    private EditText edtSapCode;
    private Button btnRegister;
    private ProgressBar prgRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        registerPresenter = new RegisterPresenter();
        registerPresenter.attachedView(this);

        edtCorreo = findViewById(R.id.edtCorreo);
        edtSapCode = findViewById(R.id.edtSapCode);
        btnRegister = findViewById(R.id.btnRegister);

        prgRegister = findViewById(R.id.prgRegister);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            if (validateEditText(edtSapCode) && validateEditText(edtCorreo)){
                registerPresenter.userRegistration(edtSapCode.getText().toString(), edtCorreo.getText().toString());
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
        prgRegister.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        prgRegister.setVisibility(View.GONE);
    }

    @Override
    public void userRegistrationSuccess(UserRegisterResponse userRegisterResponse) {
        if (userRegisterResponse.getMessage().equals("success")) {
            Intent intent = new Intent(NewPortApplication.getAppContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(NewPortApplication.getAppContext(), "Ingresado Correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void userRegistrationError(String error) {
        Toast.makeText(NewPortApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
    }
}
