package com.caiwei.customui.xfermode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.R;

public class SRCActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_src);
        findViewById(R.id.roundsrcin).setOnClickListener(this);
        findViewById(R.id.invertsrcin).setOnClickListener(this);
        findViewById(R.id.eraserview).setOnClickListener(this);
        findViewById(R.id.guaguaview).setOnClickListener(this);
        findViewById(R.id.roundsrcatop).setOnClickListener(this);
        findViewById(R.id.invertsrcatop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideAllViews();
        switch (v.getId()) {
            case R.id.roundsrcin:
                findViewById(R.id.roundsrcin_view).setVisibility(View.VISIBLE);
                break;
            case R.id.invertsrcin:
                findViewById(R.id.invertsrcin_view).setVisibility(View.VISIBLE);
                break;
            case R.id.eraserview:
                findViewById(R.id.eraserview_view).setVisibility(View.VISIBLE);
                break;
            case R.id.guaguaview:
                findViewById(R.id.guaguaview_view).setVisibility(View.VISIBLE);
                break;
            case R.id.roundsrcatop:
                findViewById(R.id.roundsrcatop_view).setVisibility(View.VISIBLE);
                break;
            case R.id.invertsrcatop:
                findViewById(R.id.invertsrcatop_view).setVisibility(View.VISIBLE);
                break;
        }

    }

    private void hideAllViews() {
        findViewById(R.id.roundsrcin_view).setVisibility(View.GONE);
        findViewById(R.id.invertsrcin_view).setVisibility(View.GONE);
        findViewById(R.id.eraserview_view).setVisibility(View.GONE);
        findViewById(R.id.guaguaview_view).setVisibility(View.GONE);
        findViewById(R.id.roundsrcatop_view).setVisibility(View.GONE);
        findViewById(R.id.invertsrcatop_view).setVisibility(View.GONE);
    }
}