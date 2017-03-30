package com.customviewcollection.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;

/**
 * Created by Magina on 2/8/17.
 * 类功能介绍:
 */
public class BlueToothActivity extends BaseActivity {
    private static final String TAG = BlueToothActivity.class.getSimpleName();

//    ACTION_STATE_CHANGED                    蓝牙状态值发生改变
//    ACTION_SCAN_MODE_CHANGED         蓝牙扫描状态(SCAN_MODE)发生改变
//    ACTION_DISCOVERY_STARTED             蓝牙扫描过程开始
//    ACTION_DISCOVERY_FINISHED             蓝牙扫描过程结束
//    ACTION_LOCAL_NAME_CHANGED        蓝牙设备Name发生改变
//    ACTION_REQUEST_DISCOVERABLE       请求用户选择是否使该蓝牙能被扫描
//    PS：如果蓝牙没有开启，用户点击确定后，会首先开启蓝牙，继而设置蓝牙能被扫描。
//    ACTION_REQUEST_ENABLE                  请求用户选择是否打开蓝牙
//    上面的在BluetoothAdapter中
//    ACTION_FOUND  (该常量字段位于BluetoothDevice类中)
//    说明：蓝牙扫描时，扫描到任一远程蓝牙设备时，会发送此广播。

    private BluetoothAdapter mBluetoothAdapter;
    private BT mBT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        mBT = new BT();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBT, intentFilter);

        View btn_start = findViewById(R.id.btn_start);
        View btn_scan = findViewById(R.id.btn_scan);
        View btn_stop = findViewById(R.id.btn_stop);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    // 这是一种打开方式
                    boolean enable = mBluetoothAdapter.enable();
                    Toast.makeText(BlueToothActivity.this, "蓝牙打开" + (enable ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
                    // 这是另一种，通过系统界面去打开。 我们可以在onActivityResult()方法去处理返回值
//                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(intent, 1);

                }
            }
        });
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean discovery = mBluetoothAdapter.startDiscovery();
                Toast.makeText(BlueToothActivity.this, "蓝牙扫描" + (discovery ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                }, 60 * 1000);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean disable = mBluetoothAdapter.disable();
                Toast.makeText(BlueToothActivity.this, "蓝牙关闭" + (disable ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class BT extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (btDevice != null) {
                Log.i(TAG, "Name : " + btDevice.getName() + " Address: " + btDevice.getAddress());
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mBT);
    }
}
