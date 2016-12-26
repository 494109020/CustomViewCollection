package com.customviewcollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.customviewcollection.ui.BezierLoadingViewActivity;
import com.customviewcollection.ui.CustomTextViewActivity;
import com.customviewcollection.ui.DynamicProxyActivity;
import com.customviewcollection.ui.LoadingViewActivity;
import com.customviewcollection.ui.MatrixActivity;
import com.customviewcollection.ui.RxJavaActivity;
import com.customviewcollection.ui.SceneActivity;
import com.customviewcollection.ui.ShoppingViewActivity;
import com.customviewcollection.ui.WebCallAppActivity1;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View btn_custom1, btn_custom2, btn_custom3,
            btn_custom4, btn_custom5, btn_custom6, btn_custom7, btn_custom8,
            btn_custom9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_custom1 = findViewById(R.id.btn_custom1);
        btn_custom2 = findViewById(R.id.btn_custom2);
        btn_custom3 = findViewById(R.id.btn_custom3);
        btn_custom4 = findViewById(R.id.btn_custom4);
        btn_custom5 = findViewById(R.id.btn_custom5);
        btn_custom6 = findViewById(R.id.btn_custom6);
        btn_custom7 = findViewById(R.id.btn_custom7);
        btn_custom8 = findViewById(R.id.btn_custom8);
        btn_custom9 = findViewById(R.id.btn_custom9);
        btn_custom1.setOnClickListener(this);
        btn_custom2.setOnClickListener(this);
        btn_custom3.setOnClickListener(this);
        btn_custom4.setOnClickListener(this);
        btn_custom5.setOnClickListener(this);
        btn_custom6.setOnClickListener(this);
        btn_custom7.setOnClickListener(this);
        btn_custom8.setOnClickListener(this);
        btn_custom9.setOnClickListener(this);
    }

    private void start(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_custom1:
                start(CustomTextViewActivity.class);
                break;
            case R.id.btn_custom2:
                start(RxJavaActivity.class);
                break;
            case R.id.btn_custom3:
                start(ShoppingViewActivity.class);
                break;
            case R.id.btn_custom4:
                start(LoadingViewActivity.class);
                break;
            case R.id.btn_custom5:
                start(BezierLoadingViewActivity.class);
                break;
            case R.id.btn_custom6:
                start(SceneActivity.class);
                break;
            case R.id.btn_custom7:
                start(DynamicProxyActivity.class);
                break;
            case R.id.btn_custom8:
                start(WebCallAppActivity1.class);
                break;
            case R.id.btn_custom9:
                start(MatrixActivity.class);
                break;
        }
    }

}
