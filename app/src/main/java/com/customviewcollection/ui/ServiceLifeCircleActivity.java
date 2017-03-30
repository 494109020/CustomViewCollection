package com.customviewcollection.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.LifeCircleService;
import com.customviewcollection.R;

/**
 * Created by Magina on 2/17/17.
 * 类功能介绍:
 */
public class ServiceLifeCircleActivity extends BaseActivity {

    StringBuilder sb = new StringBuilder();
    private TextView tv;
    private String NEW_LINE = "\r\n";
    private ServiceConnection sc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_life_circle);

        View btn_bind = findViewById(R.id.btn_bind);
        View btn_unbind = findViewById(R.id.btn_unbind);
        View btn_start = findViewById(R.id.btn_start);
        View btn_stop = findViewById(R.id.btn_stop);

        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceLifeCircleActivity.this, LifeCircleService.class);
                sc = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                bindService(intent, sc, BIND_AUTO_CREATE);
            }
        });

        btn_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sc != null) {
                    unbindService(sc);
                    sc = null;
                }
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceLifeCircleActivity.this, LifeCircleService.class);
                startService(intent);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceLifeCircleActivity.this, LifeCircleService.class);
                stopService(intent);
            }
        });

        tv = (TextView) findViewById(R.id.tv);
    }

}
