package com.caiwei.customui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.caiwei.customui.maskFilter.MaskFilterActitivy;

/**
 * Created by Cai Wei on 5/6/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.flow_layout_button).setOnClickListener(this);
        findViewById(R.id.xfer_mode_button).setOnClickListener(this);

        findViewById(R.id.mask_filter_button).setOnClickListener(this);
        findViewById(R.id.canvas_button).setOnClickListener(this);
        findViewById(R.id.reveal_button).setOnClickListener(this);
        findViewById(R.id.search_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flow_layout_button:
                goToPage(FlowLayoutActivity.class);
                break;
            case R.id.xfer_mode_button:
                goToPage(XferModeActivity.class);
                break;
            case R.id.mask_filter_button:
                goToPage(MaskFilterActitivy.class);
                break;
            case R.id.canvas_button:
                goToPage(CanvasActivity.class);
                break;
            case R.id.reveal_button:
                goToPage(RevealActivity.class);
                break;
            case R.id.search_button:
                goToPage(SearchViewActivity.class);
                break;
        }
    }

    private void goToPage(Class page) {
        startActivity(new Intent(MainActivity.this, page));
    }
}
