package com.customviewcollection.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.XfermodeView;

/**
 * Created by Magina on 1/9/17.
 * 类功能介绍:
 */
public class XfermodeActivity extends BaseActivity implements View.OnClickListener {

    private XfermodeView mXv;
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfermode);

        View btn_1 = findViewById(R.id.btn_1);
        View btn_2 = findViewById(R.id.btn_2);
        View btn_3 = findViewById(R.id.btn_3);
        View btn_4 = findViewById(R.id.btn_4);
        View btn_5 = findViewById(R.id.btn_5);
        View btn_6 = findViewById(R.id.btn_6);
        View btn_7 = findViewById(R.id.btn_7);
        View btn_8 = findViewById(R.id.btn_8);
        View btn_9 = findViewById(R.id.btn_9);
        View btn_10 = findViewById(R.id.btn_10);
        View btn_11 = findViewById(R.id.btn_11);
        View btn_12 = findViewById(R.id.btn_12);
        View btn_13 = findViewById(R.id.btn_13);
        View btn_14 = findViewById(R.id.btn_14);
        View btn_15 = findViewById(R.id.btn_15);
        View btn_16 = findViewById(R.id.btn_16);
        View btn_17 = findViewById(R.id.btn_17);
        View btn_18 = findViewById(R.id.btn_18);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_10.setOnClickListener(this);
        btn_11.setOnClickListener(this);
        btn_12.setOnClickListener(this);
        btn_13.setOnClickListener(this);
        btn_14.setOnClickListener(this);
        btn_15.setOnClickListener(this);
        btn_16.setOnClickListener(this);
        btn_17.setOnClickListener(this);
        btn_18.setOnClickListener(this);

        mXv = (XfermodeView) findViewById(R.id.xv);
        mTv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mXv.setMode(PorterDuff.Mode.SRC);
                mTv.setText("SRC：[Sa, Sc]");
                break;
            case R.id.btn_2:
                mXv.setMode(PorterDuff.Mode.DST);
                mTv.setText("DST：[Da, Dc]");
                break;
            case R.id.btn_3:
                mXv.setMode(PorterDuff.Mode.SRC_IN);
                mTv.setText("SRC_IN：[Sa * Da, Sc * Da]");
                break;
            case R.id.btn_4:
                mXv.setMode(PorterDuff.Mode.DST_IN);
                mTv.setText("DST_IN：[Sa * Da, Sa * Dc]");
                break;
            case R.id.btn_5:
                mXv.setMode(PorterDuff.Mode.SRC_OUT);
                mTv.setText("SRC_OUT：[Sa * (1 - Da), Sc * (1 - Da)]");
                break;
            case R.id.btn_6:
                mXv.setMode(PorterDuff.Mode.DST_OUT);
                mTv.setText("DST_OUT：[Da * (1 - Sa), Dc * (1 - Sa)]");
                break;
            case R.id.btn_7:
                mXv.setMode(PorterDuff.Mode.SRC_OVER);
                mTv.setText("SRC_OVER：[Sa + (1 - Sa)*Da, Rc = Sc + (1 - Sa)*Dc]");
                break;
            case R.id.btn_8:
                mXv.setMode(PorterDuff.Mode.DST_OVER);
                mTv.setText("DST_OVER：[Sa + (1 - Sa)*Da, Rc = Dc + (1 - Da)*Sc]");
                break;
            case R.id.btn_9:
                mXv.setMode(PorterDuff.Mode.SRC_ATOP);
                mTv.setText("SRC_ATOP：[Da, Sc * Da + (1 - Sa) * Dc]");
                break;
            case R.id.btn_10:
                mXv.setMode(PorterDuff.Mode.DST_ATOP);
                mTv.setText("DST_ATOP：[Sa, Sa * Dc + Sc * (1 - Da)]");
                break;
            case R.id.btn_11:
                mXv.setMode(PorterDuff.Mode.CLEAR);
                mTv.setText("CLEAR：[0, 0]");
                break;
            case R.id.btn_12:
                mXv.setMode(PorterDuff.Mode.XOR);
                mTv.setText("XOR：[Sa + Da - 2 * Sa * Da, Sc * (1 - Da) + (1 - Sa) * Dc]");
                break;
            case R.id.btn_13:
                mXv.setMode(PorterDuff.Mode.DARKEN);
                mTv.setText("DARKEN：[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)]");
                break;
            case R.id.btn_14:
                mXv.setMode(PorterDuff.Mode.LIGHTEN);
                mTv.setText("LIGHTEN：[Sa + Da - Sa*Da, Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]");
                break;
            case R.id.btn_15:
                mXv.setMode(PorterDuff.Mode.ADD);
                mTv.setText("ADD：Saturate(S + D)");
                break;
            case R.id.btn_16:
                mXv.setMode(PorterDuff.Mode.MULTIPLY);
                mTv.setText("MULTIPLY：[Sa * Da, Sc * Dc]");
                break;
            case R.id.btn_17:
                mXv.setMode(PorterDuff.Mode.SCREEN);
                mTv.setText("SCREEN：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc]");
                break;
            case R.id.btn_18:
                mXv.setMode(PorterDuff.Mode.OVERLAY);
                mTv.setText("OVERLAY：Saturate(S + D)");
                break;
        }
    }
}
