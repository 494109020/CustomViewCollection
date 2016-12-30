package com.customviewcollection.ui;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.PathView;

/**
 * Created by Magina on 12/28/16.
 * 类功能介绍:  注意要关闭硬件加速
 */

public class PathFillActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTv;
    private PathView mPv;
    private StringBuilder mSb;
    String rule = "";
    String innerDirection = "";
    String outerDirection = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        View btn_even_odd = findViewById(R.id.btn_even_odd);
        View btn_inverse_even_odd = findViewById(R.id.btn_inverse_even_odd);
        View btn_winding = findViewById(R.id.btn_winding);
        View btn_inverse_winding = findViewById(R.id.btn_inverse_winding);
        View btn_inner_cw = findViewById(R.id.btn_inner_cw);
        View btn_inner_ccw = findViewById(R.id.btn_inner_ccw);
        View btn_outer_cw = findViewById(R.id.btn_outer_cw);
        View btn_outer_ccw = findViewById(R.id.btn_outer_ccw);
        btn_even_odd.setOnClickListener(this);
        btn_inverse_even_odd.setOnClickListener(this);
        btn_winding.setOnClickListener(this);
        btn_inverse_winding.setOnClickListener(this);
        btn_inner_cw.setOnClickListener(this);
        btn_inner_ccw.setOnClickListener(this);
        btn_outer_cw.setOnClickListener(this);
        btn_outer_ccw.setOnClickListener(this);

        mTv = (TextView) findViewById(R.id.tv);
        mPv = (PathView) findViewById(R.id.pv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_even_odd:
                mPv.setRule(Path.FillType.EVEN_ODD);
                rule = "奇偶规则";
                break;
            case R.id.btn_inverse_even_odd:
                mPv.setRule(Path.FillType.INVERSE_EVEN_ODD);
                rule = "反奇偶规则";
                break;
            case R.id.btn_winding:
                mPv.setRule(Path.FillType.WINDING);
                rule = "非零环绕数规则";
                break;
            case R.id.btn_inverse_winding:
                mPv.setRule(Path.FillType.INVERSE_WINDING);
                rule = "反非零环绕数规则";
                break;
            case R.id.btn_inner_cw:
                mPv.setInnerDirection(Path.Direction.CW);
                innerDirection = "内顺";
                break;
            case R.id.btn_inner_ccw:
                mPv.setInnerDirection(Path.Direction.CCW);
                innerDirection = "内逆";
                break;
            case R.id.btn_outer_cw:
                mPv.setOuterDirection(Path.Direction.CW);
                outerDirection = "外顺";
                break;
            case R.id.btn_outer_ccw:
                mPv.setOuterDirection(Path.Direction.CCW);
                outerDirection = "外逆";
                break;
        }
        mSb = new StringBuilder();
        mSb.append(innerDirection).append("-----")
                .append(outerDirection).append("-----")
                .append(rule);
        mTv.setText(mSb.toString());
    }
}
