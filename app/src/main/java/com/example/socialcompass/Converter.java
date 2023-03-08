package com.example.socialcompass;

import androidx.annotation.NonNull;

public class Converter {

    private Converter() {}

    /**
     * Compute angle of the line connecting two lat/long points
     * @param   startLat    latitude of starting point, between -90 and 90
     * @param   startLng    longitide of starting point, between -180 and 180
     * @param   endLat      latitude of ending point, between -90 and 90
     * @param   endLng      longitide of ending point, between -180 and 180
     * @return              angle of ray from start to end, between -180 and 180, where 0
     *                      represents north, 90 represents east, -90 represents west.
     */
    public static float coordinateToDegree(float startLat, float startLng, float endLat, float endLng) {
        /*float longitude1 = startLng;
        float longitude2 = endLng;
        float latitude1 = (float) Math.toRadians(startLat);
        float latitude2 = (float) Math.toRadians(endLat);
        float longDiff = (float) Math.toRadians(longitude2 - longitude1);
        float y = (float) (Math.sin(longDiff) * Math.cos(latitude2));
        float x = (float) (Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff));

        return (float) ((Math.toDegrees(Math.atan2(y, x)) + 360) % 360);*/

        float latDiff = endLat - startLat;
        float lngDiff = endLng - startLng;
        // correct for edge of map
        if (lngDiff > 180) {
            lngDiff = 360 - lngDiff;
        }
        if (lngDiff < -180) {
            lngDiff = -360 - lngDiff;
        }
        // since this is a 2D map, lat/long are used as distances and not angles
        // so no conversion to radians necessary
        return (float) (Math.toDegrees(Math.atan2(lngDiff, latDiff)));
    }

    /*
     * wrapper for coordinateToDegree
     */
    public static float pointToDegreeFromNorth(@NonNull Point startPoint, @NonNull Point endPoint) {
        /*
        float latitude_north = 90;
        float longitude_north = -135;
        return (coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude())
                - coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(), latitude_north, longitude_north) + 360) % 360;
         */
        return coordinateToDegree(startPoint.getLatitude(), startPoint.getLongitude(),
                endPoint.getLatitude(), endPoint.getLongitude());
    }

    /**
     * Compute great-circle distance between two lat/long points
     * @param   startLat    latitude of starting point, between -90 and 90
     * @param   startLng    longitide of starting point, between -180 and 180
     * @param   endLat      latitude of ending point, between -90 and 90
     * @param   endLng      longitide of ending point, between -180 and 180
     * @return              great-circle distance, in miles
     */
    public static float coordinateToDistanceInMiles(float startLat, float startLng, float endLat, float endLng) {
        final float radius = 3953.1676f; // radius of earth in miles
        // use the simple formula
        // https://en.wikipedia.org/wiki/Great-circle_distance
        // can upgrade to haversine formula for improved efficiency, although not necessary
        double startLatR = Math.toRadians(startLat);
        double startLngR = Math.toRadians(startLng);
        double endLatR = Math.toRadians(endLat);
        double endLngR = Math.toRadians(endLng);
        double angle = Math.acos(
                Math.sin(startLatR)*Math.sin(endLatR) +
                Math.cos(startLatR)*Math.cos(endLatR)*Math.cos(Math.abs(endLngR-startLngR))
        );
        //return (float) Math.toDegrees(angle);
        return (float) angle * radius;
    }

    /*
     * wrapper for coordinateToDistanceInMiles
     */
    public static float pointToDistanceInMiles(@NonNull Point startPoint, @NonNull Point endPoint) {
        return coordinateToDistanceInMiles(startPoint.getLatitude(), startPoint.getLongitude(),
                endPoint.getLatitude(), endPoint.getLongitude());
    }

    /**
     * Convert true distance (miles away) to map distance (radius in pixels)
     * @param distanceMiles location distance, in miles
     * @param circleRadius radius of map circle, in pixels
     * @param circleZoom number of zoom rings on map, from 1 to 4
     * @return radius to place location point, in pixels
     */
    public static int distanceToMapRadius(float distanceMiles, int circleRadius, int circleZoom) {
        int bracket = distanceToMapBracket(distanceMiles);
        if (bracket > circleZoom) {
            return circleRadius;
        }
        int ringSize = circleRadius / circleZoom;
        return ringSize*bracket + (int) (ringSize * distanceToBracketProp(distanceMiles, bracket));
    }

    /*
     * helper method, returns 0-3 for brackets 1-4
     */
    public static int distanceToMapBracket(float distanceMiles) {
        final float[] brackets = {0, 1, 10, 500};
        for (int i = 3; i >= 0; i--) {
            if (distanceMiles > brackets[i]) {
                return i;
            }
        }
        return -1; // should never reach this point for positive distance
    }

    /*
     * helper method, returns percentage distance within a bracket
     * possible feature creep: dynamically adjust fifth bracket distance depending on largest dist
     */
    public static float distanceToBracketProp(float distanceMiles, int bracket) {
        final float[] brackets = {0, 1, 10, 500, 12500}; // earth has circum. of 25000
        return (distanceMiles - brackets[bracket]) / (brackets[bracket+1] - brackets[bracket]);
    }

    /*
     * wrapper for distanceToMapRadius
     */
    public static int pointToMapRadius(@NonNull Point startPoint, @NonNull Point endPoint,
                                       int circleRadius, int circleZoom) {
        return distanceToMapRadius(pointToDistanceInMiles(startPoint, endPoint),
                circleRadius, circleZoom);
    }

}