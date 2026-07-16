package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Navigation for Services (Complex UI)
        if (findViewById(R.id.service_wash) != null) {
            findViewById(R.id.service_wash).setOnClickListener(v ->
                    startActivity(new Intent(this, ServiceDetailActivity.class)));
        }

        // Navigation for Tracking Card
        findViewById(R.id.active_order_card).setOnClickListener(v ->
                startActivity(new Intent(this, OrderTrackingActivity.class)));

        // Updated Bottom Navigation Logic
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_orders) {
                // Future Orders Activity
                return true;
            } else if (id == R.id.nav_wallet) {
                // Future Wallet Activity


                // RecyclerView
                RecyclerView rvRecommended = findViewById(R.id.rv_recommended);
                rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                rvRecommended.setAdapter(new RecommendedAdapter());

                // Navigation to OrdersActivity
                EditText searchServices = findViewById(R.id.search_services);
                searchServices.setOnClickListener(v -> startActivity(new Intent(this, OrdersActivity.class)));

                findViewById(R.id.service_ironing).setOnClickListener(v -> startActivity(new Intent(this, ServiceDetailActivity.class)));

                findViewById(R.id.service_wash_iron).setOnClickListener(v -> startActivity(new Intent(this, ServiceDetailActivity.class)));

                // Bottom Navigation
                BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
                bottomNav.setOnItemSelectedListener(item -> {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        // Already here
                        return true;
                    } else if (id == R.id.nav_search) {
                        startActivity(new Intent(this, OrdersActivity.class));
                        return true;
                    } else if (id == R.id.nav_notifications) {
                        // Just toast for now
                        return true;
                    } else if (id == R.id.nav_messages) {
                        // Just toast for now
                        return true;
                    }
                    return true;
                });
            }
        }
