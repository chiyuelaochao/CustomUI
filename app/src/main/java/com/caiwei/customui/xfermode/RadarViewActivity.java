package com.caiwei.customui.xfermode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.R;

public class RadarViewActivity extends AppCompatActivity {
    private RadarView mRadarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_view);
        mRadarView = (RadarView) findViewById(R.id.radar_view);
    }

    public void start(View view) {
        mRadarView.startScan();
    }

    public void stop(View view) {
        mRadarView.stopScan();
    }
}
