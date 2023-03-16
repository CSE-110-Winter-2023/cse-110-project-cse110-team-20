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
    private MutableLiveData<Long> timeLastDC;
    public static LocationStatus singleton(Activity activity) {
        if (instance == null) {
            instance = new LocationStatus(activity);
        }
        return instance;
    }
    public LocationStatus(Activity activity){
        this.currentStatus = new MutableLiveData<>();
        this.timeLastDC = new MutableLiveData<>();
        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        executor = Executors.newScheduledThreadPool(1);
    }

    public void registerLocationStatus() {
        this.timeLastDC.postValue(System.currentTimeMillis()/1000); //initial startup time
        executor.scheduleAtFixedRate(() -> {
                this.currentStatus.postValue(this.locationManager.
                        isProviderEnabled(this.locationManager.GPS_PROVIDER));
                //this.currentStatus.postValue(false);//test if it is dc, since it always return true right now
                if(locationManager.isProviderEnabled(this.locationManager.GPS_PROVIDER)){ //reset timer
                    this.timeLastDC.postValue(System.currentTimeMillis()/1000);

                    //if(timeLastDC.getValue() != null)
                    //    this.timeLastDC.postValue(timeLastDC.getValue());//testing
                } else if(timeLastDC.getValue() != null){
                    this.timeLastDC.postValue(timeLastDC.getValue());//this somehow tricks AS into thinking it was updated hehe
                }
            if(currentStatus.getValue() != null)
                Log.d("L_STATUS", Boolean.toString(currentStatus.getValue()));
            if(timeLastDC.getValue() != null)
                Log.d("L_STATUS", "last connect "+ timeLastDC.getValue()
                        + " curr " + System.currentTimeMillis()/1000);
            }, 0, 5, TimeUnit.SECONDS);
    }
    public LiveData<Boolean> getLocationStatus() {
        return this.currentStatus;
    }

    public LiveData<Long> getDCtime(){
        return this.timeLastDC;
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
