package com.caiwei.customui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Cai Wei on 5/6/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton(R.id.flow_layout_button);
        initButton(R.id.paint_button);
        initButton(R.id.xfer_mode_button);
        initButton(R.id.mask_filter_button);
        initButton(R.id.canvas_button);
        initButton(R.id.reveal_button);
        initButton(R.id.search_button);
        initButton(R.id.path_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flow_layout_button:
                goToPage(LayoutActivity.class);
                break;
            case R.id.paint_button:
                goToPage(PaintActivity.class);
                break;
            case R.id.xfer_mode_button:
                goToPage(XferModeActivity.class);
                break;
            case R.id.mask_filter_button:
                goToPage(MaskFilterActivity.class);
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
            case R.id.path_button:
                goToPage(PathActivity.class);
                break;
        }
    }

    private void initButton(int id) {
        View v = findViewById(id);
        if (v != null) {
            v.setOnClickListener(this);
        }
    }

    private void goToPage(Class page) {
        startActivity(new Intent(MainActivity.this, page));
    }
}
