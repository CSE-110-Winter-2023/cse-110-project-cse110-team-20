package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class Mapscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);

        // Get points from storage
        Bundle extras = getIntent().getExtras();
        Point point1, point2, point3;
        if (extras != null) {
            String[] point1_str = extras.getStringArray("point1");
            String[] point2_str = extras.getStringArray("point2");
            String[] point3_str = extras.getStringArray("point3");

            point1 = new Point(point1_str);
            point2 = new Point(point2_str);
            point3 = new Point(point3_str);
        } else {
            point1 = new Point();
            point2 = new Point();
            point3 = new Point();
        }

        Log.d("test", point1.toString());
        Log.d("test", point2.toString());
        Log.d("test", point3.toString());

        // Set legends
        TextView redLegendText = (TextView) findViewById(R.id.red_label_legend);
        TextView yellowLegendText = (TextView) findViewById(R.id.yellow_label_legend);
        TextView greenLegendText = (TextView) findViewById(R.id.green_label_legend);

        redLegendText.setText(point1.getLabel());
        yellowLegendText.setText(point2.getLabel());
        greenLegendText.setText(point3.getLabel());

        // Use compass to set points
        ImageView redPoint = (ImageView) findViewById(R.id.red_point);
        ImageView yellowPoint = (ImageView) findViewById(R.id.yellow_point);
        ImageView greenPoint = (ImageView) findViewById(R.id.green_point);
        ImageView northPoint = (ImageView) findViewById(R.id.north_point);

        Point current = new Point(32.87979908558993f, -117.2360734379745f);
        Display display = new Display(new MockOrientation(),  new Compass(current, point2, point3, point1));
        Map<String, Float> degreesForDisplay = display.modifyDegreesToLocations();

        ConstraintLayout.LayoutParams layoutParamsRed = (ConstraintLayout.LayoutParams) redPoint.getLayoutParams();
        layoutParamsRed.circleAngle = degreesForDisplay.get("home");
        redPoint.setLayoutParams(layoutParamsRed);
        Log.d("test", degreesForDisplay.get("home").toString());

        ConstraintLayout.LayoutParams layoutParamsYellow = (ConstraintLayout.LayoutParams) yellowPoint.getLayoutParams();
        layoutParamsYellow.circleAngle = degreesForDisplay.get("parent");
        yellowPoint.setLayoutParams(layoutParamsYellow);
        Log.d("test", degreesForDisplay.get("parent").toString());

        ConstraintLayout.LayoutParams layoutParamsGreen = (ConstraintLayout.LayoutParams) greenPoint.getLayoutParams();
        layoutParamsGreen.circleAngle = degreesForDisplay.get("friend");
        greenPoint.setLayoutParams(layoutParamsGreen);
        Log.d("test", degreesForDisplay.get("friend").toString());

        ConstraintLayout.LayoutParams layoutParamsNorth = (ConstraintLayout.LayoutParams) northPoint.getLayoutParams();
        layoutParamsNorth.circleAngle = degreesForDisplay.get("north");
        northPoint.setLayoutParams(layoutParamsNorth);
    }

}