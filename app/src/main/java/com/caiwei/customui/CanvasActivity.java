package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.save_restore, R.id.sava_restore_view);
        initViews.initButtonAndLayout(this, R.id.save_restore_basic, R.id.sava_restore_basic_view);
        initViews.initButtonAndLayout(this, R.id.save_restore_layer, R.id.sava_restore_layer_view);
        initViews.initButtonAndLayout(this, R.id.draw, R.id.canvas_draw_view);
        initViews.initButtonAndLayout(this, R.id.round, R.id.round_image_view);
        initViews.initButtonAndLayout(this, R.id.clip, R.id.region_clip_image_view);
        initViews.initButtonAndLayout(this, R.id.draw_filter, R.id.draw_filter_view);
    }
}
