package com.customviewcollection.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.media.SonicSender;

public class PureToneActivity extends BaseActivity {

    private SonicSender sonicSender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pure_tone);
        sonicSender = new SonicSender();

        final EditText et = (EditText) findViewById(R.id.et_hz);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonicSender.stop();
                sonicSender.setIdstr(et.getText().toString().trim());
                sonicSender.start();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonicSender.stop();
            }
        });
    }

    @Override
    public void finish() {
        sonicSender.stop();
        super.finish();
    }
}