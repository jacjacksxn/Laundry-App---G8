package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // Bottom padding handled by Nav
            return insets;
        });

        // Service Clicks
        findViewById(R.id.service_wash).setOnClickListener(v -> {
            startActivity(new Intent(this, ServiceDetailActivity.class));
        });

        findViewById(R.id.service_dry).setOnClickListener(v -> {
            startActivity(new Intent(this, ServiceDetailActivity.class));
        });

        // Active Order Click
        findViewById(R.id.active_order_card).setOnClickListener(v -> {
            startActivity(new Intent(this, TrackingActivity.class));
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_basket) {
                startActivity(new Intent(this, BasketActivity.class));
                return true;
            } else if (id == R.id.nav_tracking) {
                startActivity(new Intent(this, TrackingActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return true;
        });
    }
}