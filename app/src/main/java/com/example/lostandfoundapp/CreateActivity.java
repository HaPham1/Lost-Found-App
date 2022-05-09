package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

public class CreateActivity extends AppCompatActivity {
    private String type = "";
    RadioButton Lost, Found;
    EditText nameText, phoneText, desText, dateText, locationText;
    Button saveBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Lost = findViewById(R.id.Lost);
        Found = findViewById(R.id.Found);
        nameText = findViewById(R.id.nameText);
        phoneText = findViewById(R.id.phoneText);
        desText = findViewById(R.id.desText);
        dateText = findViewById(R.id.dateText);
        locationText = findViewById(R.id.locationText);
        saveBtn = findViewById(R.id.saveBtn);

        db = new DatabaseHelper(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String phone = phoneText.getText().toString();
                String des = desText.getText().toString();
                String date = dateText.getText().toString();
                String location =locationText.getText().toString();
                long result = db.insertItem( new Item(type, name, phone, des, date, location));
                if (result > 0)
                {
                    Toast.makeText(CreateActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent addIntent = new Intent(CreateActivity.this, MainActivity.class);
                    startActivity(addIntent);
                    finish();
                }
                else {
                    Toast.makeText(CreateActivity.this, "Registration error!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void radioTapped(View view) {
        switch (view.getId()) {
            case R.id.Lost:
                type = Lost.getText().toString();
                break;
            case R.id.Found:
                type = Found.getText().toString();
                break;

            default:
                break;
        }
    }
}