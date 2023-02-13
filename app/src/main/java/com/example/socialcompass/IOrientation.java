package com.example.socialcompass;

public interface IOrientation {
    // return the orientation of the device relative to true north in radians
    // where 0 is north, pi/2 is east, -pi/2 is west, etc.
    // ideally, this would return a value in the range [-pi, pi]
    // which is the range of android's sensormanager.getorientation()
    float getOrientationVal();
}
