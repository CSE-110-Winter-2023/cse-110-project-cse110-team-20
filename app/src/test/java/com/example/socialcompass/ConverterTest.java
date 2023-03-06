package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Map;

public class ConverterTest {
    private static Converter converter = new Converter();
    @Test
    public void testDirNorth() {
        // test if it finds the direction going north
        float[] coords = {0.0f, 0.0f, 1.0f, 0.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToNorth = converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(0.0f, originToNorth,0.001f);
    }

    @Test
    public void testDirEast() {
        // test if it finds the direction going east
        float[] coords = {0.0f, 0.0f, 0.0f, 1.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToEast = converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(90.0f, originToEast, 0.001f);
    }

    @Test
    public void testDirWest() {
        // test if it finds the direction going west
        float[] coords = {0.0f, 0.0f, 0.0f, -1.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToWest = converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(-90.0f, originToWest, 0.001f);
    }

    @Test
    public void testDirSouthWest() {
        // test if it finds the direction going southwest
        float[] coords = {0.0f, 0.0f, -1.0f, -1.0f};
        Point originPoint = new Point(coords[0], coords[1]);
        Point northPoint = new Point(coords[2], coords[3]);
        float originToSouthwest = converter.pointToDegreeFromNorth(originPoint, northPoint);
        assertEquals(-135.0f, originToSouthwest, 0.001f);
    }

    @Test
    public void testDirSmallDistance() {
        // test if it finds the direction from franklin antonio to geisel
        float[] coords = {32.88350f, -117.23492f, 32.88109f, -117.23756f};
        Point fahPoint = new Point(coords[0], coords[1]);
        Point geiselPoint = new Point(coords[2], coords[3]);
        float fahToGeisel = converter.pointToDegreeFromNorth(fahPoint, geiselPoint);

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
        float fahToEiffel = converter.pointToDegreeFromNorth(fahPoint, eiffelPoint);

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
        float vancouverToVladivostok = converter.pointToDegreeFromNorth(
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
        float fahToGeisel = converter.pointToDistanceInMiles(fahPoint, geiselPoint);

        // using google maps
        double expected = 0.2266;
        assertEquals(expected, fahToGeisel, 0.01);
    }

    @Test
    public void testDistLargeDistance() {
        // test if it finds the distance from franklin antonio to eiffel tower
        float[] coords = {32.88350f, -117.23492f, 48.85824f, 2.29450f};
        Point fahPoint = new Point(coords[0], coords[1]);
        Point eiffelPoint = new Point(coords[2], coords[3]);
        float fahToEiffel = converter.pointToDistanceInMiles(fahPoint, eiffelPoint);

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
        float hollywoodToSydney = converter.pointToDistanceInMiles(hollywoodPoint, sydneyOperaPoint);

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
        float kamchatkaToKodiak = converter.pointToDistanceInMiles(kamchatkaPoint, kodiakPoint);

        // using google maps
        double expected = 1799.70;
        // give a larger margin of error over large distances
        assertEquals(expected, kamchatkaToKodiak, 25);
    }

}
