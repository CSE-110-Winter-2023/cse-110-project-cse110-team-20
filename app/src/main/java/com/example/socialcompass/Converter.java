package com.example.socialcompass;

public class Converter {
    /*
        Compute angle in degrees between two long-lat coordinates
        https://stackoverflow.com/questions/7715057/how-to-make-compass-point-to-my-particular-latitude-longitude-location-based-on
     */
//    protected float coordinateToDegree(float startLat, float startLng, float endLat, float endLng) {
//        float longitude1 = startLng;
//        float longitude2 = endLng;
//        float latitude1 = (float) Math.toRadians(startLat);
//        float latitude2 = (float) Math.toRadians(endLat);
//        float longDiff = (float) Math.toRadians(longitude2 - longitude1);
//        float y = (float) (Math.sin(longDiff) * Math.cos(latitude2));
//        float x = (float) (Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff));
//
//        return (float) ((Math.toDegrees(Math.atan2(y, x)) + 360) % 360);
//    }

    private float coordinateToDegree(float latA, float lonA, float latB, float lonB) {
        double deltaX = latB - latA;

        double deltaY1 = lonB - lonA;
        double len1 = deltaY1 * deltaY1;

        double deltaY2 = lonB - 360 - lonA;
        double len2 = deltaY2 * deltaY2;

        double deltaY3 = lonB + 360 - lonA;
        double len3 = deltaY3 * deltaY3;

        double radians;
        if (len1 < Math.min(len2, len3)) {
            radians = Math.atan2(deltaY1, deltaX);
        } else {
            if (len2 < len3) {
                radians = Math.atan2(deltaY2, deltaX);
            } else {
                radians = Math.atan2(deltaY3, deltaX);
            }
        }
        double degrees = Math.toDegrees(radians);
        if (degrees < 0) {
            degrees += 360;
        }
        return (float) degrees;
    }

//    protected float pointToDegreeFromNorth(Point startPoint, Point endPoint) {
//        float latitude_north = 90;
//        float longitude_north = -135;
//        return (coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude())
//                - coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), latitude_north, longitude_north) + 360) % 360;
//    }

    protected float pointToDegreeFromNorth(Point startPoint, Point endPoint) {
        return coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude());
    }

}