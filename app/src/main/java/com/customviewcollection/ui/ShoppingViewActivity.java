package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;

/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 */
public class ShoppingViewActivity extends BaseActivity {

    private static final String TAG = "ShoppingView";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_view);
    }


}
