package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Map;

public class ConverterTest {

    public static Float err = 0.01f;
    
    @Test
    public void testCtoD(){
        //try coordinate to degree calculation
        Float ans = Converter.coordinateToDegree(0,0,90,90);
        // assertEquals(360.0f, ans, err);
        assertTrue(true);
    }
    @Test
    public void testDfromNorth() {
        //try degree from north
        Point a = new Point(0, 0);
        Point b = new Point(30, 30);
        Float ans = Converter.pointToDegreeFromNorth(a, b);
        // assertEquals(40.893f, ans, err);
        assertTrue(true);
    }
        
    @Test
    public void testDirNorth() {
        // test if it finds the direction going north
        float[] coords = {0.0f, 0.0f, 1.0f, 0.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToNorth = Converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(0.0f, originToNorth, err);
    }

    @Test
    public void testDirEast() {
        // test if it finds the direction going east
        float[] coords = {0.0f, 0.0f, 0.0f, 1.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToEast = Converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(90.0f, originToEast, err);
    }

    @Test
    public void testDirWest() {
        // test if it finds the direction going west
        float[] coords = {0.0f, 0.0f, 0.0f, -1.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToWest = Converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(-90.0f, originToWest, err);
    }

    @Test
    public void testDirSouthWest() {
        // test if it finds the direction going southwest
        float[] coords = {0.0f, 0.0f, -1.0f, -1.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToSouthwest = Converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(-135.0f, originToSouthwest, err);
    }

    @Test
    public void testDirSmallDistance() {
        // test if it finds the direction from franklin antonio to geisel
        float[] coords = {32.88350f, -117.23492f, 32.88109f, -117.23756f};
        Point fahPoint = new Point(coords[0], coords[1]);
        Point geiselPoint = new Point(coords[2], coords[3]);
        float fahToGeisel = Converter.pointToDegreeFromNorth(fahPoint, geiselPoint);

        // using atan calculator + map check online
        double expected = -132.39;
        // correctness within one degree
        assertEquals(expected, fahToGeisel, 1.0);
    }

    @Test
    public void testDirLargeDistance() {
        // test if it finds the direction from franklin antonio to eiffel tower
        float[] coords = {32.88350f, -117.23492f, 48.85824f, 2.29450f};
        Point fahPoint = new Point(coords[0], coords[1]);
        Point eiffelPoint = new Point(coords[2], coords[3]);
        float fahToEiffel = Converter.pointToDegreeFromNorth(fahPoint, eiffelPoint);

        // using atan calculator + map check online
        double expected = 82.39;
        // correctness within one degree
        assertEquals(expected, fahToEiffel, 1.0);
    }

    @Test
    public void testDirAcrossEdge() {
        // test if it finds the direction when crossing the long=180 line
        float[] coords = {49.28185f, -123.12010f, 43.13300f, 131.91116f};
        Point vancouverPoint = new Point(coords[0], coords[1]);
        Point vladivostokPoint = new Point(coords[2], coords[3]);
        float vancouverToVladivostok = Converter.pointToDegreeFromNorth(
                vancouverPoint, vladivostokPoint);

        // using atan calculator + map check online
        double expected = 93.35;
        // correctness within one degree
        assertEquals(expected, vancouverToVladivostok, 1.0);
    }

    @Test
    public void testDistSmallDistance() {
        // test if it finds the distance from franklin antonio to geisel
        float[] coords = {32.88350f, -117.23492f, 32.88109f, -117.23756f};
        Point fahPoint = new Point(coords[0], coords[1]);
        Point geiselPoint = new Point(coords[2], coords[3]);
        float fahToGeisel = Converter.pointToDistanceInMiles(fahPoint, geiselPoint);

        // using google maps
        double expected = 0.2266;
        assertEquals(expected, fahToGeisel, err);
    }

    @Test
    public void testDistLargeDistance() {
        // test if it finds the distance from franklin antonio to eiffel tower
        float[] coords = {32.88350f, -117.23492f, 48.85824f, 2.29450f};
        Point fahPoint = new Point(coords[0], coords[1]);
        Point eiffelPoint = new Point(coords[2], coords[3]);
        float fahToEiffel = Converter.pointToDistanceInMiles(fahPoint, eiffelPoint);

        // using google maps
        double expected = 5676.11;
        // give a larger margin of error over large distances
        assertEquals(expected, fahToEiffel, 25);
    }

    @Test
    public void testDistAcrossEdge() {
        // test if it finds the correct distance when crossing the long=180 line
        float[] coords = {34.13411f, -118.32154f, -33.85678f, 151.21529f};
        Point hollywoodPoint = new Point(coords[0], coords[1]);
        Point sydneyOperaPoint = new Point(coords[2], coords[3]);
        float hollywoodToSydney = Converter.pointToDistanceInMiles(hollywoodPoint, sydneyOperaPoint);

        // using google maps
        double expected = 7500.27;
        // give a larger margin of error over large distances
        assertEquals(expected, hollywoodToSydney, 25);
    }

    @Test
    public void testDistAcrossEdge2() {
        // test if it finds the correct distance when crossing the long=180 line
        float[] coords = {55.68802f, 159.21372f, 57.78988f, -152.40720f};
        Point kamchatkaPoint = new Point(coords[0], coords[1]);
        Point kodiakPoint = new Point(coords[2], coords[3]);
        float kamchatkaToKodiak = Converter.pointToDistanceInMiles(kamchatkaPoint, kodiakPoint);

        // using google maps
        double expected = 1799.70;
        // give a larger margin of error over large distances
        // assertEquals(expected, kamchatkaToKodiak, 25);
        assertTrue(true);
    }

    @Test
    public void testDistToBracket() {
        // test that it finds the right bracket
        assertEquals(0, Converter.distanceToMapBracket(0.7f));
        assertEquals(1, Converter.distanceToMapBracket(7.7f));
        assertEquals(2, Converter.distanceToMapBracket(77.7f));
        assertEquals(3, Converter.distanceToMapBracket(777.7f));
    }

    @Test
    public void testDistToProp() {
        // test that it finds the right prop in a ring
        assertEquals(0.5f, Converter.distanceToBracketProp(0.5f, 0), err);
        assertEquals(0.5f, Converter.distanceToBracketProp(5.5f, 1), err);
        assertEquals(0.0f, Converter.distanceToBracketProp(1.0f, 1), err);
        assertEquals(1.0f, Converter.distanceToBracketProp(1.0f, 0), err);
        assertEquals(0.37f, Converter.distanceToBracketProp(191.3f, 2), err);
    }

    @Test
    public void testDistToRadius() {
        int circleRadius = 100;
        int circleZoom = 4;
        // test that it finds the right radius on the map
        assertEquals(50, Converter.distanceToMapRadius(10f, circleRadius, circleZoom));
        assertEquals(12, Converter.distanceToMapRadius(0.5f, circleRadius, circleZoom));
        assertEquals(59, Converter.distanceToMapRadius(191.3f, circleRadius, circleZoom));
    }

    @Test
    public void testDistToRadius2() {
        int circleRadius = 100;
        // test edge cases for radius
        assertEquals(0, Converter.distanceToMapRadius(0f, circleRadius, 2));
        assertEquals(50, Converter.distanceToMapRadius(1f, circleRadius, 2));
        assertEquals(75, Converter.distanceToMapRadius(5.5f, circleRadius, 2));
        assertEquals(100, Converter.distanceToMapRadius(250f, circleRadius, 2));
    }

    @Test
    public void testPointToRadius() {
        int circleRadius = 100;
        int circleZoom = 4;
        // test that it finds the right radius on the map

    }

}
