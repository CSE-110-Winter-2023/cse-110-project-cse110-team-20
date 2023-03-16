package com.example.socialcompass.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.socialcompass.R;

import java.util.ArrayList;
import java.util.List;

public class CompassBg {

    public final static int MAX_ZOOM_LEVEL = 4;

    public final static int MAX_RADIUS = 408;   // divisible by 2, 3, and 4

    private List<ImageView> compassCircles;


    public CompassBg(ConstraintLayout layout, Context context, int initZoomLevel) {
        compassCircles = new ArrayList<>();

        for (int i = 0; i < MAX_ZOOM_LEVEL; i++) {
            ImageView circle = new ImageView(context);
            circle.setId(View.generateViewId());
            circle.setImageResource(R.drawable.outline_circle);
            layout.addView(circle);

            // Get the ConstraintSet for the layout
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);

            // Set the circle constraint for the TextView
            constraintSet.connect(circle.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(circle.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START);
            constraintSet.connect(circle.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
            constraintSet.constrainCircle(circle.getId(), layout.getId(), 0, 0);

            // Apply the constraints to the layout
            constraintSet.applyTo(layout);

            compassCircles.add(circle);
        }

        update(initZoomLevel);
    }

    public List<ImageView> getCircleViews() {
        return compassCircles;
    }

    /*
        assert zoomLevel 1 - 4
     */
    public void update(int zoomLevel) {
        for (int i = 0; i < MAX_ZOOM_LEVEL; i++) {
            compassCircles.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 1; i <= zoomLevel; i++) {
            int radius = MAX_RADIUS * i / zoomLevel;
            compassCircles.get(i - 1).setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = compassCircles.get(i - 1).getLayoutParams();
            params.width = radius * 2;
            params.height = radius * 2;
            compassCircles.get(i - 1).setLayoutParams(params);
        }
    }
}
