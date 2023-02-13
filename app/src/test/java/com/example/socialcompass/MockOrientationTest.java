package com.example.socialcompass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MockOrientationTest {
    private static double delta = 0.001f;
    @Test
    public void testNewOrientation() {
        double orientation1 = 1.0;
        MockOrientation mo = new MockOrientation(orientation1);
        assertEquals(orientation1, mo.getOrientation(), delta);
    }

    @Test
    public void testDefaultOrientation() {
        MockOrientation mo = new MockOrientation();
        assertEquals(0.0, mo.getOrientation(), delta);
    }

    @Test
    public void testSetOrientation() {
        double orientation2 = 1.5;
        MockOrientation mo = new MockOrientation();
        mo.setOrientation(orientation2);
        assertEquals(orientation2, mo.getOrientation(), delta);
    }
}
