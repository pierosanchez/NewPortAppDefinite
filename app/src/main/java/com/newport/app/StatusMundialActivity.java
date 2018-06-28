package com.newport.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newport.app.ui.mundialevent.MundialEventActivity;

public class StatusMundialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_mundial);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StatusMundialActivity.this, MundialEventActivity.class);
        startActivity(intent);
        finish();
    }
}
