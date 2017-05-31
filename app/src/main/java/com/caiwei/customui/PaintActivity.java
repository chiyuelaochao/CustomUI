package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.caiwei.customui.paint.CircleProgressBar;
import com.caiwei.customui.paint.DashView;

public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.paint_basic, R.id.paint_basic_view);
        initViews.initButtonAndLayout(this, R.id.dash, R.id.dash_layout);
        initViews.initButtonAndLayout(this, R.id.dash_path, R.id.pash_path_view);
        initViews.initButtonAndLayout(this, R.id.minion, R.id.minion_view);
        initViews.initButtonAndLayout(this, R.id.circle_progress, R.id.circle_progress_view);
        initViews.initButtonAndLayout(this, R.id.linear_gradient, R.id.linear_gradient_text_view);
        initViews.initButtonAndLayout(this, R.id.gradient, R.id.gradient_view);
        initViews.initButtonAndLayout(this, R.id.zoom, R.id.zoom_image_view);
        initViews.initButtonAndLayout(this, R.id.video_progress, R.id.video_progress_bar);

        initCircleProgressBarView();
        initDashView();
    }

    private int progress = 0;

    private void initCircleProgressBarView() {
        final CircleProgressBar mProgressbar = (CircleProgressBar) findViewById(R.id.circle_progress_view);
        if (mProgressbar != null) {
            mProgressbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (progress <= 100) {
                                progress += 2;
                                mProgressbar.setProgress(progress);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            });
        }
    }

    private void initDashView() {
        final DashView dashView = (DashView) findViewById(R.id.dash_view);
        Button button = (Button) findViewById(R.id.dash_button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dashView != null) {
                        dashView.startAnim();
                    }
                }
            });
        }
    }
}
