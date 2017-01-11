package com.customviewcollection.ui;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.BlurView;

/**
 * Created by Magina on 1/10/17.
 * 类功能介绍:
 */
public class BlurActivity extends BaseActivity implements View.OnClickListener {

    private BlurView mBv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        View btn_1 = findViewById(R.id.btn_1);
        View btn_2 = findViewById(R.id.btn_2);
        View btn_3 = findViewById(R.id.btn_3);
        View btn_4 = findViewById(R.id.btn_4);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);

        mBv = (BlurView) findViewById(R.id.bv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mBv.setBlurType(BlurMaskFilter.Blur.NORMAL);
                break;
            case R.id.btn_2:
                mBv.setBlurType(BlurMaskFilter.Blur.SOLID);
                break;
            case R.id.btn_3:
                mBv.setBlurType(BlurMaskFilter.Blur.OUTER);
                break;
            case R.id.btn_4:
                mBv.setBlurType(BlurMaskFilter.Blur.INNER);
                break;
        }
    }
}
