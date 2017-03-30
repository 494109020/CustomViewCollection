package com.customviewcollection.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.customviewcollection.R;

public class ProgressWithBtnActivity extends Activity implements OnClickListener {

    private int progress;
    private TextView btn;
    private ProgressBar pbBar;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progress += 5;
                    if (progress < 100) {
                        btn.setText(progress + "%");
                        handler.sendEmptyMessageDelayed(1, 500);
                    } else if (progress == 100) {
                        btn.setText("下载完成");
                    }
                    pbBar.setProgress(progress);

                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresswithbtn);

        btn = (TextView) findViewById(R.id.btn_progress);
        pbBar = (ProgressBar) findViewById(R.id.pb);
        // btnPro.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // while (progress < 100) {
        // try {
        // Thread.sleep(500);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // progress += 5;
        // pbBar.setProgress(progress);
        // if (progress == 100) {
        // btnPro.setText("下载完成");
        // } else {
        // btnPro.setText(progress + "%");
        // }
        //
        // }
        // }
        // });
        btn.setOnClickListener(this);
    }

    // 主线程while循环
//    @Override
//    public void onClick(View v) {
//        // 估计是耗时问题导致的UI界面不更新.最好还是使用handler.
//        while (progress < 100) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            progress += 5;
//            if (progress == 100) {
//                btn.setText("下载完成");
//            } else if (progress < 100) {
//                btn.setText(progress + "%");
//                Log.i("TAG", "进入设置了");
//
//            }
//            Log.i("TAG", "pp=" + progress);
//            pbBar.setProgress(progress);
//        }
//    }

    //    handler发送消息
    @Override
    public void onClick(View v) {
        pbBar.setProgress(0);
        Message msg = Message.obtain();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    // 子线程中的while循环
    // @Override
    // public void onClick(View v) {
    // new Thread() {
    // @Override
    // public void run() {
    // while (progress < 100) {
    // try {
    // Thread.sleep(500);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // progress += 5;
    // if (progress == 100) {
    // MainActivity.this.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // btn.setText("下载完成");
    //
    // }
    // });
    // } else if (progress < 100) {
    // MainActivity.this.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // btn.setText(progress + "%");
    // Log.i("TAG", "进行设置");
    // }
    // });
    // }
    // MainActivity.this.runOnUiThread(new Runnable() {
    //
    // @Override
    // public void run() {
    // pbBar.setProgress(progress);
    // }
    // });
    //
    // }
    // }
    // }.start();
    // }

}
