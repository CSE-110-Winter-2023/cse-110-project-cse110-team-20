package com.example.socialcompass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialcompass.Models.Person;
import com.example.socialcompass.Orientation;
import com.example.socialcompass.Services.LocationService;
import com.example.socialcompass.Services.OrientationService;
import com.example.socialcompass.Point;
import com.example.socialcompass.R;
import com.example.socialcompass.Utils.CompassBg;
import com.example.socialcompass.Utils.CompassMarkers;
import com.example.socialcompass.Utils.GPSSignalStatus;
import com.example.socialcompass.ViewModels.FriendListViewModel;

import java.util.ArrayList;
import java.util.List;

public class CompassActivity extends AppCompatActivity {

    private final int REFRESH_INTERVAL_MILLIS = 5000;

    private SharedPreferences preferences;
    private FriendListViewModel viewModel;

    private int numPeople;  // user + number of friends
    private Person user;
    private LiveData<List<Person>> friends;
    private Point currentLocation;
    private List<Point> friendLocations;

    private long prevTime = 0;
    private int zoomLevel = 2; // technically number of circles

    private LocationService locationService;
    private OrientationService orientationService;
    public static Float device_orientation = 0f;
    private Orientation orientation;
    Orientation mockorientation;

    private ConstraintLayout layout;
    private TextView timer;
    private ImageView statusIcon;
    private TextView urlText;

    private CompassBg compassBg;
    private CompassMarkers compassMarkers;
    private GPSSignalStatus signalStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        wireWidgets();

        initializeServices();
        initializeStorage();
        initializeUser();
        initializeFriends();
        initializeLocations();
        initializeOrientation();
        initializeViewComponenets();

        // Observers
        friends.observe(this, this::updateFriendLocations);
        locationService.getLocation().observe(this, this::updateLocations);
        orientationService.getOrientation().observe(this, this::updateOrientations);
    }

    @Override
    protected void onPause(){
        super.onPause();
        orientationService.unregisterSensorListeners();
    }

    void wireWidgets() {
        layout = findViewById(R.id.Compass);
        timer = findViewById(R.id.DC_timer);
        statusIcon = findViewById(R.id.connection_status);
        urlText = findViewById(R.id.editApiUrl);
    }

    private FriendListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(FriendListViewModel.class);
    }

    public void OkbtnClicked(View view) {
        viewModel.mockUrl(urlText.getText().toString());
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



    private void initializeStorage() {
        preferences = getSharedPreferences("main", MODE_PRIVATE);
        numPeople = Math.max(1, preferences.getAll().size() - 1);
    }

    private void initializeServices() {
        locationService = locationService.singleton(this);
        orientationService = orientationService.singleton(this);
    }

    void initializeOrientation(){
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mockorientation = new Orientation(extras.getFloat("orientation"));
        } else {
            mockorientation = new Orientation();
        }
        orientation = new Orientation(device_orientation);
    }

    void initializeLocations() {
        currentLocation = new Point();
        friendLocations = new ArrayList<>();

        for (int i = 1; i < numPeople; i++) {
            friendLocations.add(new Point());
        }
    }

    void initializeUser() {
        String userName = preferences.getString("name", "no name");
        String userUID = preferences.getString("0", "no UID");
        user = new Person(userName, userUID, 0,0);
    }

    private void initializeViewComponenets() {
        compassBg = new CompassBg(layout, this, zoomLevel);
        compassMarkers = new CompassMarkers(friendLocations, currentLocation, orientation, layout, this);
        signalStatus = new GPSSignalStatus(timer, statusIcon);
    }

    private void initializeFriends() {
        String[] friendsUID = new String[numPeople - 1];

        for (int i = 1; i < numPeople; i++) {
            friendsUID[i - 1] = preferences.getString("" + i, "no friend");
        }

        viewModel = setupViewModel();
        friends = viewModel.getRemoteFriends(friendsUID);
    }



    void updateLocations(Pair<Double, Double> loc) {
        signalStatus.updateTime(System.currentTimeMillis());

        if (System.currentTimeMillis() - prevTime > REFRESH_INTERVAL_MILLIS) {
            currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
            compassMarkers.setCurrentLocation(currentLocation);
            user.latitude = loc.first.floatValue();
            user.longitude = loc.second.floatValue();
            viewModel.updateUser(user);
            prevTime = System.currentTimeMillis();
        }
    }

    private void updateOrientations(Float orient) {
        device_orientation = (Float)orient;
        orientation.setOrientationFromRadians(device_orientation);
        orientation.setOrientation(orientation.getOrientationInDegrees() + mockorientation.getOrientationInDegrees());
        compassMarkers.setCurrentOrientation(orientation);
        // Log.d("testing", "" + orientation.getOrientationInDegrees());
        compassMarkers.update(zoomLevel);
        signalStatus.update();
    }

    private void updateFriendLocations(List<Person> locList) {
        for (int i = 0; i < locList.size(); i++) {
            // Log.d("Compass Test", locList.get(i).toString());
            friendLocations.get(i).setLabel(locList.get(i).name);
            friendLocations.get(i).setLocation(locList.get(i).latitude, locList.get(i).longitude);
        }
    }

}