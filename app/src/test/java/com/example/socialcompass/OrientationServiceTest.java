package com.example.socialcompass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
public class OrientationServiceTest {
    private static float delta = 0.001f;

    //test getorientation method
    @Test
    public void testGetOrientation(){


        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService testos = OrientationService.singleton(activity);
            MutableLiveData mockdata = new MutableLiveData<Float>();
            testos.setMockOrientationSource(mockdata);
            mockdata.setValue(1.00f);
            LiveData observed = testos.getOrientation();
            //unsure how to do this assertion
            assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
        });
    }

    //test changing accelerometer
    @Test
    public void testAccelchange(){


        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService testos = OrientationService.singleton(activity);
            MutableLiveData mockdata = new MutableLiveData<Float>();
            testos.setMockOrientationSource(mockdata);
            mockdata.setValue(1.00f);
            LiveData observed = testos.getOrientation();
            //unsure how to do this assertion
            assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
        });
    }

    //test changing magnetic field
    @Test
    public void testMagchange(){


        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService testos = OrientationService.singleton(activity);
            MutableLiveData mockdata = new MutableLiveData<Float>();
            testos.setMockOrientationSource(mockdata);
            mockdata.setValue(1.00f);
            LiveData observed = testos.getOrientation();
            //unsure how to do this assertion
            assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
        });
    }

    //test changing both sensor data
    @Test
    public void testBothchanged(){


        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService testos = OrientationService.singleton(activity);
            MutableLiveData mockdata = new MutableLiveData<Float>();
            testos.setMockOrientationSource(mockdata);
            mockdata.setValue(1.00f);
            LiveData observed = testos.getOrientation();
            //unsure how to do this assertion
            assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
        });
    }
}
