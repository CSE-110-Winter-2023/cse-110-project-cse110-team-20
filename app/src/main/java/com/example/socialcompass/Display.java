package com.example.socialcompass;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Display {
    private IOrientation orientation;
    private Compass compass;
    public Display(IOrientation orientation, Compass compass) {
        this.orientation = orientation;
        this.compass = compass;
    }

    public void update(Point newPosition, IOrientation newOrientation) {
        compass.update(newPosition);
        orientation = newOrientation;
    }

    public Map<String, Float> modifyDegreesToLocations() {
        Map<String, Float> compassResult = this.compass.getDegreesToPoints();
        Map<String, Float> result = new HashMap<String, Float>();
        result.put("parent", compassResult.get("parent") - orientation.getOrientation());
        result.put("friend", compassResult.get("friend") - orientation.getOrientation());
        result.put("home", compassResult.get("home") - orientation.getOrientation());
        result.put("north", orientation.getOrientation()*(-1));
        return result;
    }
    public Map<String, Float> modifyDegreesToLocations(Map<String, Float> compassResult) {
        Map<String, Float> result = new HashMap<String, Float>();
        result.put("parent", compassResult.get("parent") - orientation.getOrientation());
        result.put("friend", compassResult.get("friend") - orientation.getOrientation());
        result.put("home", compassResult.get("home") - orientation.getOrientation());
        result.put("north", orientation.getOrientation());
        return result;
    }
}
