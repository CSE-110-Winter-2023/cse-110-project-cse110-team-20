package com.example.socialcompass;

import java.util.HashMap;
import java.util.Map;

public class Display {
    private IOrientation orientation;

    public Display(IOrientation orientation) {
        this.orientation = orientation;
    }

    public Map<String, Double> modifyDegreesToLocations(Map<String, Double> compassResult) {
        Map<String, Double> result = new HashMap<String, Double>();
        result.put("parent", compassResult.get("parent") + orientation.getOrientation());
        result.put("friend", compassResult.get("friend") + orientation.getOrientation());
        result.put("home", compassResult.get("home") + orientation.getOrientation());
        result.put("north", orientation.getOrientation());
        return result;
    }
}
