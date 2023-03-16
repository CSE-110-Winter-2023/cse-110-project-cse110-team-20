package com.example.socialcompass;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.socialcompass.CompassActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.socialcompass.CircleCountMatcher.withCircleCount;

@RunWith(AndroidJUnit4.class)
public class CompassActivityTest {

    @Rule
    public ActivityScenarioRule<CompassActivity> activityRule = new ActivityScenarioRule<>(CompassActivity.class);

    @Test
    public void testOrientationEditText() {
        onView(withId(R.id.editOrientation)).perform(typeText("90"));
        Espresso.closeSoftKeyboard();
    }

    @Test
    public void testOkButtonClick() {
        onView(withId(R.id.editOrientation)).perform(typeText("90"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.ok_btn)).perform(click());
    }

    @Test
    public void testDecreaseZoomButtonClick() {
        onView(withId(R.id.decr_btn)).perform(click());
    }

    @Test
    public void testIncreaseZoomButtonClick() {
        onView(withId(R.id.incr_btn)).perform(click());
    }

    @Test
    public void testZoomInAndOutChangesNumberOfCircles() {
        int initialNumberOfCircles = 3; // Replace with the actual initial number of circles
        int expectedNumberOfCirclesAfterZoomIn = 4; // Replace with the expected number of circles after zooming in
        int expectedNumberOfCirclesAfterZoomOut = 3; // Replace with the expected number of circles after zooming out
        activityRule.getScenario().onActivity(activity -> {
            onView(withId(R.id.Compass)).check(matches(withCircleCount(initialNumberOfCircles, activity)));
        });

        // Perform click action on the "+" button
        onView(withId(R.id.incr_btn)).perform(click());

        // Check if the number of circles increases accordingly
        activityRule.getScenario().onActivity(activity -> {
            onView(withId(R.id.Compass)).check(matches(withCircleCount(expectedNumberOfCirclesAfterZoomIn, activity)));
        });

        // Perform click action on the "-" button
        onView(withId(R.id.decr_btn)).perform(click());

        // Check if the number of circles decreases accordingly
        activityRule.getScenario().onActivity(activity -> {
            onView(withId(R.id.Compass)).check(matches(withCircleCount(expectedNumberOfCirclesAfterZoomOut, activity)));
        });
    }
}
