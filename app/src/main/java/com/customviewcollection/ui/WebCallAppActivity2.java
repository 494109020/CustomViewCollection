package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;

/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 */
public class WebCallAppActivity2 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_call_app);
        TextView tv = (TextView) findViewById(R.id.tv);
        String scheme = getIntent().getScheme();
        if (getString(R.string.scheme).equals(scheme))
            tv.setText("网页调起的");
    }


}
