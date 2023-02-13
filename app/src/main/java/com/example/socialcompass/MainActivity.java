package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //make this preferences public so compass can access it?
    public SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AddDataClicked(View view) {
        if(preferences.getAll().size()>3){//make sure only 3 values are stored
            //show an alert saying 3 datapoints have already been added
            Utilities.showAlert(this, "3 datapoints have already been added");
        }
        else{
            TextView wpname = findViewById(R.id.waypoint_name);
            TextView wpstatus = findViewById(R.id.waypoint_coord);
            if(wpname.getText().toString().equals("Input datapoint name") &&
                    wpstatus.getText().toString().equals("Input lattitude and longitude")){
                //if user just tried to input text without changing inputs
                Utilities.showAlert(this, "Please change lattitude and longitude");
                return;
            }
            saveProfile();
            wpname.setText("Input datapoint name");
            wpname.setText("Input lattitude and longitude");
        }
        return;
    }
    public String getDataPoint(String wpname) {
        String s = preferences.getString(wpname, "default_if_not_found");
        return s;
    }

    //this method should return all preferences in an array of string
    //it should be easy to convert from this into "point" or "compass"
    public String[][] returnPreferences(){
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

    public void saveProfile() {
        SharedPreferences.Editor editor = preferences.edit();
        TextView wpname = findViewById(R.id.waypoint_name);
        TextView wpstatus = findViewById(R.id.waypoint_coord);
        editor.putString(wpname.getText().toString(), wpstatus.getText().toString());

        editor.apply();
    }
}