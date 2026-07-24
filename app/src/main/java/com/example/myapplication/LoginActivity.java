package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Handles user authentication (Login and Registration).
 * Includes fields for email, phone, city, and address.
 */
public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText etUser, etPass, etConfirm, etEmail, etPhone, etCity, etAddress;
    TextView tvTitle, tvSub, tvToggle;
    Button btn;
    boolean isLogin = true; // State tracker: true for Login, false for Register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews(); // Initialize components
        
        // Listeners for mode toggle and primary action
        tvToggle.setOnClickListener(v -> toggleMode());
        btn.setOnClickListener(v -> handleAction());
    }

    // Connects Java objects to XML layout IDs and setups DB
    void initViews() {
        db = new DatabaseHelper(this);
        etUser = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etCity = findViewById(R.id.et_city);
        etAddress = findViewById(R.id.et_address);
        etPass = findViewById(R.id.et_password);
        etConfirm = findViewById(R.id.et_confirm_password);
        tvTitle = findViewById(R.id.tv_title);
        tvSub = findViewById(R.id.tv_subtitle);
        tvToggle = findViewById(R.id.tv_toggle_mode);
        btn = findViewById(R.id.btn_action);
    }

    // Switches the UI between Login and Register modes
    void toggleMode() {
        isLogin = !isLogin;
        tvTitle.setText(isLogin ? R.string.welcome_back : R.string.create_account);
        tvSub.setText(isLogin ? R.string.sign_in_continue : R.string.fill_details);
        
        // Registration extra fields visibility
        int visibility = isLogin ? View.GONE : View.VISIBLE;
        etEmail.setVisibility(visibility);
        etPhone.setVisibility(visibility);
        etCity.setVisibility(visibility);
        etAddress.setVisibility(visibility);
        etConfirm.setVisibility(visibility);

        btn.setText(isLogin ? R.string.login : R.string.register);
        tvToggle.setText(isLogin ? R.string.dont_have_account : R.string.already_have_account);
        
        // Clear errors when toggling
        etUser.setError(null);
        etPass.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etCity.setError(null);
        etAddress.setError(null);
        etConfirm.setError(null);
    }

    // Process either Login or Registration based on current mode
    void handleAction() {
        String u = etUser.getText().toString().trim();
        String p = etPass.getText().toString().trim();

        if (isLogin) {
            if (u.isEmpty()) {
                etUser.setError("Username is required");
                return;
            }
            if (p.isEmpty()) {
                etPass.setError("Password is required");
                return;
            }

            // Login check: verify user in SQLite
            if (db.checkUser(u, p)) {
                saveSession(u); // Remember who is logged in
                msg("Login Successful");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                msg("Invalid username or password");
            }
        } else {
            // Registration logic: collect all details
            String em = etEmail.getText().toString().trim();
            String ph = etPhone.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String addr = etAddress.getText().toString().trim();
            String cp = etConfirm.getText().toString().trim();

            if (u.length() < 3) {
                etUser.setError("Username must be at least 3 characters");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                etEmail.setError("Please enter a valid email address");
                return;
            }
            if (ph.length() < 10) {
                etPhone.setError("Phone number must be at least 10 digits");
                return;
            }
            if (city.isEmpty()) {
                etCity.setError("City is required");
                return;
            }
            if (addr.isEmpty()) {
                etAddress.setError("Address is required");
                return;
            }
            if (p.length() < 6) {
                etPass.setError("Password must be at least 6 characters");
                return;
            }
            if (!p.equals(cp)) {
                etConfirm.setError("Passwords do not match");
                return;
            }

            // Check if username already exists
            Cursor cursor = db.getUser(u);
            if (cursor != null && cursor.getCount() > 0) {
                etUser.setError("Username already exists");
                cursor.close();
                return;
            }
            if (cursor != null) cursor.close();

            if (db.addUser(u, p, em, ph, city, addr)) {
                msg("Registration successful! Please login.");
                toggleMode(); // Switch back to login mode after success
            } else {
                msg("Registration failed. Please try again.");
            }
        }
    }

    // Stores the username in SharedPreferences so ProfileActivity knows who it is
    void saveSession(String username) {
        SharedPreferences pref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        pref.edit().putString("logged_user", username).apply();
    }

    // Simple helper for Toast messages
    void msg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
