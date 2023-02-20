package com.example.socialcompass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MockOrientation implements IOrientation {
    private MutableLiveData<Float> orientation;

    MockOrientation() {
        this(0.0f);
    }
    MockOrientation(float f) {
        orientation = new MutableLiveData<Float>();
        setOrientation(f);
    }

    public void setOrientation(float f) {
        this.orientation.setValue(f);
    }

    public LiveData<Float> getOrientation() {
        return orientation;
    }
}
