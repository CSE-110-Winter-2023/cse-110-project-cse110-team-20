package com.example.socialcompass;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompassActivity extends AppCompatActivity {

    SharedPreferences preferences;
    FriendListViewModel viewModel;

    private LocationService locationService;

    private LiveData<List<Person>> people;

    public LiveData<Integer> disconnect_time;//livedata for the timer since dc?
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

    private LocationStatus locationStatus;

    private TextView redLegendText;
    private TextView yellowLegendText;
    private TextView greenLegendText;
    private TextView orientationText;
    private ImageView redPoint;
    private ImageView yellowPoint;
    private ImageView greenPoint;
    private ImageView northPoint;

    //TODO: add this for US4 stuff MS2
    private ImageView gps_status;
    private TextView time_since_disconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        wireWidgets();

        locationService = locationService.singleton(this);
        orientationService = orientationService.singleton(this);


        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPeople = preferences.getAll().size();

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

        user = new Person("", userUID, 0,0);


        getOrientation();

        currentLocation = new Point();
        friendLocations = new ArrayList<>();
        friendLocationMarkers = new ArrayList<>();
        for (int i = 1; i < numPeople; i++) {
            friendLocations.add(new Point());
        }
        display = new Display(mockorientation,  new Compass(currentLocation, friendLocations));

        initializeLocationMarkerWidgets();

        //TODO: make class that runs a scheduled executor for whether location is enabled
        locationStatus = new LocationStatus(locationService);
        disconnect_time = new MutableLiveData<Integer>();//unsure how to do this, check lab 5 maybe
        locationStatus.checkLocationStatus().observe(this, status->{
            if(!status){//it is disconnected here
                //TODO: add code to change compassactivity and timer's text
                //add 5 seconds each time its disconnected so match the 5 second ping time?
                //disconnect_time.setValue(disconnect_time.getValue()+5000);
                gps_status.setColorFilter(RED);//change the filter of the imageview so its red??
            } else{//it reconnected, set the timer back to 0
                //disconnect_time.setValue(0);
                gps_status.setColorFilter(GREEN);
            }
            //time_since_disconnect.setText(disconnect_time.getValue());
        });
        //insert code

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
        redLegendText = (TextView) findViewById(R.id.red_label_legend);
        yellowLegendText = (TextView) findViewById(R.id.yellow_label_legend);
        greenLegendText = (TextView) findViewById(R.id.green_label_legend);
        redPoint = (ImageView) findViewById(R.id.red_point);
        yellowPoint = (ImageView) findViewById(R.id.yellow_point);
        greenPoint = (ImageView) findViewById(R.id.green_point);
        northPoint = (ImageView) findViewById(R.id.north_point);
        orientationText = findViewById(R.id.editOrientation);
        gps_status = findViewById(R.id.connectionstatus);
        time_since_disconnect = findViewById(R.id.DC_timer);
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
        Map<String, Integer> distanceForDisplay = display.modifyDistanceToLocations(360, 4);

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
}