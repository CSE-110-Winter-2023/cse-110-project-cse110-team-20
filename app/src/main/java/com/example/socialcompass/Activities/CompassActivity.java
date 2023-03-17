package com.example.socialcompass.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialcompass.Compass;
import com.example.socialcompass.Display;
import com.example.socialcompass.Models.Person;
import com.example.socialcompass.Orientation;
import com.example.socialcompass.Services.LocationService;
import com.example.socialcompass.Services.OrientationService;
import com.example.socialcompass.Point;
import com.example.socialcompass.R;
import com.example.socialcompass.Utils.CompassBg;
import com.example.socialcompass.Utils.CompassMarkers;
import com.example.socialcompass.ViewModels.FriendListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompassActivity extends AppCompatActivity {

    SharedPreferences preferences;
    FriendListViewModel viewModel;

    private int zoomLevel = 2; // technically number of circles


    private LocationService locationService;

    private LiveData<List<Person>> people;

    private long prevTime;
    private Person user;

    private OrientationService orientationService;
    public static Float device_orientation =0f;
    private Orientation orientation;


    Point currentLocation;
    List<Point> friendLocations;

    Orientation mockorientation;

    private TextView orientationText;


    private CompassBg compassBg;
    private CompassMarkers compassMarkers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        wireWidgets();

        locationService = locationService.singleton(this);
        orientationService = orientationService.singleton(this);


        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPeople = preferences.getAll().size() - 1;
        if(numPeople<1){
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

        for (int i = 1; i < numPeople; i++) {
            friendLocations.add(new Point());
        }

        ConstraintLayout layout = findViewById(R.id.Compass);
        compassBg = new CompassBg(layout, this, zoomLevel);
        compassMarkers = new CompassMarkers(friendLocations, currentLocation, orientation, layout, this);

        prevTime = 0;
        locationService.getLocation().observe(this, loc->{
            if (System.currentTimeMillis() - prevTime > 5000) {
                currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
                compassMarkers.setCurrentLocation(currentLocation);
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
            compassMarkers.setCurrentOrientation(orientation);
            // Log.d("testing", "" + orientation.getOrientationInDegrees());
            compassMarkers.update(zoomLevel);
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        orientationService.unregisterSensorListeners();
    }

    void wireWidgets() {
        orientationText = findViewById(R.id.editOrientation);
    }

    private FriendListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(FriendListViewModel.class);
    }

    public void OkbtnClicked(View view) {
        mockorientation.setOrientation(Float.parseFloat(orientationText.getText().toString()));
    }
    public int getCurrentZoomLevel() {
        return zoomLevel;
    }

    public boolean isShowingMoreDetails() {
        return zoomLevel > 1;
    }

    public boolean isDisplayingDistances() {
        return zoomLevel >= 1;
    }


    public void onIncrZoomBtnClicked(View view) {
        if (zoomLevel >= 4) return;
        zoomLevel++;
        compassBg.update(zoomLevel);
    }

    public void onDecrZoomBtnClicked(View view) {
        if (zoomLevel <= 1) return;
        zoomLevel--;
        compassBg.update(zoomLevel);
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
}