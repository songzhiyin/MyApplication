package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.View.WaveView;

/**
 * 水波纹
 */
public class WaterWaveActivity extends BaseActivity {
    private ImageView imageView;
    private WaveView waveView;
    private FrameLayout.LayoutParams lp;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_water_wave;
    }

    @Override
    protected void initViews() {
        super.initViews();
        imageView = $(R.id.image);
        waveView = $(R.id.wave_view);
        lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        setTextTitleName("水波纹");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 2);
                imageView.setLayoutParams(lp);
            }
        });
    }
}
