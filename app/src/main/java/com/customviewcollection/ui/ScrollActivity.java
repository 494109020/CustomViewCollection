package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.CScrollView;

/**
 * Created by Magina on 1/4/17.
 * 类功能介绍:
 */
public class ScrollActivity extends BaseActivity implements View.OnClickListener {

    private CScrollView mCsv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

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


        mCsv = (CScrollView) findViewById(R.id.csv);
        mCsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScrollActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mCsv.setScroll(CScrollView.Type.SCROLLTO);
                break;
            case R.id.btn_2:
                mCsv.setScroll(CScrollView.Type.SCROLLBY);
                break;
            case R.id.btn_3:
                mCsv.setScroll(CScrollView.Type.SETTRANSLATIONXY);
                break;
            case R.id.btn_4:
                mCsv.setScroll(CScrollView.Type.SETXY);
                break;
            case R.id.btn_5:
                mCsv.setScroll(CScrollView.Type.OFFSET);
                break;
            case R.id.btn_6:
                mCsv.startScroll();
                break;
        }
    }
}
