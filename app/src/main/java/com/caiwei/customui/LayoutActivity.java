package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.caiwei.customui.layout.WaterfallJohnLayout;

import java.util.Random;

/**
 * Created by Cai Wei on 5/6/2017.
 */

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        InitViews initViews = new InitViews();
        initViews.initButtonAndLayout(this, R.id.flow_layout_button, R.id.flow_layout);
        initViews.initButtonAndLayout(this, R.id.waterflow_layout_button, R.id.waterflow_layout);
        initViews.initButtonAndLayout(this, R.id.waterflow_john_layout_button, R.id.waterflow_john_layout);


        final WaterfallJohnLayout waterfallLayout = ((WaterfallJohnLayout) findViewById(R.id.waterfallLayout));
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView(waterfallLayout);
            }
        });
    }

    public void addView(WaterfallJohnLayout waterfallLayout) {
        int IMG_COUNT = 5;
        Random random = new Random();
        Integer num = Math.abs(random.nextInt());
        WaterfallJohnLayout.LayoutParams layoutParams = new WaterfallJohnLayout.LayoutParams(
                WaterfallJohnLayout.LayoutParams.MATCH_PARENT,
                WaterfallJohnLayout.LayoutParams.MATCH_PARENT);
        /*WaterfallJohnLayout.LayoutParams layoutParams = new WaterfallJohnLayout.LayoutParams(
                100,
                100);*/
        ImageView imageView = new ImageView(this);
        if (num % IMG_COUNT == 0) {
            imageView.setImageResource(R.drawable.pic_1);
        } else if (num % IMG_COUNT == 1) {
            imageView.setImageResource(R.drawable.pic_2);
        } else if (num % IMG_COUNT == 2) {
            imageView.setImageResource(R.drawable.pic_3);
        } else if (num % IMG_COUNT == 3) {
            imageView.setImageResource(R.drawable.pic_4);
        } else if (num % IMG_COUNT == 4) {
            imageView.setImageResource(R.drawable.pic_5);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        waterfallLayout.addView(imageView, layoutParams);
        waterfallLayout.setOnItemClickListener(new WaterfallJohnLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(LayoutActivity.this, "item=" + index, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
