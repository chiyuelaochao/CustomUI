package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.curve, R.id.curve_view);
        initViews.initButtonAndLayout(this, R.id.path_fill, R.id.path_fill_view);
        initViews.initButtonAndLayout(this, R.id.path_op, R.id.path_op_view);
    }
}
