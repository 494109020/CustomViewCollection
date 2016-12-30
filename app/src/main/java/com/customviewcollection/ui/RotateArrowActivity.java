package com.customviewcollection.ui;

import android.os.Bundle;
import android.view.View;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.RotateArrowView;

public class RotateArrowActivity extends BaseActivity implements View.OnClickListener {

    private RotateArrowView mRa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_arrow);

        View btn_1 = findViewById(R.id.btn_1);
        View btn_2 = findViewById(R.id.btn_2);
        View btn_3 = findViewById(R.id.btn_3);
        View btn_4 = findViewById(R.id.btn_4);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);

        mRa = (RotateArrowView) findViewById(R.id.ra);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mRa.setSimpleSize(1);
                break;
            case R.id.btn_2:
                mRa.setSimpleSize(2);
                break;
            case R.id.btn_3:
                mRa.setSimpleSize(4);
                break;
            case R.id.btn_4:
                mRa.setSimpleSize(8);
                break;
        }
    }
}
