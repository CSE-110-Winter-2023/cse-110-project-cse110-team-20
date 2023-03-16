package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompassActivity extends AppCompatActivity {

    SharedPreferences preferences;
    FriendListViewModel viewModel;

    private int zoomLevel = 2; // technically number of circles
    private int MAX_ZOOM_LEVEL = 4;

    private int MAX_RADIUS = 408;   // divisible by 2, 3, and 4

    private int DEFAULT_TEXT_SIZE = 14;

    private LocationService locationService;

    private LiveData<List<Person>> people;

    private long prevTime;
    private Person user;

    private OrientationService orientationService;
    public static Float device_orientation =0f;
    private Orientation orientation;

    private Display display;

    Point currentLocation;
    List<Point> friendLocations;

    List<TextView> friendLocationMarkers;

    Orientation mockorientation;

    private TextView orientationText;

    private ImageView northPoint;

    List<ImageView> compassCircles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        wireWidgets();

        locationService = locationService.singleton(this);
        orientationService = orientationService.singleton(this);


        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPeople = preferences.getAll().size() - 1;

        if (numPeople < 1) {
            numPeople = 1;
        }

        String userUID = preferences.getString("0", "default_if_not_found");
        String[] friendsUID = new String[numPeople - 1];

        for (int i = 1; i < numPeople; i++) {
            friendsUID[i - 1] = preferences.getString("" + i, "default_if_not_found");
        }

        viewModel = setupViewModel();
        people = viewModel.getRemoteFriends(friendsUID);
        people.observe(this, locList->{
            for (int i = 0; i < locList.size(); i++) {
                Log.d("Compass Test", locList.get(i).toString());
                friendLocations.get(i).setLabel(locList.get(i).name);
                friendLocations.get(i).setLocation(locList.get(i).latitude, locList.get(i).longitude);
            }
        });

        Log.d("Compass Test", userUID);

        String userName = preferences.getString("name", "");
        user = new Person(userName, userUID, 0,0);

        getOrientation();

        currentLocation = new Point();
        friendLocations = new ArrayList<>();
        friendLocationMarkers = new ArrayList<>();
        for (int i = 1; i < numPeople; i++) {
            friendLocations.add(new Point());
        }
        display = new Display(mockorientation, new Compass(currentLocation, friendLocations));

        initializeCompassCircles();
        updateCompassCircles(zoomLevel);

        initializeLocationMarkerWidgets();


        prevTime = 0;
        locationService.getLocation().observe(this, loc->{
            if (System.currentTimeMillis() - prevTime > 5000) {
                currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
                user.latitude = loc.first.floatValue();
                user.longitude = loc.second.floatValue();
                viewModel.updateUser(user);
                prevTime = System.currentTimeMillis();
            }
        });
        orientationService.getOrientation().observe(this, orient ->{
            device_orientation = (Float)orient;
            orientation.setOrientationFromRadians(device_orientation);
            orientation.setOrientation(orientation.getOrientationInDegrees() + mockorientation.getOrientationInDegrees());
            // Log.d("testing", "" + orientation.getOrientationInDegrees());
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
        northPoint = (ImageView) findViewById(R.id.north_point);
        orientationText = findViewById(R.id.editOrientation);
    }

    private FriendListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(FriendListViewModel.class);
    }

    protected void initializeLocationMarkerWidgets() {
        for (int i = 0; i < friendLocations.size(); i++) {
            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(friendLocations.get(i).getLabel());
            textView.setTextSize(DEFAULT_TEXT_SIZE);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            friendLocationMarkers.add(textView);
        }

        // Add the TextView widget to the activity's layout
        ConstraintLayout layout = findViewById(R.id.Compass);
        for (int i = 0; i < friendLocations.size(); i++) {
            layout.addView(friendLocationMarkers.get(i));

            // Get the ConstraintSet for the layout
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);

            // Set the circle constraint for the TextView
            constraintSet.connect(friendLocationMarkers.get(i).getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(friendLocationMarkers.get(i).getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START);
            constraintSet.connect(friendLocationMarkers.get(i).getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
            constraintSet.constrainCircle(friendLocationMarkers.get(i).getId(), layout.getId(), 300, i * 30);

            // Apply the constraints to the layout
            constraintSet.applyTo(layout);
        }
    }

    protected void initializeCompassCircles() {
        compassCircles = new ArrayList<>();
        for (int i = 0; i < MAX_ZOOM_LEVEL; i++) {
            ImageView circle = new ImageView(this);
            circle.setId(View.generateViewId());
            circle.setImageResource(R.drawable.outline_circle);
            ConstraintLayout layout = findViewById(R.id.Compass);
            layout.addView(circle);
            // Get the ConstraintSet for the layout
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);

            // Set the circle constraint for the TextView
            constraintSet.connect(circle.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(circle.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START);
            constraintSet.connect(circle.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
            constraintSet.constrainCircle(circle.getId(), layout.getId(), 0, 0);

            // Apply the constraints to the layout
            constraintSet.applyTo(layout);

            compassCircles.add(circle);
        }
    }



    protected void updateCompassCircles(int zoomLevel) {
        for (int i = 0; i < MAX_ZOOM_LEVEL; i++) {
            compassCircles.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 1; i <= zoomLevel; i++) {
            int radius = MAX_RADIUS * i / zoomLevel;
            compassCircles.get(i - 1).setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = compassCircles.get(i - 1).getLayoutParams();
            params.width = radius * 2;
            params.height = radius * 2;
            compassCircles.get(i - 1).setLayoutParams(params);
        }
    }

    void getOrientation(){
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mockorientation = new Orientation(extras.getFloat("orientation"));
        } else {
            mockorientation = new Orientation();
        }

        orientation = new Orientation(device_orientation);

    }
    void updateDisplay() {
        display.update(currentLocation, friendLocations, orientation);
        Map<String, Float> degreesForDisplay = display.modifyDegreesToLocations();
        Map<String, Integer> distanceForDisplay = display.modifyDistanceToLocations(MAX_RADIUS, zoomLevel);

        ConstraintLayout.LayoutParams layoutParamsNorth = (ConstraintLayout.LayoutParams) northPoint.getLayoutParams();
        layoutParamsNorth.circleAngle = degreesForDisplay.get("north");
        layoutParamsNorth.circleRadius = 375;
        northPoint.setLayoutParams(layoutParamsNorth);

        for (int i = 0; i < friendLocations.size(); i++) {
            if (distanceForDisplay.get(friendLocations.get(i).getLabel()) < MAX_RADIUS) {
                // Not on outer edge
                friendLocationMarkers.get(i).setTextSize(DEFAULT_TEXT_SIZE);
                friendLocationMarkers.get(i).setText(friendLocations.get(i).getLabel());
            } else {
                friendLocationMarkers.get(i).setTextSize(30);
                friendLocationMarkers.get(i).setText("●");
            }
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) friendLocationMarkers.get(i).getLayoutParams();
            layoutParams.circleAngle = degreesForDisplay.get(friendLocations.get(i).getLabel());
            layoutParams.circleRadius = distanceForDisplay.get(friendLocations.get(i).getLabel());
            friendLocationMarkers.get(i).setLayoutParams(layoutParams);
        }
    }

    public void OkbtnClicked(View view) {
        mockorientation.setOrientation(Float.parseFloat(orientationText.getText().toString()));
        updateDisplay();
    }

    public void onIncrZoomBtnClicked(View view) {
        if (zoomLevel >= 4) return;
        zoomLevel++;
        updateCompassCircles(zoomLevel);
    }

    public void onDecrZoomBtnClicked(View view) {
        if (zoomLevel <= 1) return;
        zoomLevel--;
        updateCompassCircles(zoomLevel);
    }
}