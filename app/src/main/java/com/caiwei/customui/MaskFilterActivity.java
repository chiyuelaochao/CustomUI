package com.caiwei.customui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.caiwei.customui.maskFilter.EmbossMaskFilterView;

public class MaskFilterActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    public final String TAG = getClass().getSimpleName();
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
        if (bar != null) {
            bar.setOnSeekBarChangeListener(this);
        }
        return bar;
    }

    @Override
    public String toString() {
        return "invalidateView(" +
                "x=" + (this.x.isChecked() ? 1 : 0) +
                ", y=" + (this.y.isChecked() ? 1 : 0) +
                ", z=" + (this.z.isChecked() ? 1 : 0) +
                ", light=" + (bar1.getProgress() / 100) +
                ", specular=" + bar2.getProgress() +
                ", blur=" + bar3.getProgress() +
                ')';
    }

    private void invalidateView() {
        float x = this.x.isChecked() ? 1 : 0;
        float y = this.y.isChecked() ? 1 : 0;
        float z = this.z.isChecked() ? 1 : 0;
        float light = bar1.getProgress() / 100;
        float specular = bar2.getProgress();
        float blur = bar3.getProgress();
        Log.e(TAG, this.toString());
        embossMaskFilterView.setParam(x, y, z, light, specular, blur);
    }

    private CheckBox initCheckBox(int id) {
        CheckBox box = (CheckBox) findViewById(id);
        if (box != null) {
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    invalidateView();
                }
            });
        }

        return box;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        invalidateView();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
