package com.caiwei.customui;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.caiwei.customui.canvas.GallaryHorizontalScrollView;
import com.caiwei.customui.canvas.RevealDrawable;

public class RevealActivity extends AppCompatActivity {
    public final String TAG = getClass().getSimpleName();
    private ImageView iv;

    private int[] mImgIds = new int[]{ //7个
            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline,
            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline
    };

    private int[] mImgIds_active = new int[]{
            R.drawable.avft_active,
            R.drawable.box_stack_active,
            R.drawable.bubble_frame_active,
            R.drawable.bubbles_active,
            R.drawable.bullseye_active,
            R.drawable.circle_filled_active,
            R.drawable.circle_outline_active,
            R.drawable.avft_active,
            R.drawable.box_stack_active,
            R.drawable.bubble_frame_active,
            R.drawable.bubbles_active,
            R.drawable.bullseye_active,
            R.drawable.circle_filled_active,
            R.drawable.circle_outline_active
    };

    public Drawable[] revealDrawables;
    protected int level = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        initData();
        initView();

        iv = (ImageView) findViewById(R.id.iv);
        iv.setImageDrawable(revealDrawables[0]);
        iv.setImageLevel(level);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level -= 1000;
                iv.getDrawable().setLevel(level);
            }
        });

    }


    private void initData() {
        revealDrawables = new Drawable[mImgIds.length];
    }

    private void initView() {

        for (int i = 0; i < mImgIds.length; i++) {

            RevealDrawable rd = new RevealDrawable(
                    getResources().getDrawable(mImgIds[i]),
                    getResources().getDrawable(mImgIds_active[i]),
                    RevealDrawable.HORIZONTAL
            );

            revealDrawables[i] = rd;
        }

        GallaryHorizontalScrollView hzv = (GallaryHorizontalScrollView) findViewById(R.id.hsv);
        if (hzv != null) {
            hzv.addImageViews(revealDrawables);
        } else {
            Log.e(TAG, "hzv is null!");
        }
    }

}