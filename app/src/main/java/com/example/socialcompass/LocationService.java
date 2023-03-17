package com.example.socialcompass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LocationService implements LocationListener {

    private static LocationService instance;
    private Activity activity;

    private MutableLiveData<Pair<Double,Double>> locationValue;

    private final LocationManager locationManager;
    //add these from locationstatus, try this in here since we register
    // a locationlistener?
    private MutableLiveData<Boolean> currentStatus;
    private MutableLiveData<Long> timeLastDC;
    private final ScheduledExecutorService executor;

    public static LocationService singleton(Activity activity) {
        if (instance == null) {
            instance = new LocationService(activity);
        }
        return instance;
    }

    protected LocationService(Activity activity) {
        this.locationValue = new MutableLiveData<>();
        this.activity = activity;

        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        //try this here
        this.currentStatus = new MutableLiveData<>();
        this.timeLastDC = new MutableLiveData<>();
        executor = Executors.newScheduledThreadPool(1);
    // Register sensor listeners
        this.registerLocationListener();



    }

    private void registerLocationListener() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new IllegalStateException("App needs location permission to get latest location");
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        String gpss = LocationManager.GPS_PROVIDER;
        this.registerLocationStatus();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Boolean b = Boolean.FALSE;
        if(locationManager.isLocationEnabled()
                && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true)
            b = true;
        Log.d("L_SERVICE", Boolean.toString(b));
        this.locationValue.postValue(new Pair<Double, Double>(location.getLatitude(), location.getLongitude()));
    }

    private void unregisterLocationListener() {locationManager.removeUpdates(this);}

    public LiveData<Pair<Double, Double>> getLocation(){return this.locationValue;}

    public void setMockOrientationSource(MutableLiveData<Pair<Double, Double>> mockDataSource){
        unregisterLocationListener();
        this.locationValue = mockDataSource;
    }

    public void registerLocationStatus() {
        this.timeLastDC.postValue(System.currentTimeMillis()/1000); //initial startup time
        executor.scheduleAtFixedRate(() -> {
            Boolean gps_status = Boolean.FALSE;
            if(locationManager.isLocationEnabled()
                    && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gps_status = Boolean.TRUE;
            }
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
    }

    public LiveData<Boolean> getLocationStatus() {
        return currentStatus;
    }

    public LiveData<Long> getDCtime(){
        return timeLastDC;
    }
}
