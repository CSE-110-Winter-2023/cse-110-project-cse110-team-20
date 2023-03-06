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

    public void update(Point newCurrent) {
        this.currentPoint = newCurrent;
    }

    Map<String, Float> getDegreesToPoints() {
        float resultParent = Converter.pointToDegreeFromNorth(currentPoint, parentPoint);
        float resultFriend = Converter.pointToDegreeFromNorth(currentPoint, friendPoint);
        float resultHome = Converter.pointToDegreeFromNorth(currentPoint, homePoint);

        Map<String, Float> map = new HashMap<String, Float>();
        map.put("parent",resultParent);
        map.put("friend",resultFriend);
        map.put("home",resultHome);

        return map;
    }
}
