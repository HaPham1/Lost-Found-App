package com.example.lostandfoundapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    private String type = "";
    RadioButton Lost, Found;
    EditText nameText, phoneText, desText, dateText, locationText;
    Button saveBtn, getBtn;
    DatabaseHelper db;
    LocationManager locationManager;
    LocationListener locationListener;
    Double latitude = 0.0;
    Double longitude = 0.0;
    String API_KEY = "AIzaSyDQ3mmN_mrJWQ1s5avPHjmmZJDH1YPWl9w";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }

    }

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
        getBtn = findViewById(R.id.getBtn);

        locationText.setFocusable(false);
        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(CreateActivity.this);
                startActivityForResult(intent, 100);
            }
        });


        db = new DatabaseHelper(this);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationText.setText(location.getLatitude() + ", " + location.getLongitude());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        };

        Places.initialize(getApplicationContext(), API_KEY);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(CreateActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
                else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        });

        /*AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CreateActivity.this, "Error with the API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                locationText.setText(place.getAddress());
            }
        });*/



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String phone = phoneText.getText().toString();
                String des = desText.getText().toString();
                String date = dateText.getText().toString();
                String location =locationText.getText().toString();
                long result = db.insertItem( new Item(type, name, phone, des, date, location, latitude, longitude));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            locationText.setText(place.getAddress());
            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;
        }
        else{
            Toast.makeText(CreateActivity.this, "Error with the API", Toast.LENGTH_SHORT).show();
        }
    }
}