package com.caiwei.customui.xfermode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.R;

public class XfermodeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfermode);
        findViewById(R.id.round_image_destin).setOnClickListener(this);
        findViewById(R.id.heart_map_destin).setOnClickListener(this);
        findViewById(R.id.official_demo).setOnClickListener(this);
        findViewById(R.id.radar_view).setOnClickListener(this);
        findViewById(R.id.inverted_image_destin).setOnClickListener(this);
        findViewById(R.id.irregular_wave_destin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.official_demo:
                goToPage(OfficialDemoActivity.class);
                break;
            case R.id.round_image_destin:
                goToPage(RoundImageActivity.class);
                break;
            case R.id.heart_map_destin:
                goToPage(HeartMapActivity.class);
                break;
            case R.id.radar_view:
                goToPage(RadarViewActivity.class);
                break;
            case R.id.inverted_image_destin:
                goToPage(InvertedImageActivity.class);
                break;
            case R.id.irregular_wave_destin:
                goToPage(IrregularWaveActivity.class);
                break;
        }
    }

    private void goToPage(Class page) {
        startActivity(new Intent(XfermodeActivity.this, page));
    }
}