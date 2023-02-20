package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private OrientationService orientationService;
    public static Float device_orientation;
    private SharedPreferences preferences;
    private TextView wpname;
    private TextView wpstatus;
    private TextView mockOrientation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        orientationService = OrientationService.singleton(this);
        orientationService.getOrientation().observe(this, orient ->{
            device_orientation = (Float)orient;
        });
        
        wireWidgets();
        preferences = getPreferences(MODE_PRIVATE);
        int numPoints = preferences.getAll().size();
        if (numPoints >= 2) {
            goToMapScreen();
        }
    }


    @Override
    protected void onPause(){
        super.onPause();
        //unregister orientationservice
        orientationService.unregisterSensorListeners();
    }

    public static Float getDevice_orientation(){
        return device_orientation;
  
    }

    private void wireWidgets() {
        wpname = findViewById(R.id.waypoint_name);
        wpstatus = findViewById(R.id.waypoint_coord);
        mockOrientation = findViewById(R.id.mock_orientation);
    }

    private void goToMapScreen() {
        String[][] ans = returnPreferences();
        Intent intent = new Intent(this, Mapscreen.class);
        intent.putExtra("point1", ans[0]);
        intent.putExtra("point2", ans[1]);
        intent.putExtra("point3", ans[2]);
        intent.putExtra("orientation", Float.parseFloat(mockOrientation.getText().toString()));
        startActivity(intent);
    }

    private void resetFields() {
        wpname.setText("");
        wpstatus.setText("");
        wpname.setHint("Input datapoint name");
        wpstatus.setHint("Input latitude and longitude");
    }

    public void AddDataClicked(View view) {
        int numPoints = preferences.getAll().size();

        if (wpname.getText().toString().equals("") ||
                wpstatus.getText().toString().equals("")
                    ||mockOrientation.getText().toString().equals("")) {
            //if user just tried to input text without changing inputs
            Utilities.showAlert(this, "Invalid Input");
            return;
        }

        saveProfile();

        if (numPoints >= 2) {   //make sure only 3 values are stored
            goToMapScreen();
        } else{
            resetFields();
        }
        return;
    }
    private String getDataPoint(String wpname) {
        String s = preferences.getString(wpname, "default_if_not_found");
        return s;
    }

    //this method should return all preferences in an array of string
    //it should be easy to convert from this into "point" or "compass"
    private String[][] returnPreferences(){
        String[][] ans = new String[3][2];
        Map<String, String> mp = (Map<String, String>) preferences.getAll();
        Object[] keys = mp.keySet().toArray();
        Object[] vals = mp.values().toArray();
        for(int i=0; i<3; i++){
            ans[i][0] = (String)keys[i];
            ans[i][1] = (String)vals[i];
        }
        return ans;
    }

    private void saveProfile() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(wpname.getText().toString(), wpstatus.getText().toString());
        editor.apply();
    }


}