package com.example.socialcompass;

import static com.example.socialcompass.Mapscreen.device_orientation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    private LocationService locationService;
    private TextView name;
    private Point currentLocation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestLocationPermissions();
        
        wireWidgets();

        preferences = getSharedPreferences("main", MODE_PRIVATE);
        int numPoints = preferences.getAll().size();
        if (numPoints >= 1) {
            goToCompass();
        }

        locationService = locationService.singleton(this);

        currentLocation = new Point();
        locationService.getLocation().observe(this, loc->{
            currentLocation.setLocation(loc.first.floatValue(), loc.second.floatValue());
            if (currentLocation != null) {
                // api.put(name, UID, currentLocation.getLatitude(), currentLocation.getLongitude());
                Log.d("API-Test", "observer running");
            }
        });
    }

    private void goToCompass() {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
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

//    private void goToMapScreen() {
//        String[][] ans = returnPreferences();
//        Intent intent = new Intent(this, Mapscreen.class);
//        intent.putExtra("point1", ans[0]);
//        intent.putExtra("point2", ans[1]);
//        intent.putExtra("point3", ans[2]);
//        intent.putExtra("orientation", Float.parseFloat(mockOrientation.getText().toString()));
//        startActivity(intent);
//    }

    public static Float getDevice_orientation(){
        return device_orientation;
    }

    public void onClickOK(View view) {
        Intent intent = new Intent(this, DisplayUIDActivity.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("lat", currentLocation.getLatitude());
        intent.putExtra("lon", currentLocation.getLongitude());
        startActivity(intent);
    }

//    public void AddDataClicked(View view) {
//        int numPoints = preferences.getAll().size();
//
//        if (wpname.getText().toString().equals("") ||
//                wpstatus.getText().toString().equals("")
//                    ||mockOrientation.getText().toString().equals("")) {
//            //if user just tried to input text without changing inputs
//            Utilities.showAlert(this, "Invalid Input");
//            return;
//        }
//
//        saveProfile();
//
//        if (numPoints >= 2) {   //make sure only 3 values are stored
//            goToMapScreen();
//        } else{
//            resetFields();
//        }
//        return;
//    }
//    private String getDataPoint(String wpname) {
//        String s = preferences.getString(wpname, "default_if_not_found");
//        return s;
//    }
//
//    //this method should return all preferences in an array of string
//    //it should be easy to convert from this into "point" or "compass"
//    private String[][] returnPreferences(){
//        String[][] ans = new String[3][2];
//        Map<String, String> mp = (Map<String, String>) preferences.getAll();
//        Object[] keys = mp.keySet().toArray();
//        Object[] vals = mp.values().toArray();
//        for(int i=0; i<3; i++){
//            ans[i][0] = (String)keys[i];
//            ans[i][1] = (String)vals[i];
//        }
//        return ans;
//    }
//
//    private void saveProfile() {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(wpname.getText().toString(), wpstatus.getText().toString());
//        editor.apply();
//    }


}