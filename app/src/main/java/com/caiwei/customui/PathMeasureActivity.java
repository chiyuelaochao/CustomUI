package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.caiwei.customui.path.CircleLoadingView;
import com.caiwei.customui.path.TrashCanView;
import com.caiwei.customui.path.WaveBoatView;
import com.caiwei.customui.path.WaveRiseView;

public class PathMeasureActivity extends AppCompatActivity {
    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);

        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.wave_boat, R.id.wave_view);
        initViews.initButtonAndLayout(this, R.id.path_measure_basic, R.id.path_measure_basic_view);
        initViews.initButtonAndLayout(this, R.id.arrow_circle, R.id.arrow_circle_view);
        initViews.initButtonAndLayout(this, R.id.face_loading, R.id.face_loading_view);
        initViews.initButtonAndLayout(this, R.id.circle_loading, R.id.circle_loading_layout);
        initViews.initButtonAndLayout(this, R.id.trash_can, R.id.trash_can_layout);
        initViews.initButtonAndLayout(this, R.id.wave_rise, R.id.wave_rising_layout);
        initViews.initButtonAndLayout(this, R.id.circle_search, R.id.circle_search_view);
        initViews.initButtonAndLayout(this, R.id.circle_airplane, R.id.circle_airplane_view);

        initWaveBoatView();
        initCircleLoadingView();
        initTrashCanView();
        initWaveRiseVIew();
    }

    private void initWaveRiseVIew() {
        final WaveRiseView waveRiseView = (WaveRiseView) findViewById(R.id.wave_rising_view);
        if (waveRiseView == null) {
            return;
        }
        Button mBtnReset = (Button) findViewById(R.id.wave_rising_reset);
        Button mBtnStart = (Button) findViewById(R.id.wave_rising_start);
        if (mBtnStart != null) {
            mBtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    waveRiseView.startAnim();
                }
            });
        }
        if (mBtnReset != null) {
            mBtnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    waveRiseView.reset();
                }
            });
        }
    }

    private void initTrashCanView() {
        Button button = (Button) findViewById(R.id.trash_can_button);
        final TrashCanView trashCanView = (TrashCanView) findViewById(R.id.trash_can_view);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trashCanView.startAnimation();
                }
            });
        }
    }

    private void initWaveBoatView() {
        WaveBoatView waveBoatView = (WaveBoatView) findViewById(R.id.wave_view);
        if (waveBoatView != null) {
            waveBoatView.startAnimation();
        }
    }

    private void initCircleLoadingView() {
        final CircleLoadingView view = (CircleLoadingView) findViewById(R.id.circle_loading_view);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.start();
            }
        });
        findViewById(R.id.end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.end();
            }
        });
    }

}
