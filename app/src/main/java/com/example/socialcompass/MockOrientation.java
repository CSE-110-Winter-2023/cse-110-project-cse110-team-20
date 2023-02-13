package com.example.socialcompass;

public class MockOrientation implements IOrientation {
    private float orientation;

    MockOrientation(float d) {
        setOrientation(d);
    }

    MockOrientation() {
        setOrientation(0.0f);
    }

    public void setOrientation(float d) {
        this.orientation = d;
    }

    public float getOrientation() {
        return orientation;
    }
}
