package com.example.socialcompass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MockOrientationTest {
    private static float delta = 0.001f;
    @Test
    public void testNewOrientation() {
        float orientation1 = 1.0f;
        Orientation mo = new Orientation(orientation1);
        assertEquals(orientation1, mo.getOrientation().getValue(), delta);
    }

    @Test
    public void testDefaultOrientation() {
        Orientation mo = new Orientation();
        assertEquals(0.0f, mo.getOrientation().getValue(), delta);
    }

    @Test
    public void testSetOrientation() {
        float orientation2 = 1.5f;
        Orientation mo = new Orientation();
        mo.setOrientation(orientation2);
        assertEquals(orientation2, mo.getOrientation().getValue(), delta);
    }
    @Test
    public void testNegativeOrientation() {
        float orientation = -1.0f;
        Orientation mo = new Orientation(orientation);
        assertEquals(orientation, mo.getOrientation().getValue(), delta);
    }

    @Test
    public void testLargeOrientation() {
        float orientation = 100.0f;
        Orientation mo = new Orientation(orientation);
        assertEquals(orientation, mo.getOrientation().getValue(), delta);
    }

    @Test
    public void testSetNegativeOrientation() {
        float orientation = -1.0f;
        Orientation mo = new Orientation();
        mo.setOrientation(orientation);
        assertEquals(orientation, mo.getOrientation().getValue(), delta);
    }

    @Test
    public void testSetLargeOrientation() {
        float orientation = 100.0f;
        Orientation mo = new Orientation();
        mo.setOrientation(orientation);
        assertEquals(orientation, mo.getOrientation().getValue(), delta);
    }

    @Test
    public void testSetMultipleOrientation() {
        float orientation1 = 1.0f;
        float orientation2 = 2.0f;
        Orientation mo = new Orientation(orientation1);
        mo.setOrientation(orientation2);
        assertEquals(orientation2, mo.getOrientation().getValue(), delta);
    }

}
