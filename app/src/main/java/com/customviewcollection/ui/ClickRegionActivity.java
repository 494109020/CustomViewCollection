package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.ClickRegionView;

/**
 * Created by Magina on 1/6/17.
 * 类功能介绍:
 */
public class ClickRegionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_region);

        ClickRegionView crv = (ClickRegionView) findViewById(R.id.crv);
    }
}
