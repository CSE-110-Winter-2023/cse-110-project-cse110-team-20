package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Map;

public class CompassTest {
    double THRESHOLD = 5;

    @Test
    public void testBasicDirections() {
        Location current = new Location(32.87979908558993, -117.2360734379745);
        Location northLocation = new Location(32.99702100197694, -117.23677577281721);
        Location eastLocation = new Location(32.88371156375991, -117.12470269437084);
        Location southLocation = new Location(32.812789690011876, -117.23653697805734);

        Compass compass = new Compass(current, northLocation, eastLocation, southLocation);
        Map<String, Double> result = compass.getDegreesToLocations();

        assertTrue(result.get("friend") - result.get("parent") < 90 + THRESHOLD && result.get("friend") - result.get("parent") > 90 - THRESHOLD);
        assertTrue(result.get("home") - result.get("friend") < 90 + THRESHOLD && result.get("home") - result.get("friend") > 90 - THRESHOLD);

        assertTrue(true);
    }
}
