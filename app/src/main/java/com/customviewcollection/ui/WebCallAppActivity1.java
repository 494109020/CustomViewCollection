package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;

/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 */
public class WebCallAppActivity1 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_call_app);
        WebView webView = (WebView) findViewById(R.id.wv);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl("file:///android_asset/test.html");
    }


}
