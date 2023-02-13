package com.example.socialcompass;

public class Converter {
    /*
        Compute angle in degrees between two long-lat coordinates
        https://stackoverflow.com/questions/7715057/how-to-make-compass-point-to-my-particular-latitude-longitude-location-based-on
     */
    protected double coordinateToDegree(double startLat, double startLng, double endLat, double endLng) {
        double longitude1 = startLng;
        double longitude2 = endLng;
        double latitude1 = Math.toRadians(startLat);
        double latitude2 = Math.toRadians(endLat);
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    protected double locationToDegreeFromNorth(Location startLocation, Location endLocation) {
        NorthLocation NP = new NorthLocation();
        return (coordinateToDegree(startLocation.getLocation().first, startLocation.getLocation().second, endLocation.getLocation().first, endLocation.getLocation().second)
                - coordinateToDegree(startLocation.getLocation().first, startLocation.getLocation().second, NP.getLocation().first, NP.getLocation().second) + 360) % 360;
    }

}