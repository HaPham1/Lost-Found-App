package com.example.lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfoundapp.model.Item;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemAdapterHolder> {

    private List<Item> itemList;
    private Context context;
    private OnRowClickListener listener;

    public ItemRecyclerAdapter(List<Item> itemList, Context context, OnRowClickListener clickListener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = clickListener;
    }

    public interface OnRowClickListener {
        void onItemClick(int position);
    }


    @NonNull
    @Override
    public ItemRecyclerAdapter.ItemAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.property_item, parent, false);
        return new ItemAdapterHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ItemAdapterHolder holder, int position) {
        Item item = itemList.get(position);
        holder.name.setText(item.getType() + " " + item.getName());
        holder.phone.setText(item.getPhone());
        holder.description.setText(item.getDescription());
        holder.date.setText(item.getDate());
        holder.location.setText(item.getLocation());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public OnRowClickListener onRowClickListener;
        TextView name, phone, description, date, location;

        public ItemAdapterHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);


            this.onRowClickListener = onRowClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onRowClickListener.onItemClick(getAdapterPosition());
        }
    }

}
