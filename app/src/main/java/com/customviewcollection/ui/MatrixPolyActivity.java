package com.customviewcollection.ui;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.MatrixPolyView;

/**
 * Created by Magina on 1/3/17.
 * 类功能介绍:
 */

public class MatrixPolyActivity extends BaseActivity implements View.OnClickListener {

    private MatrixPolyView mMpv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_poly);

        mMpv = (MatrixPolyView) findViewById(R.id.mpv);

        RadioGroup group = (RadioGroup) findViewById(R.id.group);
        assert group != null;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.point0:
                        mMpv.setTestPoint(0);
                        break;
                    case R.id.point1:
                        mMpv.setTestPoint(1);
                        break;
                    case R.id.point2:
                        mMpv.setTestPoint(2);
                        break;
                    case R.id.point3:
                        mMpv.setTestPoint(3);
                        break;
                    case R.id.point4:
                        mMpv.setTestPoint(4);
                        break;
                }
            }
        });


        View btn_1 = findViewById(R.id.btn_1);
        View btn_2 = findViewById(R.id.btn_2);
        View btn_3 = findViewById(R.id.btn_3);
        View btn_4 = findViewById(R.id.btn_4);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mMpv.setRect(Matrix.ScaleToFit.CENTER);
                break;
            case R.id.btn_2:
                mMpv.setRect(Matrix.ScaleToFit.END);
                break;
            case R.id.btn_3:
                mMpv.setRect(Matrix.ScaleToFit.FILL);
                break;
            case R.id.btn_4:
                mMpv.setRect(Matrix.ScaleToFit.START);
                break;
        }
    }
}
