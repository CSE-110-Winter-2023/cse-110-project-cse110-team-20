package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
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

    private int zoomLevel = 4; // technically number of circles

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

    private Button incrZoomBtn, decrZoomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        wireWidgets();

        locationService = locationService.singleton(this);
        orientationService = orientationService.singleton(this);


        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPeople = preferences.getAll().size() - 1;

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
        display = new Display(mockorientation,  new Compass(currentLocation, friendLocations));

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
            Log.d("testing", "" + orientation.getOrientationInDegrees());
            updateDisplay();
        });
    }

    private FriendListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(FriendListViewModel.class);
    }

    protected void initializeLocationMarkerWidgets() {
        /*
        app:layout_constraintCircle="@+id/compass_bg"
            app:layout_constraintCircleAngle="180"
            app:layout_constraintCircleRadius="150dp"
         */
        for (int i = 0; i < friendLocations.size(); i++) {
            TextView textView = new TextView(this);
            textView.setId(View.generateViewId());
            textView.setText(friendLocations.get(i).getLabel());
            friendLocationMarkers.add(textView);
        }

//        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT
//        );

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

    @Override
    protected void onPause(){
        super.onPause();
        //unregister orientationservice
        orientationService.unregisterSensorListeners();
    }


    void wireWidgets() {
        northPoint = (ImageView) findViewById(R.id.north_point);
        orientationText = findViewById(R.id.editOrientation);
        incrZoomBtn = findViewById(R.id.incr_btn);
        decrZoomBtn = findViewById(R.id.decr_btn);
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
        display.update(currentLocation, friendLocations, orientation);
        Map<String, Float> degreesForDisplay = display.modifyDegreesToLocations();
        Map<String, Integer> distanceForDisplay = display.modifyDistanceToLocations(360, zoomLevel);

        ConstraintLayout.LayoutParams layoutParamsNorth = (ConstraintLayout.LayoutParams) northPoint.getLayoutParams();
        layoutParamsNorth.circleAngle = degreesForDisplay.get("north");
        layoutParamsNorth.circleRadius = 375;
        northPoint.setLayoutParams(layoutParamsNorth);

        for (int i = 0; i < friendLocations.size(); i++) {
            friendLocationMarkers.get(i).setText(friendLocations.get(i).getLabel());
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
    }

    public void onDecrZoomBtnClicked(View view) {
        if (zoomLevel <= 1) return;
        zoomLevel--;
    }
}