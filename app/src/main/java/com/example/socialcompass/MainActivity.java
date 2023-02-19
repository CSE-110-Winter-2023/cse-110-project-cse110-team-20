package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private OrientationService orientationService;
    public static Float device_orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orientationService = OrientationService.singleton(this);
        orientationService.getOrientation().observe(this, orient ->{
            device_orientation = (Float)orient;
        });
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
}