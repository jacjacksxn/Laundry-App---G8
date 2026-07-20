package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recommended RecyclerView
        RecyclerView rvRecommended = findViewById(R.id.rv_recommended);
        if (rvRecommended != null) {
            rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvRecommended.setAdapter(new RecommendedAdapter());
        }

        // Header Navigation
        if (findViewById(R.id.btn_profile) != null) {
            findViewById(R.id.btn_profile).setOnClickListener(v -> 
                    startActivity(new Intent(this, ProfileActivity.class)));
        }

        // Active Order Section Navigation
        if (findViewById(R.id.btn_order_history) != null) {
            findViewById(R.id.btn_order_history).setOnClickListener(v -> 
                    startActivity(new Intent(this, OrdersActivity.class)));
        }

        if (findViewById(R.id.btn_active_order) != null) {
            findViewById(R.id.btn_active_order).setOnClickListener(v -> 
                    startActivity(new Intent(this, OrderTrackingActivity.class)));
        }

        // Search Bar Navigation
        EditText searchServices = findViewById(R.id.search_services);
        if (searchServices != null) {
            searchServices.setOnClickListener(v -> 
                    startActivity(new Intent(this, ServiceDetailActivity.class)));
        }

        // Service Grid Navigation
        if (findViewById(R.id.service_ironing) != null) {
            findViewById(R.id.service_ironing).setOnClickListener(v -> 
                    startActivity(new Intent(this, ServiceDetailActivity.class)));
        }

        if (findViewById(R.id.service_wash_iron) != null) {
            findViewById(R.id.service_wash_iron).setOnClickListener(v -> 
                    startActivity(new Intent(this, ServiceDetailActivity.class)));
        }

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        if (bottomNav != null) {
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    return true;
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(this, ServiceDetailActivity.class));
                    return true;
                } else if (id == R.id.nav_notifications) {
                    startActivity(new Intent(this, NotificationsActivity.class));
                    return true;
                } else if (id == R.id.nav_messages) {
                    startActivity(new Intent(this, MessagesActivity.class));
                    return true;
                }
                return true;
            });
        }
    }
}
