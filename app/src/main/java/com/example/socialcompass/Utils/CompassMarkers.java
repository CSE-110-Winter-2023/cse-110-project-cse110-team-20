package com.example.socialcompass.Utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.socialcompass.Compass;
import com.example.socialcompass.Display;
import com.example.socialcompass.Orientation;
import com.example.socialcompass.Point;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompassMarkers {

    private final int DEFAULT_TEXT_SIZE = 14;
    private final int POINT_SIZE = 30;
    private final String POINT_TEXT = "●";

    private List<Point> friendLocations;
    private Display display;
    private Map<String, Integer> distanceForDisplay;
    private Map<String, Float> degreesForDisplay;
    private Point currentLocation;
    private Orientation currentOrientation;

    private List<TextView> realFriendLocationMarkers;
    private List<TextView> fakeFriendLocationMarkers;
    private List<TextView> fakeFriendLocationMarkers1;

    public CompassMarkers(List<Point> friendLocations, Point currentLocation, Orientation currentOrientation, ConstraintLayout layout, Context context) {
        this.friendLocations = friendLocations;
        this.currentLocation = currentLocation;
        this.currentOrientation = currentOrientation;

        realFriendLocationMarkers = new ArrayList<>();
        fakeFriendLocationMarkers = new ArrayList<>();
        fakeFriendLocationMarkers1 = new ArrayList<>();

        display = new Display(currentOrientation, new Compass(currentLocation, friendLocations));
        initializeLocationMarkerWidgets(realFriendLocationMarkers, layout, context,true);
        initializeLocationMarkerWidgets(fakeFriendLocationMarkers, layout, context, false);
        initializeLocationMarkerWidgets(fakeFriendLocationMarkers1, layout, context, false);
    }

    public void update(int zoomLevel) {
        computePositions(zoomLevel);
        updateMarkersForCollision();
    }

    public void setFriendLocations(List<Point> friendLocations) {
        this.friendLocations = friendLocations;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    protected void updateMarkersForCollision() {
        setupMarkers(fakeFriendLocationMarkers1, fakeFriendLocationMarkers, false);
        for (int i = 0; i < friendLocations.size(); i++) {
            for (int j = i + 1; j < friendLocations.size(); j++) {
                if (detectTextviewCollision(fakeFriendLocationMarkers.get(i), fakeFriendLocationMarkers.get(j))) {
                    if (fakeFriendLocationMarkers.get(i).getWidth() > 100) {
                        if (fakeFriendLocationMarkers.get(i).getLeft() <= fakeFriendLocationMarkers.get(j).getLeft()) {
                            truncateTextview(fakeFriendLocationMarkers1.get(i), fakeFriendLocationMarkers.get(i));
                        } else {
                            truncateTextview(fakeFriendLocationMarkers1.get(j), fakeFriendLocationMarkers.get(j));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < friendLocations.size(); i++) {
            if (fakeFriendLocationMarkers1.get(i).getText().toString().isEmpty()) {
                fakeFriendLocationMarkers1.get(i).setText(fakeFriendLocationMarkers.get(i).getText().toString());
            }
        }

        setupMarkers(realFriendLocationMarkers, fakeFriendLocationMarkers1, true);
        for (int i = 0; i < friendLocations.size(); i++) {
            realFriendLocationMarkers.get(i).setText(fakeFriendLocationMarkers1.get(i).getText().toString());
            for (int j = i + 1; j < friendLocations.size(); j++) {
                if (detectTextviewCollision(fakeFriendLocationMarkers1.get(i), fakeFriendLocationMarkers1.get(j))) {
                    // string already truncated but still collide -> overlap
                    adjustTextviewsForOverlap(realFriendLocationMarkers.get(i), realFriendLocationMarkers.get(j), 20 + 45 * Math.abs(Math.sin(degreesForDisplay.get(friendLocations.get(i).getLabel()) / 360 * 2 * Math.PI)));
                }
            }
        }
    }

    protected void initializeLocationMarkerWidgets(List<TextView> locationMarkers, ConstraintLayout layout, Context context, boolean visible) {
        for (int i = 0; i < friendLocations.size(); i++) {
            TextView textView = new TextView(context);
            textView.setId(View.generateViewId());
            textView.setText(friendLocations.get(i).getLabel());
            textView.setTextSize(DEFAULT_TEXT_SIZE);
            if (!visible) textView.setVisibility(View.INVISIBLE);
            locationMarkers.add(textView);
        }

        // Add the TextView widget to the activity's layout
        for (int i = 0; i < friendLocations.size(); i++) {
            layout.addView(locationMarkers.get(i));

            // Get the ConstraintSet for the layout
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);

            // Set the circle constraint for the TextView
            constraintSet.connect(locationMarkers.get(i).getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(locationMarkers.get(i).getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START);
            constraintSet.connect(locationMarkers.get(i).getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
            constraintSet.constrainCircle(locationMarkers.get(i).getId(), layout.getId(), 300, i * 30);

            // Apply the constraints to the layout
            constraintSet.applyTo(layout);
        }
    }

    private void updateText(TextView tv_real, TextView tv_fake, String label, int size, boolean repeat) {
        tv_real.setTextSize(size);
        tv_real.setText("");
        if (!repeat) {
            tv_fake.setTextSize(size);
            tv_fake.setText(label);
        }
    }

    private void updateLayout(TextView tv, int radius, float angle) {
        ConstraintLayout.LayoutParams layoutParamsReal = (ConstraintLayout.LayoutParams) tv.getLayoutParams();
        layoutParamsReal.circleAngle = angle;
        layoutParamsReal.circleRadius = radius;
        tv.setLayoutParams(layoutParamsReal);
    }

    private void computePositions(int zoomLevel) {
        // Compute updated radius and degrees for each friend marker
        display.update(currentLocation, friendLocations, currentOrientation);
        degreesForDisplay = display.modifyDegreesToLocations();
        distanceForDisplay = display.modifyDistanceToLocations(CompassBg.MAX_RADIUS, zoomLevel);
    }

    private void setupMarkers(List<TextView> realMarkers, List<TextView> fakeMarkers, boolean repeat) {
        // Setup real and fake markers for collision detection
        for (int i = 0; i < friendLocations.size(); i++) {
            if (distanceForDisplay.get(friendLocations.get(i).getLabel()) < CompassBg.MAX_RADIUS) {
                // Not on outer edge
                updateText(realMarkers.get(i), fakeMarkers.get(i), friendLocations.get(i).getLabel(), DEFAULT_TEXT_SIZE, repeat);
            } else {
                updateText(realMarkers.get(i), fakeMarkers.get(i), POINT_TEXT, POINT_SIZE, repeat);
            }

            updateLayout(realMarkers.get(i), distanceForDisplay.get(friendLocations.get(i).getLabel()), degreesForDisplay.get(friendLocations.get(i).getLabel()));
            updateLayout(fakeMarkers.get(i), distanceForDisplay.get(friendLocations.get(i).getLabel()), degreesForDisplay.get(friendLocations.get(i).getLabel()));
        }
    }

    private boolean detectTextviewCollision(TextView tv1, TextView tv2) {
        return tv1.getLeft() < tv2.getRight() &&
                tv1.getRight() > tv2.getLeft() &&
                tv1.getTop() < tv2.getBottom() &&
                tv1.getBottom() > tv2.getTop();
    }

    private void truncateTextview(TextView tv, TextView fakeTv) {
        if (!tv.getText().toString().isEmpty()) {
            return;
        }
        if (fakeTv.getText().toString().length() > 3) {
            tv.setText(fakeTv.getText().toString().substring(0, 3));
        } else {
            tv.setText(fakeTv.getText().toString());
        }
    }

    private void adjustTextviewsForOverlap(TextView tv1, TextView tv2, double adjustMargin) {
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) tv1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) tv2.getLayoutParams();

        // Strategy: keep angle of textviews unchanged
        //  - decrease radius of textview with smaller radius
        //  - increase radius of textview with larger radius
        if (layoutParams1.circleRadius <= layoutParams2.circleRadius) {
            layoutParams1.circleRadius = (int) Math.max(0, layoutParams1.circleRadius - adjustMargin);
            layoutParams2.circleRadius = (int) Math.min(CompassBg.MAX_RADIUS, layoutParams2.circleRadius + adjustMargin);
        } else {
            layoutParams1.circleRadius = (int) Math.min(CompassBg.MAX_RADIUS, layoutParams1.circleRadius + adjustMargin);
            layoutParams2.circleRadius = (int) Math.max(0, layoutParams2.circleRadius - adjustMargin);
        }
    }
}
