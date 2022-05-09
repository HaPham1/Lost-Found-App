package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelper;

public class RemoveActivity extends AppCompatActivity {
    TextView nameDetail, phoneDetail, dateDetail, locationDetail, descriptionDetail;
    String type, name, phone, date, location, description;
    Button removeBtn;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        //Receive intent
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        date = intent.getStringExtra("date");
        location = intent.getStringExtra("location");
        description = intent.getStringExtra("description");

        //Init
        nameDetail = findViewById(R.id.nameDetail);
        phoneDetail = findViewById(R.id.phoneDetail);
        dateDetail = findViewById(R.id.dateDetail);
        locationDetail = findViewById(R.id.locationDetail);
        descriptionDetail = findViewById(R.id.desDetail);
        removeBtn = findViewById(R.id.removeBtn);

        db = new DatabaseHelper(RemoveActivity.this);

        // Set view
        nameDetail.setText(type + " " + name);
        phoneDetail.setText(phone);
        dateDetail.setText(date);
        locationDetail.setText(location);
        descriptionDetail.setText(description);

        //Set button
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long result = db.deleteItem(type, name);
                if (result != -1) {
                    Toast.makeText(RemoveActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent(RemoveActivity.this, HomeActivity.class);
                    startActivity(returnIntent);
                    finish();
                }
                else {
                    Toast.makeText(RemoveActivity.this, "Fail to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}