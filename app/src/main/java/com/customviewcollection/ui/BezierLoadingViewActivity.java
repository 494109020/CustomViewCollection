package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.BezierLoadingView;

/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 */
public class BezierLoadingViewActivity extends BaseActivity {

    private static final String TAG = "BezierLoadingView";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezierloading_view);
        BezierLoadingView bv = (BezierLoadingView) findViewById(R.id.bv);
        bv.start();
    }


}
