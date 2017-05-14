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
        findViewById(R.id.round_image).setOnClickListener(this);
        findViewById(R.id.heart_map).setOnClickListener(this);
        findViewById(R.id.official_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.round_image:
                goToPage(RoundImageActivity.class);
                break;
            case R.id.heart_map:
                goToPage(HeartMapActivity.class);
                break;
            case R.id.official_demo:
                goToPage(OfficialDemoActivity.class);
                break;
        }
    }

    private void goToPage(Class page) {
        startActivity(new Intent(XfermodeActivity.this, page));
    }
}