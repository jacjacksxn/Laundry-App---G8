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

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rv_messages);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<MessageItem> items = new ArrayList<>();
        items.add(new MessageItem("Laundry Service", "Hello! Your laundry is ready for pickup.", "10:30 AM", true));
        items.add(new MessageItem("Support Team", "How can we help you today?", "Yesterday", false));
        items.add(new MessageItem("Driver Alex", "I'm near your location for pickup.", "2 days ago", false));

        rv.setAdapter(new MessageAdapter(items));
    }

    private static class MessageItem {
        String name, lastMessage, time;
        boolean unread;
        MessageItem(String name, String lastMessage, String time, boolean unread) {
            this.name = name;
            this.lastMessage = lastMessage;
            this.time = time;
            this.unread = unread;
        }
    }

    private static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
        private final List<MessageItem> items;

        MessageAdapter(List<MessageItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MessageItem item = items.get(position);
            holder.tvName.setText(item.name);
            holder.tvLastMessage.setText(item.lastMessage);
            holder.tvTime.setText(item.time);
            holder.unreadIndicator.setVisibility(item.unread ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvLastMessage, tvTime;
            View unreadIndicator;
            ViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tv_name);
                tvLastMessage = v.findViewById(R.id.tv_last_message);
                tvTime = v.findViewById(R.id.tv_time);
                unreadIndicator = v.findViewById(R.id.unread_indicator);
            }
        }
    }
}
