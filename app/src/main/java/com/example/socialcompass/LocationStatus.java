package com.example.socialcompass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LocationStatus {
    LocationService locationService;
    private final ScheduledExecutorService executor;
    public LocationStatus(LocationService ls){
        this.locationService = ls;
        executor = Executors.newScheduledThreadPool(1);
    }

    public LiveData<Boolean> checkLocationStatus() {
        MutableLiveData<Boolean> ans = new MutableLiveData<>();
        ScheduledFuture<?> clockFuture = executor.scheduleAtFixedRate(() -> {
                ans.postValue(locationService.GPS_Enabled());
                }, 0, 5000, TimeUnit.MILLISECONDS);
        return ans;
    }
}
