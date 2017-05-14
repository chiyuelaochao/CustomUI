package com.caiwei.customui.xfermode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.R;
import com.caiwei.customui.RoundImageActivity;
import com.caiwei.customui.WaterfallLayoutActivity;

public class XfermodeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfermode);
        findViewById(R.id.round_image).setOnClickListener(this);
        findViewById(R.id.x2).setOnClickListener(this);
        findViewById(R.id.x3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.round_image:
                goToPage(RoundImageActivity.class);
                break;
            case R.id.x2:
                goToPage(WaterfallLayoutActivity.class);
                break;
            case R.id.x3:
                goToPage(WaterfallLayoutActivity.class);
                break;
        }
    }

    private void goToPage(Class page) {
        startActivity(new Intent(XfermodeActivity.this, page));
    }
}