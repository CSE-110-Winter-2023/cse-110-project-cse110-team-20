package com.example.socialcompass;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.socialcompass.Utils.CompassBg;
import com.example.socialcompass.Utils.CompassMarkers;
//import com.example.socialcompass.Utils.Point;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// Use AndroidJUnit4 test runner to run the tests
@RunWith(AndroidJUnit4.class)
public class CompassActivityTest_2 {
    // Declare a Context variable to store the application context
    private Context context;

    // This method runs before each test to set up the test environment
    @Before
    public void setUp() {
        // Get the application context using ApplicationProvider
        context = ApplicationProvider.getApplicationContext();
    }

    // Test to check if the compass screen can display up to 4 circles
    @Test
    public void testCompassScreenDisplaysUpToFourCircles() {
        // Create a ConstraintLayout object
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        // Initialize the CompassBg with constraintLayout, context, and initial zoom level
        CompassBg compassBg = new CompassBg(constraintLayout, context, 1);
        // Get the list of circle ImageView objects from the CompassBg
        List<ImageView> circleViews = compassBg.getCircleViews();

        // Check if there are 4 circles in the list
        assertEquals(4, circleViews.size());
    }

    // Helper method to count the visible circles in a list of ImageView objects
    private int getVisibleCircles(List<ImageView> circleViews) {
        // Initialize a counter for visible circles
        int visibleCount = 0;
        // Loop through each ImageView object in the list
        for (ImageView circleView : circleViews) {
            // Check if the current circleView is visible
            if (circleView.getVisibility() == View.VISIBLE) {
                // Increment the visible circle counter
                visibleCount++;
            }
        }
        // Return the count of visible circles
        return visibleCount;
    }

    // Test to check if zooming in and out of the compass works as expected
    @Test
    public void testCompassZoomInAndOut() {
        // Create a ConstraintLayout object
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        // Initialize the CompassBg with constraintLayout, context, and initial zoom level
        CompassBg compassBg = new CompassBg(constraintLayout, context, 1);
        // Get the list of circle ImageView objects from the CompassBg
        List<ImageView> circleViews = compassBg.getCircleViews();

        // Update the zoom level to 2 and check if 2 circles are visible
        compassBg.update(2);
        assertEquals(2, getVisibleCircles(circleViews));

        // Update the zoom level to 3 and check if 3 circles are visible
        compassBg.update(3);
        assertEquals(3, getVisibleCircles(circleViews));

        // Update the zoom level to 4 and check if 4 circles are visible
        compassBg.update(4);
        assertEquals(4, getVisibleCircles(circleViews));
    }

    // Test to check if the Point class updates its location correctly
    @Test
    public void testPointUpdatesCorrectlyBetweenCompassCircles() {
        // Create a Point object
        Point point = new Point();
        // Define latitude and longitude values
        float lat = 12.34f;
        float lon = 56.78f;

        // Set the location of the point using the latitude and longitude values
        point.setLocation(lat, lon);

        // Check if the point's latitude and longitude are updated correctly
        assertEquals(lat, point.getLatitude(), 0.001);
        assertEquals(lon, point.getLongitude(), 0.001);
    }
}
