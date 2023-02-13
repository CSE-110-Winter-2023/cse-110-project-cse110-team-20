package com.example.socialcompass;

public class MockOrientation implements IOrientation {
    private float orientation;

    MockOrientation(float f) {
        setOrientation(f);
    }

    MockOrientation() {
        setOrientation(0.0f);
    }

    public void setOrientation(float f) {
        this.orientation = f;
    }

    public float getOrientationVal() {
        return orientation;
    }
}
