package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void testConstructor(){
        Point a = new Point();
        assertEquals(0, a.getLatitude(), 0.01);
        assertEquals(0, a.getLongitude(), 0.01);
        assertEquals("", a.getLabel());
        Point b = new Point(1.1, 2.2, "example");
        assertEquals(1.1, b.getLatitude(), 0.001);
        assertEquals(2.2, b.getLongitude(), 0.001);
        assertEquals("example", b.getLabel());
    }

    @Test
    public void testSetPoint(){
        Point tst = new Point(1.0, 2.0, "tester");
        tst.setLocation(2.2, -0.4);
        assertEquals(2.2, tst.getLatitude(), 0.001);
        assertEquals(-0.4, tst.getLongitude(), 0.001);
        tst.setLocation("10.4,3.62");
        assertEquals(10.4, tst.getLatitude(), 0.001);
        assertEquals(3.62, tst.getLongitude(), 0.0001);
    }
}
