package com.newport.app.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.ui.BaseActivity;

public class ConclusionChangeForgottenPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btnOpenEnewportMailWebPage;
    private TextView lblCorreoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion_change_forgotten_password);

        btnOpenEnewportMailWebPage = findViewById(R.id.btnOpenEnewportMailWebPage);
        lblCorreoURL = findViewById(R.id.lblCorreoURL);

        btnOpenEnewportMailWebPage.setOnClickListener(this);
        lblCorreoURL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOpenEnewportMailWebPage) {
            Uri webMail = Uri.parse(NewPortApplication.getAppContext().getApplicationContext().getString(R.string.lbl_link_web_mail));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(webMail);
            startActivity(intent);
        } else if (v.getId() == R.id.lblCorreoURL) {
            Uri webMail = Uri.parse(NewPortApplication.getAppContext().getApplicationContext().getString(R.string.lbl_link_web_mail));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(webMail);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
