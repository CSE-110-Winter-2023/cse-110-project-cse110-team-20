package com.example.socialcompass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compass {
    List<Point> friendPoints;
    Point currentPoint;

    public Compass(Point current, List<Point> friendPoints) {
        this.friendPoints = friendPoints;
        currentPoint = current;
    }

    public void update(Point newCurrent, List<Point> newPoints) {
        this.currentPoint = newCurrent;
        this.friendPoints = newPoints;
    }

    Map<String, Float> getDegreesToPoints() {
        List<Float> results = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (Point friendPoint : friendPoints) {
            results.add(Converter.pointToDegreeFromNorth(currentPoint, friendPoint));
            names.add(friendPoint.getLabel());
        }


        Map<String, Float> map = new HashMap<>();
        for (int i = 0; i < results.size(); i++) {
            map.put(names.get(i), results.get(i));
        }

        return map;
    }

    Map<String, Integer> getDistanceToPoints(int circleRadius, int circleZoom) {
        List<Integer> results = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (Point friendPoint : friendPoints) {
            results.add(Converter.pointToMapRadius(currentPoint, friendPoint, circleRadius, circleZoom));
            names.add(friendPoint.getLabel());
        }


        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < results.size(); i++) {
            map.put(names.get(i), results.get(i));
        }

        return map;
    }
}
