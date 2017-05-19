package com.caiwei.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.caiwei.customui.canvas.search.Controller;
import com.caiwei.customui.canvas.search.CustomSearchView;

public class SearchViewActivity extends AppCompatActivity {
    private CustomSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        mSearchView = (CustomSearchView) findViewById(R.id.sv);
        mSearchView.setController(new Controller());

    }

    public void start(View v) {
        mSearchView.startAnimation();
    }

    public void reset(View v) {
        mSearchView.resetAnimation();
    }
}
