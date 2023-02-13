package com.example.socialcompass;

import java.util.HashMap;
import java.util.Map;

public class Compass {
    Location parentLocation;
    Location friendLocation;
    Location homeLocation;
    Location currentLocation;

    public Compass(Location current, Location parent, Location friend, Location home) {
        parentLocation = parent;
        friendLocation = friend;
        homeLocation = home;
        currentLocation = current;
    }

    Map<String, Double> getDegreesToLocations() {
        Converter converter = new Converter();
        double resultParent = converter.locationToDegreeFromNorth(currentLocation, parentLocation);
        double resultFriend = converter.locationToDegreeFromNorth(currentLocation, friendLocation);
        double resultHome = converter.locationToDegreeFromNorth(currentLocation, homeLocation);

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("parent",resultParent);
        map.put("friend",resultFriend);
        map.put("home",resultHome);

        return map;
    }
}
