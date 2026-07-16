package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);


        // Back navigation
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Confirm action
        if (findViewById(R.id.btnContinue) != null) {
            findViewById(R.id.btnContinue).setOnClickListener(v -> {
                Toast.makeText(this, "Schedule Confirmed!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

    }
}
