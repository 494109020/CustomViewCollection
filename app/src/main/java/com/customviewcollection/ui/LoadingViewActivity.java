package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.SlackLoadingView;

/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 */
public class LoadingViewActivity extends BaseActivity {

    private static final String TAG = "LoadingView";

    private boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        final SlackLoadingView sv = (SlackLoadingView) findViewById(R.id.sv);
        final Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    sv.start();
                    btn.setText("暂停");
                } else {
                    sv.stop();
                    btn.setText("开始");
                }
                flag = !flag;
            }
        });

    }


}
