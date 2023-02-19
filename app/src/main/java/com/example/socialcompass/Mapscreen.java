package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class Mapscreen extends AppCompatActivity {

    private LocationService locationService;

    private Display display;

    Point point1, point2, point3, currentLocation;

    private TextView redLegendText;
    private TextView yellowLegendText;
    private TextView greenLegendText;
    private ImageView redPoint;
    private ImageView yellowPoint;
    private ImageView greenPoint;
    private ImageView northPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        requestLocationPermissions();

        wireWidgets();

        locationService = locationService.singleton(this);

        getPoints();
        setLegends();

        currentLocation = new Point();
        display = new Display(new MockOrientation(),  new Compass(currentLocation, point2, point3, point1));

        locationService.getLocation().observe(this, loc->{
            currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
            updateDisplay();
        });
    }

    void requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    void wireWidgets() {
        redLegendText = (TextView) findViewById(R.id.red_label_legend);
        yellowLegendText = (TextView) findViewById(R.id.yellow_label_legend);
        greenLegendText = (TextView) findViewById(R.id.green_label_legend);
        redPoint = (ImageView) findViewById(R.id.red_point);
        yellowPoint = (ImageView) findViewById(R.id.yellow_point);
        greenPoint = (ImageView) findViewById(R.id.green_point);
        northPoint = (ImageView) findViewById(R.id.north_point);
    }

    void setLegends() {
        redLegendText.setText(point1.getLabel());
        yellowLegendText.setText(point2.getLabel());
        greenLegendText.setText(point3.getLabel());
    }

    void getPoints() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            point1 = new Point(extras.getStringArray("point1"));
            point2 = new Point(extras.getStringArray("point2"));
            point3 = new Point(extras.getStringArray("point3"));
        } else {
            point1 = new Point();
            point2 = new Point();
            point3 = new Point();
        }

        Log.d("test", point1.toString());
        Log.d("test", point2.toString());
        Log.d("test", point3.toString());
    }

    void updateDisplay() {
        display.update(currentLocation);
        Map<String, Float> degreesForDisplay = display.modifyDegreesToLocations();

        ConstraintLayout.LayoutParams layoutParamsRed = (ConstraintLayout.LayoutParams) redPoint.getLayoutParams();
        layoutParamsRed.circleAngle = degreesForDisplay.get("home");
        redPoint.setLayoutParams(layoutParamsRed);

        ConstraintLayout.LayoutParams layoutParamsYellow = (ConstraintLayout.LayoutParams) yellowPoint.getLayoutParams();
        layoutParamsYellow.circleAngle = degreesForDisplay.get("parent");
        yellowPoint.setLayoutParams(layoutParamsYellow);

        ConstraintLayout.LayoutParams layoutParamsGreen = (ConstraintLayout.LayoutParams) greenPoint.getLayoutParams();
        layoutParamsGreen.circleAngle = degreesForDisplay.get("friend");
        greenPoint.setLayoutParams(layoutParamsGreen);

        ConstraintLayout.LayoutParams layoutParamsNorth = (ConstraintLayout.LayoutParams) northPoint.getLayoutParams();
        layoutParamsNorth.circleAngle = degreesForDisplay.get("north");
        northPoint.setLayoutParams(layoutParamsNorth);
    }

}