package com.customviewcollection.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.MatrixView;

/**
 * Created by Magina on 16/10/8.
 * 类功能介绍:
 * 初步学习Scene以及Transition的使用。
 */

public class MatrixActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SceneActivity";
    private FrameLayout flContainer;
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
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
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
        }
    }
}
