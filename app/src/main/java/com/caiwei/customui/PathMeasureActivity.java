package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caiwei.customui.path.WaveView;

public class PathMeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);

        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.wave, R.id.wave_view);
        initViews.initButtonAndLayout(this, R.id.path_measure_basic, R.id.path_measure_basic_view);
        initViews.initButtonAndLayout(this, R.id.arrow_circle, R.id.arrow_circle_view);
        initViews.initButtonAndLayout(this, R.id.face_loading, R.id.face_loading_view);
        initViews.initButtonAndLayout(this, R.id.circle_loading, R.id.circle_loading_view);

        initWaveView();
    }

    private void initWaveView() {
        WaveView waveView = (WaveView) findViewById(R.id.wave_view);
        if (waveView != null) {
            waveView.startAnimation();
        }
    }
}