package com.example.socialcompass.Services;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialcompass.IOrientation;

// OrientationService taken from Demo 5
public class OrientationService implements SensorEventListener, IOrientation {
    private static OrientationService instance;

    private final SensorManager sensorManager;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private MutableLiveData<Float> azimuth;
    private MutableLiveData<float[]> mockAccelerometerSource = new MutableLiveData<>();
    private MutableLiveData<float[]> mockMagneticFieldSource;


    //azimuth=0 is north

    /**
     * Constructor for OrientationService
     * Do not use; instead get an OrientationService object from singleton()
     *
     * @param activity Context needed to initiate SensorManager
     */
    protected OrientationService(Activity activity) {
        this.azimuth = new MutableLiveData<>();
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.registerSensorListeners();
    }

    /**
     * Get the OrientationService singleton object
     *
     * @param activity Context needed to initiate SensorManager
     * @return the OrientationService singleton object
     */
    public static OrientationService singleton(Activity activity) {
        if (instance == null) {
            instance = new OrientationService(activity);
        }
        return instance;
    }

    /**
     * Get orientation
     * @return LiveData object that updates with orientation
     */
    public LiveData<Float> getOrientation() {
        return this.azimuth;
    }

    /**
     * Overwrite the class LiveData with an external mock data source
     * @param mockDataSource mock data source
     */

    public void setMockOrientationSource(MutableLiveData<Float> mockDataSource) {
        unregisterSensorListeners();
        this.azimuth = mockDataSource;
    }
    /**
     * Unregister the sensors after done using all instances of OrientationService
     */
    public void unregisterSensorListeners() {
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerReading = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnetometerReading = event.values;
        }

        if (accelerometerReading != null && magnetometerReading != null) {
            onBothSensorDataAvailable();
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    /**
     * Method called in constructor for sensor setup
     */
    private void registerSensorListeners() {
        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Method called in onSensorChanged to update orientation value
     */
    private void onBothSensorDataAvailable() {
        // double check
        if (accelerometerReading == null || magnetometerReading == null) {
            throw new IllegalStateException("Both sensors must be active to compute orientation.");
        }

        float[] r = new float[9];
        float[] i = new float[9];

        // convert sensor readings to rot matrix
        boolean success = SensorManager.getRotationMatrix(r, i, accelerometerReading, magnetometerReading);
        if (success) {
            float[] orientation = new float[3];
            // further convert readings to azimuth/pitch/roll values
            SensorManager.getOrientation(r, orientation);
            // and extract the azimuth
            this.azimuth.postValue(orientation[0]);
        }
    }
    public void setMockAccelerometerSource(MutableLiveData<float[]> mockDataSource) {
        unregisterSensorListeners();
        this.mockAccelerometerSource = mockDataSource;
    }

    public void setMockMagneticFieldSource(MutableLiveData<float[]> mockDataSource) {
        unregisterSensorListeners();
        this.mockMagneticFieldSource = mockDataSource;
    }




}
