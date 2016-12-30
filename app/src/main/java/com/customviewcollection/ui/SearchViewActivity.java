package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.SearchView;

/**
 * Created by Magina on 12/30/16.
 * 类功能介绍:
 */
public class SearchViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        final SearchView sv = (SearchView) findViewById(R.id.sv);

        View btn_start = findViewById(R.id.btn_start);
        View btn_stop = findViewById(R.id.btn_stop);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.start();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.stop();
            }
        });
    }
}
