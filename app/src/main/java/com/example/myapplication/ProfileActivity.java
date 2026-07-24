package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    DatabaseHelper db;
    TextView tvName, tvEmail, tvPhone, tvCity, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        
        tvName = findViewById(R.id.tv_profile_name);
        tvEmail = findViewById(R.id.tv_profile_email);
        tvPhone = findViewById(R.id.tv_profile_phone);
        tvCity = findViewById(R.id.tv_profile_city);
        tvAddress = findViewById(R.id.tv_profile_address);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_logout).setOnClickListener(v -> logout());

        loadUserData();
    }

    void loadUserData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String user = pref.getString("logged_user", null);

        if (user != null) {
            Cursor c = db.getUser(user);
            if (c != null && c.moveToFirst()) {
                tvName.setText(user);
                tvEmail.setText(c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));
                tvPhone.setText(c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)));
                tvCity.setText(c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CITY)));
                tvAddress.setText(c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS)));
                c.close();
            }
        }
    }

    void logout() {
        getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
