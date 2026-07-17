package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.et_username);
        Button btnLogin = findViewById(R.id.btn_login);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String username = etUsername.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(this, R.string.please_enter_credentials, Toast.LENGTH_SHORT).show();
                } else {
                    // Start MainActivity when login button is clicked
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
    }
}
