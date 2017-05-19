package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Cai Wei on 5/6/2017.
 */

public class FlowLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.flow_layout_button, R.id.flow_layout);
        initViews.initButtonAndLayout(this, R.id.waterflow_layout_button, R.id.waterflow_layout);
    }
}
