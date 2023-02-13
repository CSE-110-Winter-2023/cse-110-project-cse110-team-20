package com.example.socialcompass;

import java.util.HashMap;
import java.util.Map;

public class Compass {
    Point parentPoint;
    Point friendPoint;
    Point homePoint;
    Point currentPoint;

    public Compass(Point current, Point parent, Point friend, Point home) {
        parentPoint = parent;
        friendPoint = friend;
        homePoint = home;
        currentPoint = current;
    }

    Map<String, Double> getDegreesToPoints() {
        Converter converter = new Converter();
        double resultParent = converter.pointToDegreeFromNorth(currentPoint, parentPoint);
        double resultFriend = converter.pointToDegreeFromNorth(currentPoint, friendPoint);
        double resultHome = converter.pointToDegreeFromNorth(currentPoint, homePoint);

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("parent",resultParent);
        map.put("friend",resultFriend);
        map.put("home",resultHome);

        return map;
    }
}
