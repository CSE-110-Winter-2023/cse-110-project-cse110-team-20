package com.example.socialcompass;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void testConstructor(){
        Point a = new Point();
        assertEquals(0, a.latitude, 0.01);
        assertEquals(0, a.longitude, 0.01);
        assertEquals("", a.refName);
        Point b = new Point(1.1, 2.2, "example");
        assertEquals(1.1, b.latitude, 0.001);
        assertEquals(2.2, b.longitude, 0.001);
        assertEquals("example", b.refName);
    }

    @Test
    public void testSetPoint(){
        Point tst = new Point(1.0, 2.0, "tester");
        tst.setPoint(2.2, -0.4);
        assertEquals(2.2, tst.latitude, 0.001);
        assertEquals(-0.4, tst.longitude, 0.001);
        tst.setPoint("10.4,3.62");
        assertEquals(10.4, tst.latitude, 0.001);
        assertEquals(3.62, tst.longitude, 0.0001);
    }
}
