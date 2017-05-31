package com.caiwei.customui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.caiwei.customui.maskFilter.MaskFilterView;

//http://blog.csdn.net/iispring/article/details/47690011
public class NewMaskFilterActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private MaskFilterView maskFilterView;
    private RadioGroup rgMaskFilter;
    private RadioGroup rgBlurStyle;
    private RelativeLayout layoutDirectionX;
    private SeekBar seekBarX;
    private TextView tvDirectionXNumber;
    private RelativeLayout layoutDirectionY;
    private SeekBar seekBarY;
    private TextView tvDirectionYNumber;
    private RelativeLayout layoutDirectionZ;
    private SeekBar seekBarZ;
    private TextView tvDirectionZNumber;
    private RelativeLayout layoutAmbient;
    private SeekBar seekBarAmbient;
    private TextView tvAmbientNumber;
    private RelativeLayout layoutSpecular;
    private SeekBar seekBarSpecular;
    private TextView tvSpecularNumber;
    private RelativeLayout layoutBlurRadius;
    private SeekBar seekBarBlurRadius;
    private TextView tvBlurNumber;
    private int defaultBlurRadius = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mask_filter);

        //根据当前的屏幕密度设置模糊半径
        defaultBlurRadius = (int) (defaultBlurRadius * getResources().getDisplayMetrics().density);

        //根据ID获取View
        maskFilterView = (MaskFilterView) findViewById(R.id.mask_filter_view);
        rgMaskFilter = (RadioGroup) findViewById(R.id.rgMaskFilter);
        rgBlurStyle = (RadioGroup) findViewById(R.id.rgBlurStyle);
        layoutDirectionX = (RelativeLayout) findViewById(R.id.layoutDirectionX);
        seekBarX = (SeekBar) findViewById(R.id.seekBarX);
        tvDirectionXNumber = (TextView) findViewById(R.id.tvDirectionXNumber);
        layoutDirectionY = (RelativeLayout) findViewById(R.id.layoutDirectionY);
        seekBarY = (SeekBar) findViewById(R.id.seekBarY);
        tvDirectionYNumber = (TextView) findViewById(R.id.tvDirectionYNumber);
        layoutDirectionZ = (RelativeLayout) findViewById(R.id.layoutDirectionZ);
        seekBarZ = (SeekBar) findViewById(R.id.seekBarZ);
        tvDirectionZNumber = (TextView) findViewById(R.id.tvDirectionZNumber);
        layoutAmbient = (RelativeLayout) findViewById(R.id.layoutAmbient);
        seekBarAmbient = (SeekBar) findViewById(R.id.seekBarAmbient);
        tvAmbientNumber = (TextView) findViewById(R.id.tvAmbientNumber);
        layoutSpecular = (RelativeLayout) findViewById(R.id.layoutSpecular);
        seekBarSpecular = (SeekBar) findViewById(R.id.seekBarSpecular);
        tvSpecularNumber = (TextView) findViewById(R.id.tvSpecularNumber);
        layoutBlurRadius = (RelativeLayout) findViewById(R.id.layoutBlurRadius);
        seekBarBlurRadius = (SeekBar) findViewById(R.id.seekBarBlurRadius);
        tvBlurNumber = (TextView) findViewById(R.id.tvBlurNumber);

        //设置myView
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        maskFilterView.setBitmap(bitmap);

        //为了确保画笔的setMaskFilter能供起效，我们需要对MyView禁用掉GPU硬件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //View从API Level 11才加入setLayerType方法
            //设置myView以软件渲染模式绘图
            maskFilterView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        //设置maskFilterRadioGroup
        rgMaskFilter.setOnCheckedChangeListener(this);

        //设置rgBlurStyle
        rgBlurStyle.setOnCheckedChangeListener(this);

        //设置seekBarX
        seekBarX.setMax(20);
        seekBarX.setProgress(10 - 3);
        seekBarX.setOnSeekBarChangeListener(this);

        //设置seekBarY
        seekBarY.setMax(20);
        seekBarY.setProgress(10 - 1);
        seekBarY.setOnSeekBarChangeListener(this);

        //设置seekBarZ
        seekBarZ.setMax(20);
        seekBarZ.setProgress(10 + 1);
        seekBarZ.setOnSeekBarChangeListener(this);

        //设置seekBarAmbient
        seekBarAmbient.setMax(10);
        seekBarAmbient.setProgress(5);
        seekBarAmbient.setOnSeekBarChangeListener(this);

        //设置seekBarSpecular
        seekBarSpecular.setMax(10);
        seekBarSpecular.setProgress(10);
        seekBarSpecular.setOnSeekBarChangeListener(this);

        //设置seekBarBlurRadius
        seekBarBlurRadius.setMax(defaultBlurRadius * 2);
        seekBarBlurRadius.setProgress(defaultBlurRadius);
        seekBarBlurRadius.setOnSeekBarChangeListener(this);

        update();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        update();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        update();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    //update方法会同时更新UI界面，并创建新的MaskFilter赋值给myView
    private void update() {
        MaskFilter maskFilter = null;

        int checkedId = rgMaskFilter.getCheckedRadioButtonId();
        if (checkedId == R.id.rbNone) {
            /*-------------------------不使用MaskFilter--------------------------*/
            //更新UI界面
            rgBlurStyle.setVisibility(View.GONE);
            layoutDirectionX.setVisibility(View.GONE);
            layoutDirectionY.setVisibility(View.GONE);
            layoutDirectionZ.setVisibility(View.GONE);
            layoutAmbient.setVisibility(View.GONE);
            layoutSpecular.setVisibility(View.GONE);
            layoutBlurRadius.setVisibility(View.GONE);
        } else if (checkedId == R.id.rbBlur) {
            /*-------------------------使用BlurMaskFilter--------------------------*/
            //更新UI界面
            rgBlurStyle.setVisibility(View.VISIBLE);
            layoutDirectionX.setVisibility(View.GONE);
            layoutDirectionY.setVisibility(View.GONE);
            layoutDirectionZ.setVisibility(View.GONE);
            layoutAmbient.setVisibility(View.GONE);
            layoutSpecular.setVisibility(View.GONE);
            layoutBlurRadius.setVisibility(View.VISIBLE);
            int blurRadius = getBlurRadius();
            tvBlurNumber.setText(Integer.toString(blurRadius));

            //创建BlurMaskFilter
            BlurMaskFilter.Blur blurStyle;
            switch (rgBlurStyle.getCheckedRadioButtonId()) {
                case R.id.rbNormal:
                    blurStyle = BlurMaskFilter.Blur.NORMAL;
                    break;
                case R.id.rbInner:
                    blurStyle = BlurMaskFilter.Blur.INNER;
                    break;
                case R.id.rbOuter:
                    blurStyle = BlurMaskFilter.Blur.OUTER;
                    break;
                case R.id.rbSolid:
                    blurStyle = BlurMaskFilter.Blur.SOLID;
                    break;
                default:
                    blurStyle = BlurMaskFilter.Blur.NORMAL;
            }
            maskFilter = new BlurMaskFilter(blurRadius, blurStyle);
        } else if (checkedId == R.id.rbEmboss) {
            /*-------------------------使用EmbossMaskFilter--------------------------*/
            //更新UI界面
            rgBlurStyle.setVisibility(View.GONE);
            layoutDirectionX.setVisibility(View.VISIBLE);
            layoutDirectionY.setVisibility(View.VISIBLE);
            layoutDirectionZ.setVisibility(View.VISIBLE);
            layoutAmbient.setVisibility(View.VISIBLE);
            layoutSpecular.setVisibility(View.VISIBLE);
            layoutBlurRadius.setVisibility(View.VISIBLE);

            int directionX = getDirectionX();
            tvDirectionXNumber.setText(Integer.toString(directionX));
            int directionY = getDirectionY();
            tvDirectionYNumber.setText(Integer.toString(directionY));
            int directionZ = getDirectionZ();
            tvDirectionZNumber.setText(Integer.toString(directionZ));
            float ambient = getAmbient();
            tvAmbientNumber.setText(Float.toString(ambient));
            float specular = getSpecular();
            tvSpecularNumber.setText(Float.toString(specular));
            int blurRadius = getBlurRadius();
            tvBlurNumber.setText(Integer.toString(blurRadius));

            //创建EmbossMaskFilter
            float[] direction = {directionX, directionY, directionZ};
            maskFilter = new EmbossMaskFilter(direction, ambient, specular, blurRadius);
        }

        //将根据UI界面创建的MaskFilter赋值给myView
        maskFilterView.setMaskFilterForPaint(maskFilter);
    }

    //为EmbossMaskFilter获取方向的x分量
    private int getDirectionX() {
        int x = seekBarX.getProgress();
        x -= seekBarX.getMax() / 2;
        return x;
    }

    //为EmbossMaskFilter获取方向的y分量
    private int getDirectionY() {
        int y = seekBarY.getProgress();
        y -= seekBarY.getMax() / 2;
        return y;
    }

    //为EmbossMaskFilter获取方向的z分量
    private int getDirectionZ() {
        int z = seekBarZ.getProgress();
        z -= seekBarZ.getMax() / 2;
        return z;
    }

    private float getAmbient() {
        return seekBarAmbient.getProgress() * 1.0f / seekBarAmbient.getMax();
    }

    private float getSpecular() {
        return seekBarSpecular.getProgress() * 1.0f / seekBarSpecular.getMax();
    }

    private int getBlurRadius() {
        int blurRadius = seekBarBlurRadius.getProgress();
        if (blurRadius <= 0) {
            blurRadius = 1;
        }
        return blurRadius;
    }

    @Override
    protected void onDestroy() {
        maskFilterView.destroy();
        super.onDestroy();
    }
}