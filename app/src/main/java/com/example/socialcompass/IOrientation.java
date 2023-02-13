package com.example.socialcompass;

import androidx.lifecycle.LiveData;

public interface IOrientation {
    // return the direction the device is pointed in
    LiveData<Float> getOrientation();
}
