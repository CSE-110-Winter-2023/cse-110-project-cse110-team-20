package com.example.socialcompass;

public class Converter {
    /*
        Compute angle in degrees between two long-lat coordinates
        https://stackoverflow.com/questions/7715057/how-to-make-compass-point-to-my-particular-latitude-longitude-location-based-on
     */
    protected float coordinateToDegree(float startLat, float startLng, float endLat, float endLng) {
        float longitude1 = startLng;
        float longitude2 = endLng;
        float latitude1 = (float) Math.toRadians(startLat);
        float latitude2 = (float) Math.toRadians(endLat);
        float longDiff = (float) Math.toRadians(longitude2 - longitude1);
        float y = (float) (Math.sin(longDiff) * Math.cos(latitude2));
        float x = (float) (Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff));

        return (float) ((Math.toDegrees(Math.atan2(y, x)) + 360) % 360);
    }

    protected float pointToDegreeFromNorth(Point startPoint, Point endPoint) {
        float longitude_north = 85;
        float latitude_north = -135;
        return (coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude())
                - coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), longitude_north, latitude_north) + 360) % 360;
    }

}