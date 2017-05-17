package com.caiwei.customui.maskFilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.caiwei.customui.R;

public class MaskFilterActitivy extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private EmbossMaskFilterView embossMaskFilterView;
    private CheckBox x, y, z;
    private SeekBar bar1, bar2, bar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_filter);
        embossMaskFilterView = (EmbossMaskFilterView) findViewById(R.id.emboss_mask);
        x = initCheckBox(R.id.box1);
        y = initCheckBox(R.id.box2);
        z = initCheckBox(R.id.box3);
        bar1 = initSeekBar(R.id.seekBar1);
        bar2 = initSeekBar(R.id.seekBar2);
        bar3 = initSeekBar(R.id.seekBar3);
    }

    private SeekBar initSeekBar(int id) {
        SeekBar bar = (SeekBar) findViewById(id);
        bar.setOnSeekBarChangeListener(this);
        return bar;
    }

    private void invalidateView() {
        float x = this.x.isChecked() ? 1 : 0;
        float y = this.y.isChecked() ? 1 : 0;
        float z = this.z.isChecked() ? 1 : 0;
        float light = bar1.getProgress() / 100;
        float specular = bar2.getProgress();
        float blur = bar3.getProgress();
        embossMaskFilterView.setparam(x, y, z, light, specular, blur);
    }

    private CheckBox initCheckBox(int id) {
        CheckBox box = (CheckBox) findViewById(id);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.box1:
                        Log.e("main", "box1 = " + isChecked);
                        invalidateView();
                        break;
                    case R.id.box2:
                        Log.e("main", "box2 = " + isChecked);
                        invalidateView();
                        break;
                    case R.id.box3:
                        Log.e("main", "box3 = " + isChecked);
                        invalidateView();
                        break;
                }
            }
        });

        return box;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar1:
                Log.e("main", "seekBar1 = " + progress / 100);
                invalidateView();
                break;
            case R.id.seekBar2:
                Log.e("main", "seekBar2 = " + progress);
                invalidateView();
                break;
            case R.id.seekBar3:
                Log.e("main", "seekBar3 = " + progress);
                invalidateView();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
