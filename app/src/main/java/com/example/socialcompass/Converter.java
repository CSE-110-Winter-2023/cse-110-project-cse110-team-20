package com.example.socialcompass;

public class Converter {

    /**
     * Compute angle of the line connecting two lat/long points
     * @param   startLat    latitude of starting point, between -90 and 90
     * @param   startLng    longitide of starting point, between -180 and 180
     * @param   endLat      latitude of ending point, between -90 and 90
     * @param   endLng      longitide of ending point, between -180 and 180
     * @return              angle of ray from start to end, between -180 and 180, where 0
     *                      represents north, 90 represents east, -90 represents west.
     */
    protected float coordinateToDegree(float startLat, float startLng, float endLat, float endLng) {
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

    /**
     * Wrapper; computes angle of line from start to end, taking Point as input, and feeds to
     * {@link #coordinateToDegree(float, float, float, float) coordinateToDegree()}.
     * @param startPoint point to start at
     * @param endPoint   point to end at
     * @return           angle of ray from startPoint to endPoint; see coordinateToDegree
     */
    protected float pointToDegreeFromNorth(Point startPoint, Point endPoint) {
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
    protected float coordinateToDistanceInMiles(float startLat, float startLng, float endLat, float endLng) {
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

    protected float pointToDistanceInMiles(Point startPoint, Point endPoint) {
        return coordinateToDistanceInMiles(startPoint.getLatitude(), startPoint.getLongitude(),
                endPoint.getLatitude(), endPoint.getLongitude());
    }

}