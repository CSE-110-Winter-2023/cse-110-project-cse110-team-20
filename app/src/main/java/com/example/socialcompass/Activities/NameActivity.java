package com.example.socialcompass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.socialcompass.Point;
import com.example.socialcompass.R;
import com.example.socialcompass.Services.LocationService;

public class NameActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private LocationService locationService;
    private TextView name;
    private Point currentLocation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        requestLocationPermissions();
        
        wireWidgets();

        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPoints = preferences.getAll().size();
        if (numPoints >= 1) {
            goToCompassActivity();
        }

        locationService = locationService.singleton(this);

        currentLocation = new Point();
        locationService.getLocation().observe(this, loc->{
            currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
        });
    }

    private void wireWidgets() {
        name = findViewById(R.id.name);
    }

    void requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    public void onClickOK(View view) {
        saveUsername();
        goToDisplayUIDActivity();
    }

    private void goToCompassActivity() {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    private void goToDisplayUIDActivity() {
        Intent intent = new Intent(this, DisplayUIDActivity.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("lat", currentLocation.getLatitude());
        intent.putExtra("lon", currentLocation.getLongitude());
        startActivity(intent);
    }

    private void saveUsername() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name.getText().toString());
        editor.apply();
    }

}