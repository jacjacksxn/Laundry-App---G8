package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvSubtitle = findViewById(R.id.tv_subtitle);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        EditText etConfirmPassword = findViewById(R.id.et_confirm_password);
        Button btnAction = findViewById(R.id.btn_action);
        TextView tvToggleMode = findViewById(R.id.tv_toggle_mode);

        tvToggleMode.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            if (isLoginMode) {
                tvTitle.setText(R.string.welcome_back);
                tvSubtitle.setText(R.string.sign_in_continue);
                etConfirmPassword.setVisibility(View.GONE);
                btnAction.setText(R.string.login);
                tvToggleMode.setText(R.string.dont_have_account);
            } else {
                tvTitle.setText(R.string.create_account);
                tvSubtitle.setText(R.string.fill_details);
                etConfirmPassword.setVisibility(View.VISIBLE);
                btnAction.setText(R.string.register);
                tvToggleMode.setText(R.string.already_have_account);
            }
        });

        btnAction.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (isLoginMode) {
                // Login logic
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, R.string.please_enter_credentials, Toast.LENGTH_SHORT).show();
                } else if (dbHelper.checkUser(username, password)) {
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                }
            } else {
                // Register logic
                String confirmPass = etConfirmPassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPass)) {
                    Toast.makeText(this, R.string.passwords_dont_match, Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.addUser(username, password)) {
                        Toast.makeText(this, R.string.reg_success, Toast.LENGTH_SHORT).show();
                        // Switch back to login mode automatically
                        isLoginMode = true;
                        tvTitle.setText(R.string.welcome_back);
                        tvSubtitle.setText(R.string.sign_in_continue);
                        etConfirmPassword.setVisibility(View.GONE);
                        btnAction.setText(R.string.login);
                        tvToggleMode.setText(R.string.dont_have_account);
                    } else {
                        Toast.makeText(this, R.string.reg_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
