package com.example.socialcompass;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import com.example.socialcompass.Activities.CompassActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features")
public class ZoomCompassViewTest {

    @Rule
    public ActivityTestRule<CompassActivity> activityRule = new ActivityTestRule<>(CompassActivity.class);

    //@Given("^the user has opened the compass view with their friends' locations displayed$")
    public void user_has_opened_compass_view() {
        onView(withId(R.id.Compass)).check(matches(isDisplayed()));
    }

    //@When("^the user wants to see their friends' locations in more detail$")
    public void user_wants_to_see_friends_locations_in_more_detail() {
        onView(withId(R.id.incr_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.incr_btn)).check(matches(isClickable()));
    }

    //@Then("^the user can use the zoom in control to increase the level of detail$")
    public void user_can_use_zoom_in_control() {
        // Capture the initial zoom level
        float initialZoomLevel = activityRule.getActivity().getCurrentZoomLevel();

        // Perform a click on the zoom in button
        onView(withId(R.id.incr_btn)).perform(click());

        // Check if the zoom level has increased
        float updatedZoomLevel = activityRule.getActivity().getCurrentZoomLevel();
        assertThat(updatedZoomLevel, greaterThan(initialZoomLevel));
    }

    //@And("^the display will adjust to show more details for each friend's location$")
    public void display_will_adjust_to_show_more_details() {
        // Get the CompassActivity instance from activityRule
        CompassActivity compassActivity = activityRule.getActivity();

        // Check if the compassActivity is showing more details
        assertTrue(compassActivity.isShowingMoreDetails());
    }

    //@And("^the user can see the distance between their current location and their friends' locations$")
    public void user_can_see_distance_between_locations() {
        // Get the CompassActivity instance from activityRule
        CompassActivity compassActivity = activityRule.getActivity();

        // Check if the compassActivity is displaying distances
        assertTrue(compassActivity.isDisplayingDistances());
    }

}

