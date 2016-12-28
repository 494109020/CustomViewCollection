package com.customviewcollection.ui;

import android.os.Bundle;
import android.view.View;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.MatrixView;

/**
 * Created by Magina on 16/10/8.
 * 类功能介绍:
 * 初步学习Matrix的使用。以及离屏缓冲不同类型下的表现
 */

public class MatrixActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MatrixActivity";
    private MatrixView mMv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        mMv = (MatrixView) findViewById(R.id.mv);
        View btn_1 = findViewById(R.id.btn_1);
        View btn_2 = findViewById(R.id.btn_2);
        View btn_3 = findViewById(R.id.btn_3);
        View btn_4 = findViewById(R.id.btn_4);
        View btn_5 = findViewById(R.id.btn_5);
        View btn_6 = findViewById(R.id.btn_6);
        View btn_7 = findViewById(R.id.btn_7);
        View btn_8 = findViewById(R.id.btn_8);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);

        View btn_none = findViewById(R.id.btn_none);
        View btn_hardware = findViewById(R.id.btn_hardware);
        View btn_software = findViewById(R.id.btn_software);
        btn_none.setOnClickListener(this);
        btn_hardware.setOnClickListener(this);
        btn_software.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mMv.TpostS();
                break;
            case R.id.btn_2:
                mMv.TpreS();
                break;
            case R.id.btn_3:
                mMv.TpostR();
                break;
            case R.id.btn_4:
                mMv.TpreR();
                break;
            case R.id.btn_5:
                mMv.SpostR();
                break;
            case R.id.btn_6:
                mMv.SpreR();
                break;
            case R.id.btn_7:
                mMv.clipOpen();
                break;
            case R.id.btn_8:
                mMv.clipClose();
                break;
            case R.id.btn_none:
                mMv.setLayerType(View.LAYER_TYPE_NONE, null);
                break;
            case R.id.btn_hardware:
                mMv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                break;
            case R.id.btn_software:
                mMv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                break;
        }
    }
}
