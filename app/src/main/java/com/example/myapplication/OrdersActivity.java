package com.example.myapplication;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private OrderAdapter adapter;
    private List<Order> orderList;
    private RecyclerView rvOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        dbHelper = new DatabaseHelper(this);
        orderList = new ArrayList<>();
        rvOrders = findViewById(R.id.rv_orders);
        EditText etSearch = findViewById(R.id.et_search);
        Button btnAdd = findViewById(R.id.btn_add_order);

        adapter = new OrderAdapter(orderList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onEditClick(Order order) {
                showOrderDialog(order);
            }

            @Override
            public void onDeleteClick(Order order) {
                showDeleteConfirmation(order);
            }
        });

        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(adapter);

        loadOrders();

        btnAdd.setOnClickListener(v -> showOrderDialog(null));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchOrders(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadOrders() {
        orderList.clear();
        Cursor cursor = dbHelper.getAllOrders();
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STATUS))
                );
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void searchOrders(String query) {
        orderList.clear();
        Cursor cursor = dbHelper.searchOrders(query);
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STATUS))
                );
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void showOrderDialog(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order, null);
        builder.setView(view);

        EditText etService = view.findViewById(R.id.et_dialog_service);
        EditText etPrice = view.findViewById(R.id.et_dialog_price);
        EditText etStatus = view.findViewById(R.id.et_dialog_status);
        Button btnSave = view.findViewById(R.id.btn_dialog_save);

        if (order != null) {
            etService.setText(order.getServiceName());
            etPrice.setText(order.getPrice() + "");
            etStatus.setText(order.getStatus());
        }

        AlertDialog dialog = builder.create();
        btnSave.setOnClickListener(v -> {
            String service = etService.getText().toString();
            String priceStr = etPrice.getText().toString();
            String status = etStatus.getText().toString();

            if (service.isEmpty() || priceStr.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, R.string.fields_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);

            if (order == null) {
                dbHelper.insertOrder(service, price, status);
                Toast.makeText(this, R.string.order_added, Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateOrder(order.getId(), service, price, status);
                Toast.makeText(this, R.string.order_updated, Toast.LENGTH_SHORT).show();
            }

            loadOrders();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showDeleteConfirmation(Order order) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_order)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    dbHelper.deleteOrder(order.getId());
                    Toast.makeText(this, R.string.order_deleted, Toast.LENGTH_SHORT).show();
                    loadOrders();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
