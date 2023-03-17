package com.example.socialcompass.Utils;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialcompass.R;

public class GPSSignalStatus {

    private final int GRACE_PERIOD_MILLIS = 5000;

    private long lastGPSUpdateTime;

    private TextView timeDisplay;
    private ImageView statusDisplay;

    public GPSSignalStatus(TextView timeDiaplay, ImageView statusDisplay) {
        lastGPSUpdateTime = System.currentTimeMillis();
        this.timeDisplay = timeDiaplay;
        this.statusDisplay = statusDisplay;
    }

    public void updateTime(long newTime) {
        lastGPSUpdateTime = newTime;
    }

    public void update() {
        if (System.currentTimeMillis() - lastGPSUpdateTime > GRACE_PERIOD_MILLIS) {
            // Np GPS signal for over grace period
            timeDisplay.setText(convertTimeDisplay(System.currentTimeMillis() - lastGPSUpdateTime));
            statusDisplay.setImageResource(R.drawable.red_point);
        } else {
            timeDisplay.setText("");
            statusDisplay.setImageResource(R.drawable.green_point);
        }
    }

    private String convertTimeDisplay(long timeMillis) {
        // 1000 = 1s
        long timeSecs = timeMillis / 1000;
        return timeSecs / 60 + "m";
    }
}
