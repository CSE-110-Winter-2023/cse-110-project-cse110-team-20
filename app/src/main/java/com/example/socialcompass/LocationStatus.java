package com.example.socialcompass;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LocationStatus {
    private static LocationStatus instance;
    private Activity activity;
    private final ScheduledExecutorService executor;
    private final LocationManager locationManager;
    private MutableLiveData<Boolean> currentStatus;
    private MutableLiveData<Integer> timeSinceDC;
    public static LocationStatus singleton(Activity activity) {
        if (instance == null) {
            instance = new LocationStatus(activity);
        }
        return instance;
    }
    public LocationStatus(Activity activity){
        this.currentStatus = new MutableLiveData<>();
        this.timeSinceDC = new MutableLiveData<>();
        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        executor = Executors.newScheduledThreadPool(1);
    }

    public LiveData<Boolean> checkLocationStatus() {
        executor.scheduleAtFixedRate(() -> {
            if(!locationManager.isLocationEnabled()){
                currentStatus.setValue(false);//is it bc its postvalue vs setvalue?
                //add to timer
                //timeSinceDC.setValue(timeSinceDC.getValue() + 5);
            } else{
                currentStatus.setValue(locationManager.isProviderEnabled(this.locationManager.GPS_PROVIDER));
                if(locationManager.isProviderEnabled(this.locationManager.GPS_PROVIDER)){ //reset timer
                    //timeSinceDC.setValue(0);
                } else{//add to timer
                    //timeSinceDC.setValue(timeSinceDC.getValue() + 5);
                }
            }
            Log.i("L_STATUS", currentStatus.toString());
            }, 0, 5000, TimeUnit.MILLISECONDS);
        return currentStatus;
    }

    public LiveData<Integer> getDCtime(){
        return this.timeSinceDC;
    }

    /*
        This method returns boolean checking whether GPS status is connected
        or not, add this into the same thread that gets location in real time.
        Probably do it in an if/else statement

    public LiveData<Boolean> GPS_Enabled(){
        //get the current provider
        Log.i("L_SERVICE", "reached here");
        if(!locationManager.isLocationEnabled())
            return false;
        return locationManager.isProviderEnabled(this.locationManager.GPS_PROVIDER);
    }*/
}
