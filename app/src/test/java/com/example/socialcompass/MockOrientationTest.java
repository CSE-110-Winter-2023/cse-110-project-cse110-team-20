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
        MockOrientation mo = new MockOrientation(orientation1);
        assertEquals(orientation1, mo.getOrientationVal(), delta);
    }

    @Test
    public void testDefaultOrientation() {
        MockOrientation mo = new MockOrientation();
        assertEquals(0.0f, mo.getOrientationVal(), delta);
    }

    @Test
    public void testSetOrientation() {
        float orientation2 = 1.5f;
        MockOrientation mo = new MockOrientation();
        mo.setOrientation(orientation2);
        assertEquals(orientation2, mo.getOrientationVal(), delta);
    }
}
