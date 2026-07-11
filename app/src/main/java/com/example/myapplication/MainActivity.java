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
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRecommended.setAdapter(new RecommendedAdapter());

        // Navigation to OrdersActivity (Summary Screen)
        // I'll add a listener to the search bar or a specific button if I had one
        // For now, let's use the search bar's action to navigate to search in Orders
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
