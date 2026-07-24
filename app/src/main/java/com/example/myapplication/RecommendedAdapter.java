package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private final int[] images = {
            R.drawable.iron,
            R.drawable.bulk_wash,
            R.drawable.shoe_care,
            R.drawable.wash_iron,
            R.drawable.wash_fold
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position % images.length]);
        // Removed color filter as we are now using real photos/images
        holder.imageView.clearColorFilter();
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        android.widget.ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_recommended);
        }
    }
}
