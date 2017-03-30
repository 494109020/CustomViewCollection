package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.customviewcollection.BaseActivity;
import com.lib.a.T;

/**
 * Created by Magina on 17/3/15.
 * 类介绍:
 */

public class AptAnnotationActivity extends BaseActivity {

    private static final String TAG = AptAnnotationActivity.class.getSimpleName();

    @T(value = "asdfasf")
    private void process() {
        Log.i(TAG, "SimpaleName = " + AptAnnotationActivity.class.getSimpleName());
        Log.i(TAG, "CanonicalName = " + AptAnnotationActivity.class.getCanonicalName());
        Log.i(TAG, "Name = " + AptAnnotationActivity.class.getName());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));
        process();
    }
}
