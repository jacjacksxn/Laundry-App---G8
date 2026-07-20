package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rv_notifications);
        rv.setLayoutManager(new LinearLayoutManager(this));
        
        List<NotificationItem> items = new ArrayList<>();
        items.add(new NotificationItem("Order Confirmed", "Your order #12345 has been confirmed.", "2m ago"));
        items.add(new NotificationItem("Laundry Ready", "Your laundry is ready for pickup/delivery.", "1h ago"));
        items.add(new NotificationItem("Special Offer", "Get 20% off on dry cleaning today!", "5h ago"));
        items.add(new NotificationItem("Payment Success", "Payment for order #12344 was successful.", "1d ago"));

        rv.setAdapter(new NotificationAdapter(items));
    }

    private static class NotificationItem {
        String title, message, time;
        NotificationItem(String title, String message, String time) {
            this.title = title;
            this.message = message;
            this.time = time;
        }
    }

    private static class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        private final List<NotificationItem> items;

        NotificationAdapter(List<NotificationItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NotificationItem item = items.get(position);
            holder.tvTitle.setText(item.title);
            holder.tvMessage.setText(item.message);
            holder.tvTime.setText(item.time);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvMessage, tvTime;
            ViewHolder(View v) {
                super(v);
                tvTitle = v.findViewById(R.id.tv_title);
                tvMessage = v.findViewById(R.id.tv_message);
                tvTime = v.findViewById(R.id.tv_time);
            }
        }
    }
}
