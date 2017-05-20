package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.paint.CircleProgressBar;

public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.paint_basic, R.id.paint_basic_view);
        initViews.initButtonAndLayout(this, R.id.dash, R.id.dash_view);
        initViews.initButtonAndLayout(this, R.id.dash_path, R.id.pash_path_view);
        initViews.initButtonAndLayout(this, R.id.minion, R.id.minion_view);
        initViews.initButtonAndLayout(this, R.id.circle_progress, R.id.circle_progress_view);
        initViews.initButtonAndLayout(this, R.id.linear_gradient, R.id.linear_gradient_text_view);
        initViews.initButtonAndLayout(this, R.id.gradient, R.id.gradient_view);
        initViews.initButtonAndLayout(this, R.id.zoom, R.id.zoom_image_view);

        mProgressbar = (CircleProgressBar) findViewById(R.id.circle_progress_view);

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

    private CircleProgressBar mProgressbar;
    private int progress = 0;
}
