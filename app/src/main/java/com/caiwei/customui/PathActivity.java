package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.caiwei.customui.path.DragBubbleView;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.curve, R.id.curve_view);
        initViews.initButtonAndLayout(this, R.id.path_fill, R.id.path_fill_view);
        initViews.initButtonAndLayout(this, R.id.path_op, R.id.path_op_view);
        initViews.initButtonAndLayout(this, R.id.drag_bubble, R.id.drag_bubble_layout);
        initViews.initButtonAndLayout(this, R.id.spider_web_radar, R.id.spider_web_radar_view);
        initViews.initButtonAndLayout(this, R.id.remote_control, R.id.remote_control_menu);
        initViews.initButtonAndLayout(this, R.id.region_contain, R.id.region_contain_view);
        initViews.initButtonAndLayout(this, R.id.circle_panel, R.id.circle_panel_view);

        initDragBubbleView();
    }

    private void initDragBubbleView() {
        final DragBubbleView view = (DragBubbleView) findViewById(R.id.drag_bubble_view);
        Button button = (Button) findViewById(R.id.reset_btn);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view != null) {
                        view.reset();
                    }
                }
            });
        }
    }
}