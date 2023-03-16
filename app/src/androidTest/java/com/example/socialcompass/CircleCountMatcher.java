package com.example.socialcompass;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CircleCountMatcher extends TypeSafeMatcher<View> {

    private final int expectedNumberOfCircles;
    private Context context;

    private CircleCountMatcher(int expectedNumberOfCircles, Context context) {
        this.expectedNumberOfCircles = expectedNumberOfCircles;
        this.context = context;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (!(view instanceof ConstraintLayout)) {
            return false;
        }

        ConstraintLayout compass = (ConstraintLayout) view;
        // Replace this line with the appropriate method or property to get the actual number of circles
        int actualNumberOfCircles = getNumberOfCircles(compass);
        return actualNumberOfCircles == expectedNumberOfCircles;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with circle count: ");
        description.appendValue(expectedNumberOfCircles);
    }

    public static Matcher<View> withCircleCount(int expectedNumberOfCircles, Context context) {
        return new CircleCountMatcher(expectedNumberOfCircles, context);
    }

    private int getNumberOfCircles(ConstraintLayout compass) {
        int circleCount = 0;

        for (int i = 0; i < compass.getChildCount(); i++) {
            View child = compass.getChildAt(i);
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                if (isCircle(imageView)) {
                    circleCount++;
                }
            }
        }

        return circleCount;
    }

    private boolean isCircle(ImageView imageView) {
        // Check if the ImageView uses the R.drawable.outline_circle drawable
        return imageView.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.outline_circle).getConstantState();
    }
}
