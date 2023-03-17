package com.example.socialcompass;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LocationStatus {/*
    private static LocationStatus instance;
    private Activity activity;
    private final ScheduledExecutorService executor;
    private LocationManager locationManager;
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
    public void setMockStatus(Boolean b, Long l){
        currentStatus.setValue(b);
        timeLastDC.setValue(l);
    }

    public void registerLocationStatus() {
        this.timeLastDC.postValue(System.currentTimeMillis()/1000); //initial startup time
        executor.scheduleAtFixedRate(() -> {
                Boolean gps_status = Boolean.FALSE;
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    gps_status = Boolean.TRUE;
                currentStatus.postValue(gps_status);
                //this.currentStatus.postValue(false);//test if it is dc, since it always return true right now
                if(gps_status){ //reset timer
                    timeLastDC.postValue(System.currentTimeMillis()/1000);

                    //if(timeLastDC.getValue() != null)
                    //    this.timeLastDC.postValue(timeLastDC.getValue());//testing
                } else if(timeLastDC.getValue() != null){
                    timeLastDC.postValue(timeLastDC.getValue());//this somehow tricks AS into thinking it was updated hehe
                }
            if(currentStatus.getValue() != null)
                Log.d("L_STATUS", Boolean.toString(currentStatus.getValue()));
            if(timeLastDC.getValue() != null)
                Log.d("L_STATUS", "last connect "+ timeLastDC.getValue()
                        + " curr " + System.currentTimeMillis()/1000);
            }, 0, 5, TimeUnit.SECONDS);

        /*TimerTask tt = new TimerTask(){
            @Override
            public void run(){
                currentStatus.postValue(locationManager.
                        isProviderEnabled(LocationManager.GPS_PROVIDER));
                //this.currentStatus.postValue(false);//test if it is dc, since it always return true right now
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){ //reset timer
                    timeLastDC.postValue(System.currentTimeMillis()/1000);

                    //if(timeLastDC.getValue() != null)
                    //    this.timeLastDC.postValue(timeLastDC.getValue());//testing
                } else if(timeLastDC.getValue() != null){
                    timeLastDC.postValue(timeLastDC.getValue());//this somehow tricks AS into thinking it was updated hehe
                }
                if(currentStatus.getValue() != null)
                    Log.d("L_STATUS", Boolean.toString(currentStatus.getValue()));
                if(timeLastDC.getValue() != null)
                    Log.d("L_STATUS", "last connect "+ timeLastDC.getValue()
                            + " curr " + System.currentTimeMillis()/1000);
            }
        };
        new Timer().scheduleAtFixedRate(tt, 0, 5000);
    }
    public LiveData<Boolean> getLocationStatus() {
        return currentStatus;
    }

    public LiveData<Long> getDCtime(){
        return timeLastDC;
    }


        This method returns boolean checking whether GPS status is connected
        or not, add this into the same thread that gets location in real time.
        Probably do it in an if/else statement

    public LiveData<Boolean> GPS_Enabled(){
        //get the current provider
        Log.i("L_SERVICE", "reached here");
        if(!locationManager.isLocationEnabled())
            return false;
        return locationManager.isProviderEnabled(this.locationManager.GPS_PROVIDER);
    }
    */
}
