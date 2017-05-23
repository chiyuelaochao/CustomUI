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