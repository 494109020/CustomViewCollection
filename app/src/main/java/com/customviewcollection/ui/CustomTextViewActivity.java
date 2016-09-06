package com.customviewcollection.ui;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.customviewcollection.R;
import com.customviewcollection.view.CustomTextView;

public class CustomTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_textview);
        final CustomTextView ctv = (CustomTextView) findViewById(R.id.ctv);

        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctv.setTextAlign(Paint.Align.LEFT);
            }
        });
        findViewById(R.id.btn_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctv.setTextAlign(Paint.Align.CENTER);
            }
        });
        findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctv.setTextAlign(Paint.Align.RIGHT);
            }
        });
    }

}
