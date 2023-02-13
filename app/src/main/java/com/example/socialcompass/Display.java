package com.example.socialcompass;

import java.util.HashMap;
import java.util.Map;

public class Display {
    private IOrientation orientation;
    private Compass compass;
    public Display(IOrientation orientation) {
        this.orientation = orientation;
    }
    public Map<String, Float> modifyDegreesToLocations() {
        Map<String, Float> compassResult = compass.getDegreesToPoints();
        Map<String, Float> result = new HashMap<String, Float>();
        result.put("parent", compassResult.get("parent") + orientation.getOrientation());
        result.put("friend", compassResult.get("friend") + orientation.getOrientation());
        result.put("home", compassResult.get("home") + orientation.getOrientation());
        result.put("north", orientation.getOrientation());
        return result;
    }
    public Map<String, Float> modifyDegreesToLocations(Map<String, Float> compassResult) {
        Map<String, Float> result = new HashMap<String, Float>();
        result.put("parent", compassResult.get("parent") + orientation.getOrientation());
        result.put("friend", compassResult.get("friend") + orientation.getOrientation());
        result.put("home", compassResult.get("home") + orientation.getOrientation());
        result.put("north", orientation.getOrientation());
        return result;
    }
}
