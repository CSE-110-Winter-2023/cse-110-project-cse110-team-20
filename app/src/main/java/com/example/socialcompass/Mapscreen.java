package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

public class Mapscreen extends AppCompatActivity {

    private LocationService locationService;

    private OrientationService orientationService;
    public static Float device_orientation =0f;
    private Orientation orientation;

    private Display display;

    Point point1, point2, point3, currentLocation;

    Orientation mockorientation;

    private TextView redLegendText;
    private TextView yellowLegendText;
    private TextView greenLegendText;
    private TextView orientationText;
    private ImageView redPoint;
    private ImageView yellowPoint;
    private ImageView greenPoint;
    private ImageView northPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        wireWidgets();

        locationService = locationService.singleton(this);
        orientationService = orientationService.singleton(this);

        getPoints();
        getOrientation();
        setLegends();

        currentLocation = new Point();
        display = new Display(mockorientation,  new Compass(currentLocation, point2, point3, point1));

        locationService.getLocation().observe(this, loc->{
            currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
            updateDisplay();
        });
        orientationService.getOrientation().observe(this, orient ->{
            device_orientation = (Float)orient;
            orientation.setOrientationFromRadians(device_orientation);
            orientation.setOrientation(orientation.getOrientationInDegrees() + mockorientation.getOrientationInDegrees());
            Log.d("testing", "" + orientation.getOrientationInDegrees());
            updateDisplay();
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        //unregister orientationservice
        orientationService.unregisterSensorListeners();
    }


    void wireWidgets() {
        redLegendText = (TextView) findViewById(R.id.red_label_legend);
        yellowLegendText = (TextView) findViewById(R.id.yellow_label_legend);
        greenLegendText = (TextView) findViewById(R.id.green_label_legend);
        redPoint = (ImageView) findViewById(R.id.red_point);
        yellowPoint = (ImageView) findViewById(R.id.yellow_point);
        greenPoint = (ImageView) findViewById(R.id.green_point);
        northPoint = (ImageView) findViewById(R.id.north_point);
        orientationText = findViewById(R.id.editOrientation);
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
    void getOrientation(){
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mockorientation = new Orientation(extras.getFloat("orientation"));
        } else {
            mockorientation= new Orientation();
        }

        orientation = new Orientation(device_orientation);

    }
    void updateDisplay() {
        display.update(currentLocation, orientation);
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

    public void OkbtnClicked(View view) {
        mockorientation.setOrientation(Float.parseFloat(orientationText.getText().toString()));
        updateDisplay();
    }
}