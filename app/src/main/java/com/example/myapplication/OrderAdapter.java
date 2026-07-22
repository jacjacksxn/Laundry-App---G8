package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

//main adapter
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    private final OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onEditClick(Order order);

        void onDeleteClick(Order order);
    }

    //constructor
    //receives the list of orders and the click listener
    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    //creates a new row whenever recyc view needs one
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    //displays data inside each row
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Order order = orderList.get(position);

        //displays the price using the string resource
        holder.tvServiceName.setText(order.getServiceName());
        holder.tvPrice.setText(
                holder.itemView.getContext()
                        .getString(R.string.price_format, order.getPrice())
        );
        holder.tvStatus.setText(order.getStatus());

        String service = order.getServiceName().toLowerCase();

        //order of service
        if (service.contains("iron")) {
            holder.ivServiceIcon.setImageResource(R.drawable.baseline_iron_24);
        } else if (service.contains("dry")) {
            holder.ivServiceIcon.setImageResource(R.drawable.baseline_dry_cleaning_24);
        } else {
            holder.ivServiceIcon.setImageResource(R.drawable.ic_wash);
        }

        holder.btnEdit.setOnClickListener(v ->
                listener.onEditClick(order)
        );

        holder.btnDelete.setOnClickListener(v ->
                listener.onDeleteClick(order)
        );
    }

    //returns total no. of orders
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateList(List<Order> newList) {
        this.orderList = newList;
        notifyDataSetChanged();
    }

    //holds all de views inside item_order.xml
    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvServiceName, tvPrice, tvStatus;
        ImageButton btnEdit, btnDelete;
        ImageView ivServiceIcon;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            //connecting java var to xml views
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            ivServiceIcon = itemView.findViewById(R.id.iv_service_icon);
        }
    }
}
