package com.example.socialcompass;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.core.util.Pair;

import java.util.Optional;

public class NorthLocation implements ILocation{
    private double longitude = 85;
    private double latitude = -135;
    public Pair<Double, Double> getLocation () {
        return new Pair<Double, Double>(this.longitude, this.latitude);
    }
}
