package com.example.socialcompass;

public class MockOrientation implements IOrientation {
    private double orientation;

    MockOrientation(double d) {
        setOrientation(d);
    }

    MockOrientation() {
        setOrientation(0.0);
    }

    public void setOrientation(double d) {
        this.orientation = d;
    }

    public double getOrientation() {
        return orientation;
    }
}
