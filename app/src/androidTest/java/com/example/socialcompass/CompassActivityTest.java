package com.example.socialcompass;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CompassActivityTest {

    //@Rule
    //public ActivityScenarioRule<CompassActivity> activityRule = new ActivityScenarioRule<>(CompassActivity.class);

    // Your test methods go here
//    @Test
//    public void testTask2_1_displayDifferentNumberOfCircles() {
//        // Check if 2 circles are visible and others are not
//        Espresso.onView(withId(R.id.circle1)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle2)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle3)).check(matches(not(isDisplayed())));
//        Espresso.onView(withId(R.id.circle4)).check(matches(not(isDisplayed())));
//    }
//
//    @Test
//    public void testTask2_2_zoomInAndOut() {
//        // Click zoom in button
//        Espresso.onView(withId(R.id.incr_btn)).perform(click());
//
//        // Check if 3 circles are visible and the last one is not
//        Espresso.onView(withId(R.id.circle1)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle2)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle3)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle4)).check(matches(not(isDisplayed())));
//
//        // Click zoom in button again
//        Espresso.onView(withId(R.id.incr_btn)).perform(click());
//
//        // Check if all 4 circles are visible
//        Espresso.onView(withId(R.id.circle1)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle2)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle3)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle4)).check(matches(isDisplayed()));
//
//        // Click zoom out button
//        Espresso.onView(withId(R.id.zoomOutButton)).perform(click());
//
//        // Check if 3 circles are visible and the last one is not
//        Espresso.onView(withId(R.id.circle1)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle2)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle3)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.circle4)).check(matches(not(isDisplayed())));
//    }
    @Rule
    public ActivityScenarioRule<CompassActivity> activityRule = new ActivityScenarioRule<>(CompassActivity.class);

    @Test
    public void testOrientationEditText() {
        Espresso.onView(withId(R.id.editOrientation)).perform(typeText("90"));
        Espresso.closeSoftKeyboard();
    }

    @Test
    public void testOkButtonClick() {
        Espresso.onView(withId(R.id.editOrientation)).perform(typeText("90"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.ok_btn)).perform(click());
    }


    @Test
    public void testDecreaseZoomButtonClick() {
        Espresso.onView(withId(R.id.decr_btn)).perform(click());
    }

    @Test
    public void testIncreaseZoomButtonClick() {
        Espresso.onView(withId(R.id.incr_btn)).perform(click());
    }

    //@Test
//    public void testTask2_3_pointClassUpdate() {
//        // Here you can test the Point class's functionality to change between compass circles correctly.
//        // You can create instances of the Point class, update their properties, and assert that the changes
//        // are correctly reflected.
//    }

}
