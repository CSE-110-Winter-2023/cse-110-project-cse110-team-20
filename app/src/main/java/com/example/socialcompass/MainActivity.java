package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //make this preferences public so compass can access it?

    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);
        int numPoints = preferences.getAll().size();
        if (numPoints >= 2) { //make sure only 3 values are stored
            String[][] ans = returnPreferences();
            Intent intent = new Intent(this, Mapscreen.class);
            intent.putExtra("point1", ans[0]);
            intent.putExtra("point2", ans[1]);
            intent.putExtra("point3", ans[2]);
            startActivity(intent);
        }

        try {
            String[][] ans = returnPreferences();
            for (int i = 0; i < 3; i++) {
                Log.d("store", ans[i][0]);
                Log.d("store", ans[i][1]);
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
        }
    }

    public void AddDataClicked(View view) {
        int numPoints = preferences.getAll().size();
        Log.d("points", ""+numPoints);
        TextView wpname = findViewById(R.id.waypoint_name);
        TextView wpstatus = findViewById(R.id.waypoint_coord);
        if(wpname.getText().toString().equals("") &&
                wpstatus.getText().toString().equals("")){
            //if user just tried to input text without changing inputs
            Utilities.showAlert(this, "Invalid Input");
            return;
        }
        saveProfile();

        if(numPoints >= 2){//make sure only 3 values are stored
            String[][] ans = returnPreferences();
            Intent intent = new Intent(this, Mapscreen.class);
            intent.putExtra("point1", ans[0]);
            intent.putExtra("point2", ans[1]);
            intent.putExtra("point3", ans[2]);
            startActivity(intent);
        }
        else{
            wpname.setText("");
            wpstatus.setText("");
            wpname.setHint("Input datapoint name");
            wpstatus.setHint("Input lattitude and longitude");
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