package com.example.socialcompass;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.core.app.ActivityScenario;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CircleCountMatcher extends TypeSafeMatcher<View> {

    private final int expectedNumberOfCircles;
    private ActivityScenario<?> scenario;

    private CircleCountMatcher(int expectedNumberOfCircles, ActivityScenario<?> scenario) {
        this.expectedNumberOfCircles = expectedNumberOfCircles;
        this.scenario = scenario;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (!(view instanceof ConstraintLayout)) {
            return false;
        }

        ConstraintLayout compass = (ConstraintLayout) view;
        int actualNumberOfCircles = 0;
        // Get the context from the view
        Context context = view.getContext();
        actualNumberOfCircles = getNumberOfCircles(compass, context);
        return actualNumberOfCircles == expectedNumberOfCircles;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with circle count: ");
        description.appendValue(expectedNumberOfCircles);
    }

    public static Matcher<View> withCircleCount(int expectedNumberOfCircles, ActivityScenario<?> scenario) {
        return new CircleCountMatcher(expectedNumberOfCircles, scenario);
    }

    private static int getNumberOfCircles(ConstraintLayout compass, Context context) {
        int circleCount = 0;

        for (int i = 0; i < compass.getChildCount(); i++) {
            View child = compass.getChildAt(i);
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                if (isCircle(imageView, context)) {
                    circleCount++;
                }
            }
        }

        return circleCount;
    }

    // Make isCircle a static method
    private static boolean isCircle(ImageView imageView, Context context) {
        // Check if the ImageView uses the R.drawable.outline_circle drawable
        return imageView.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.outline_circle).getConstantState();
    }
    public static void logNumberOfCircles(ActivityScenario<CompassActivity> scenario, int viewId) {
        scenario.onActivity(activity -> {
            ConstraintLayout compass = activity.findViewById(viewId);
            int numberOfCircles = getNumberOfCircles(compass, activity);
            Log.d("NumberOfCircles", "Number of circles: " + numberOfCircles);
        });
    }

}
