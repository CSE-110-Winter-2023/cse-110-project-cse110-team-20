package com.example.socialcompass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Orientation implements IOrientation {
    private MutableLiveData<Float> orientation;

    Orientation() {
        this(0.0f);
    }
    Orientation(float f) {
        orientation = new MutableLiveData<Float>();
        setOrientation(f);
    }

    public void setOrientationFromRadians(float radians) {
        setOrientation((((float)Math.toDegrees(radians) + 360) % 360));
    }

    public void setOrientation(float f) {
        this.orientation.setValue(f);
    }

    public LiveData<Float> getOrientation() {
        return orientation;
    }

    public float getOrientationInDegrees() {
        return orientation.getValue();
    }
}
