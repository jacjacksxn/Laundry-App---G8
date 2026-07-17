package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        // Back navigation
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // Confirm action
        if (findViewById(R.id.btnContinue) != null) {
            findViewById(R.id.btnContinue).setOnClickListener(v -> {
                startActivity(new Intent(this, BasketActivity.class));
            });
        }

    }
}
