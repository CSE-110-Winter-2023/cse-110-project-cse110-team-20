package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Map;

public class CompassTest {
    float THRESHOLD = 5;

    @Test
    public void testBasicDirections() {
        Point current = new Point(32.87979908558993f, -117.2360734379745f);
        Point northPoint = new Point(32.99702100197694f, -117.23677577281721f);
        Point eastPoint = new Point(32.88371156375991f, -117.12470269437084f);
        Point southPoint = new Point(32.812789690011876f, -117.23653697805734f);

        Compass compass = new Compass(current, northPoint, eastPoint, southPoint);
        Map<String, Float> result = compass.getDegreesToPoints();

        assertTrue(result.get("friend") - result.get("parent") < 90 + THRESHOLD && result.get("friend") - result.get("parent") > 90 - THRESHOLD);
        assertTrue(result.get("home") - result.get("friend") < 90 + THRESHOLD && result.get("home") - result.get("friend") > 90 - THRESHOLD);

        assertTrue(true);
    }
}
