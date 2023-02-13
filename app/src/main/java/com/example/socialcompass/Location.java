package com.example.socialcompass;

import androidx.core.util.Pair;

public class Location implements ILocation {
    private double longitude;
    private double latitude;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Pair<Double, Double> getLocation () {
        return new Pair<Double, Double>(this.longitude, this.latitude);
    }
}