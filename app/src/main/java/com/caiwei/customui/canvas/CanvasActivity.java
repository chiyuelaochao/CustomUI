package com.caiwei.customui.canvas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.caiwei.customui.R;

public class CanvasActivity extends AppCompatActivity {
    private View regionClipImageView, roundImageView, canvasDrawView, saveRestoreView, saveRestoreBasicView,
            saveRestoreLayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        regionClipImageView = findViewById(R.id.region_clip_image_view);
        roundImageView = findViewById(R.id.round_image_view);
        canvasDrawView = findViewById(R.id.canvas_draw_view);
        saveRestoreBasicView = findViewById(R.id.sava_restore_basic_view);
        saveRestoreView = findViewById(R.id.sava_restore_view);
        saveRestoreLayerView = findViewById(R.id.sava_restore_layer_view);

        init(R.id.save_restore);
        init(R.id.save_restore_basic);
        init(R.id.save_restore_layer);
        init(R.id.draw);
        init(R.id.round);
        init(R.id.clip);
    }

    private void init(int id) {
        View view = findViewById(id);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regionClipImageView.setVisibility(View.GONE);
                    roundImageView.setVisibility(View.GONE);
                    canvasDrawView.setVisibility(View.GONE);
                    saveRestoreBasicView.setVisibility(View.GONE);
                    saveRestoreView.setVisibility(View.GONE);
                    saveRestoreLayerView.setVisibility(View.GONE);
                    switch (v.getId()) {
                        case R.id.draw:
                            canvasDrawView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.round:
                            roundImageView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.clip:
                            regionClipImageView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.save_restore_basic:
                            saveRestoreBasicView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.save_restore:
                            saveRestoreView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.save_restore_layer:
                            saveRestoreLayerView.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });
        }
    }
}
