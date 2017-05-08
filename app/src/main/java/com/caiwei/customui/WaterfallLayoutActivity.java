package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.caiwei.customui.widget.WaterfallLayout;

public class WaterfallLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);
        WaterfallLayout waterfallLayout = (WaterfallLayout) findViewById(R.id.water_layout);
        waterfallLayout.setOnItemClickListener(new WaterfallLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(WaterfallLayoutActivity.this, index + " " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
