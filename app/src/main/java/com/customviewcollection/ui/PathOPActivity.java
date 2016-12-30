package com.customviewcollection.ui;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.PathOPView;

/**
 * Created by Magina on 12/28/16.
 * 类功能介绍:  注意要关闭硬件加速
 */

public class PathOPActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTv;
    private PathOPView mPv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_op);

        View btn_1 = findViewById(R.id.btn_1);
        View btn_2 = findViewById(R.id.btn_2);
        View btn_3 = findViewById(R.id.btn_3);
        View btn_4 = findViewById(R.id.btn_4);
        View btn_5 = findViewById(R.id.btn_5);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);

        mTv = (TextView) findViewById(R.id.tv);
        mPv = (PathOPView) findViewById(R.id.pv);
    }

    @Override
    public void onClick(View v) {
        String rule = "";
        switch (v.getId()) {
            case R.id.btn_1:
                mPv.setOP(Path.Op.DIFFERENCE);
                rule = "正向差集";
                break;
            case R.id.btn_2:
                mPv.setOP(Path.Op.INTERSECT);
                rule = "交集";
                break;
            case R.id.btn_3:
                mPv.setOP(Path.Op.UNION);
                rule = "并集";
                break;
            case R.id.btn_4:
                mPv.setOP(Path.Op.XOR);
                rule = "除去交集的部分(异或)";
                break;
            case R.id.btn_5:
                mPv.setOP(Path.Op.REVERSE_DIFFERENCE);
                rule = "反向差集";
                break;
        }
        mTv.setText(rule);
    }
}
