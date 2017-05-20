package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.xfermode.RadarView;

public class XferModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfer_mode);

        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.official_demo, R.id.official_demo_view);

        initViews.initButtonAndLayout(this, R.id.round_srcin, R.id.round_srcin_view);
        initViews.initButtonAndLayout(this, R.id.round_destin, R.id.round_destin_view);
        initViews.initButtonAndLayout(this, R.id.round_srcatop, R.id.round_srcatop_view);

        initViews.initButtonAndLayout(this, R.id.invert_srcin, R.id.invert_srcin_view);
        initViews.initButtonAndLayout(this, R.id.invert_destin, R.id.invert_destin_view);
        initViews.initButtonAndLayout(this, R.id.invert_srcatop, R.id.invert_srcatop_view);

        initViews.initButtonAndLayout(this, R.id.eraser_srcout, R.id.eraser_srcout_view);
        initViews.initButtonAndLayout(this, R.id.guagua_srcout, R.id.guagua_srcout_view);
        initViews.initButtonAndLayout(this, R.id.heart_map_destin, R.id.heart_map_destin_view);
        initViews.initButtonAndLayout(this, R.id.radar_button, R.id.radar_layout);

        initViews.initButtonAndLayout(this, R.id.irregular_wave_destin, R.id.irregular_wave_destin_view);
        initViews.initButtonAndLayout(this, R.id.light_book_lighten, R.id.light_book_lighten_view);
        initViews.initButtonAndLayout(this, R.id.twitter_multiply, R.id.twitter_multiply_view);

        mRadarView = (RadarView) findViewById(R.id.radar_view);
    }

    private RadarView mRadarView;

    public void start(View view) {
        mRadarView.startScan();
    }

    public void stop(View view) {
        mRadarView.stopScan();
    }
}