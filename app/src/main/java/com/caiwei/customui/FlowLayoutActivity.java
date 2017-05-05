package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.caiwei.customui.widget.FlowLayout;

/**
 * Created by Cai Wei on 5/6/2017.
 */

public class FlowLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        flowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(FlowLayoutActivity.this, index + " " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
