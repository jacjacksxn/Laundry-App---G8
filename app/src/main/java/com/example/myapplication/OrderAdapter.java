package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;
    private final OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onEditClick(Order order);
        void onDeleteClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvServiceName.setText(order.getServiceName());
        holder.tvPrice.setText(holder.itemView.getContext().getString(R.string.price_format, order.getPrice()));
        holder.tvStatus.setText(order.getStatus());

        // Dynamic icon assignment based on service name
        String service = order.getServiceName().toLowerCase();
        if (service.contains("iron")) {
            holder.ivServiceIcon.setImageResource(R.drawable.ic_ironing);
        } else if (service.contains("dry")) {
            holder.ivServiceIcon.setImageResource(R.drawable.ic_dry_clean);
        } else {
            holder.ivServiceIcon.setImageResource(R.drawable.ic_wash);
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(order));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvPrice, tvStatus;
        ImageButton btnEdit, btnDelete;
        android.widget.ImageView ivServiceIcon;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            ivServiceIcon = itemView.findViewById(R.id.iv_service_icon);
        }
    }
}
