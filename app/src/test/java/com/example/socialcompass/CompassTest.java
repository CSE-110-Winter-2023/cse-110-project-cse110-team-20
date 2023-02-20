package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Map;

public class CompassTest {

    @Test
    public void testBasicDirections() {
        Point current = new Point(32.87979908558993f, -117.2360734379745f);
        Point northPoint = new Point(32.99702100197694f, -117.23677577281721f);
        Point eastPoint = new Point(32.88371156375991f, -117.12470269437084f);
        Point southPoint = new Point(32.812789690011876f, -117.23653697805734f);

        Compass compass = new Compass(current, northPoint, eastPoint, southPoint);
        Map<String, Float> result = compass.getDegreesToPoints();

        assertTrue(result.get("parent") >= 355 || result.get("parent") <= 5);
        assertTrue(result.get("friend") >= 85 || result.get("friend") <= 95);
        assertTrue(result.get("home") >= 175 || result.get("home") <= 185);
    }
}
