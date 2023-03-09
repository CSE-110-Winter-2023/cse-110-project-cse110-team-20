package com.example.socialcompass;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Display {
    private IOrientation orientation;
    private Compass compass;
    public Display(IOrientation orientation, Compass compass) {
        this.orientation = orientation;
        this.compass = compass;
    }

    public void update(Point newPosition, List<Point> newFriendPositions, IOrientation newOrientation) {
        compass.update(newPosition, newFriendPositions);
        orientation = newOrientation;
    }

    public Map<String, Float> modifyDegreesToLocations() {
        Map<String, Float> compassResult = this.compass.getDegreesToPoints();

        Map<String, Float> result = new HashMap<>();
        result.put("north", orientation.getOrientation().getValue()*(-1));

        for (String key : compassResult.keySet()) {
            float value = compassResult.get(key);
            result.put(key, compassResult.get(key) - orientation.getOrientation().getValue());
        }

        return result;
    }

    public Map<String, Integer> modifyDistanceToLocations(int circleRadius, int circleZoom) {
        return this.compass.getDistanceToPoints(circleRadius, circleZoom);
    }
    /*
    public Map<String, Float> modifyDegreesToLocations(Map<String, Float> compassResult) {
        Map<String, Float> result = new HashMap<String, Float>();
        result.put("parent", compassResult.get("parent") - orientation.getOrientation().getValue());
        result.put("friend", compassResult.get("friend") - orientation.getOrientation().getValue());
        result.put("home", compassResult.get("home") - orientation.getOrientation().getValue());
        result.put("north", orientation.getOrientation().getValue());
        return result;
    }
    */
}
