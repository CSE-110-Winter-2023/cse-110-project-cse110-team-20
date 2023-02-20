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
    private static float delta = 0.001f; // tolerance for float equality

    // get the service running
    @Test
    public void testNewService() {
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService orient = OrientationService.singleton(activity);
            assertTrue(true);
        });
    }

    // test creating a mock data source
    @Test
    public void testMockLiveData() {
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        MutableLiveData<Float> data = new MutableLiveData<Float>();
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            data.setValue(1.0f);
            assertEquals(1.0f, data.getValue(), delta);
        });
    }

    // test passing the mock data to OrientationService
    @Test
    public void testMockOrientationSource() {
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
        MutableLiveData<Float> data = new MutableLiveData<Float>();
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService orient = OrientationService.singleton(activity);
            orient.setMockOrientationSource(data);
            data.setValue(1.0f);
            assertEquals(1.0f, orient.getOrientation().getValue(), delta);
        });
    }

    // test the actual calculations behind the orientation somehow?

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
            //assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
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
            //assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
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
            //assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
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
            //assertEquals((Float)observed.getValue(), MainActivity.getDevice_orientation(), delta);
        });
    }
}
