package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SvgMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_map);
        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.taiwan, R.id.svg_map_view);
    }
}
