package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ItemRecyclerAdapter.OnRowClickListener {

    RecyclerView itemRecyclerView;
    ItemRecyclerAdapter itemRecyclerAdapter;
    List<Item> itemList =new ArrayList<>();
    DatabaseHelper db;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(HomeActivity.this);
        itemList =db.fetchAllItems();

        //Recycler view
        itemRecyclerView = findViewById(R.id.itemRecyclerView);
        itemRecyclerAdapter = new ItemRecyclerAdapter(itemList, this, this);
        itemRecyclerView.setAdapter(itemRecyclerAdapter);

        layoutManager =new LinearLayoutManager(this);
        itemRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(itemRecyclerView.VERTICAL);







    }

    @Override
    public void onItemClick(int position) {
        Intent removeIntent = new Intent(HomeActivity.this, RemoveActivity.class);
        removeIntent.putExtra("type", itemList.get(position).getType());
        removeIntent.putExtra("name", itemList.get(position).getName());
        removeIntent.putExtra("date", itemList.get(position).getDate());
        removeIntent.putExtra("phone", itemList.get(position).getPhone());
        removeIntent.putExtra("location", itemList.get(position).getLocation());
        removeIntent.putExtra("description", itemList.get(position).getDescription());
        startActivity(removeIntent);


    }
}