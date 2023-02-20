package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Map;

public class ConverterTest {
    Float err = 0.01f;
    @Test
    public void testCtoD(){
        //try coordinate to degree calculation
        Converter c = new Converter();
        Float ans = c.coordinateToDegree(0,0,90,90);
        assertEquals(360.0f, ans, err);
    }
    @Test
    public void testDfromNorth(){
        //try degree from north
        Point a = new Point(0,0);
        Point b = new Point(30,30);
        Converter c = new Converter();
        Float ans = c.pointToDegreeFromNorth(a,b);
        assertEquals(40.893f, ans, err);
    }

}
