package com.customviewcollection.ui;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.util.Utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Magina on 2/7/17.
 * 类功能介绍:录音相关。记得注册权限。另注意要放在工作线程中去执行。
 */

public class MediaRecordActivity extends BaseActivity {


    private static final int STATE_NO_PERMISSION = -1;
    private static final int STATE_RECORDING = 1;
    private static final int STATE_SUCCESS = 2;
    private static AudioRecord mAudioRecord;
    private Handler mSyncHandler;
    private boolean isRecording;
    private File tempFile;
    private Handler mHandler = new Handler();
    private String mFileName;
    private File fpath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediarecord);

        View btn_start = findViewById(R.id.btn_start);
        View btn_stop = findViewById(R.id.btn_stop);

        //在这里我们创建一个文件，用于保存录制内容
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/files/";
        Log.i("TAG", path + "");
        fpath = new File(path);
        if (!fpath.exists()) {
            fpath.mkdirs();//创建文件夹
        } else {

        }

        HandlerThread handlerThread = new HandlerThread("AudioRecord");
        handlerThread.start();

        mSyncHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    getRecordState();
                }
            }
        };

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    mSyncHandler.sendEmptyMessage(1);
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecording = false;
            }
        });
    }


    private void stopRecording() {
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }

    public int getRecordState() {


        mFileName = String.valueOf(System.currentTimeMillis()) + ".pcm";
        tempFile = new File(fpath, mFileName);

        // 拿到最小缓冲区大小
        // 44100 是音频的采样频率，每秒钟能够采样的次数，采样率越高，音质越高。给出的实例是44100、22050、11025但不限于这几个参数。
        // 声道设置：android支持双声道立体声和单声道。MONO单声道，STEREO立体声
        // 编码制式和采样大小：采集来的数据当然使用PCM编码(脉冲代码调制编码，即PCM编码。PCM通过抽样、量化、编码三个步骤将连续变化的模拟信号转换为数字编码。) android支持的采样大小16bit 或者8bit。当然采样大小越大，那么信息量越多，音质也越高，现在主流的采样大小都是16bit，在低质量的语音传输的时候8bit 足够了。
        final int minBuffer = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, (minBuffer * 100));
        short[] point = new short[minBuffer];
        try {
            mAudioRecord.startRecording();//检测是否可以进入初始化状态
        } catch (Exception e) {
            if (mAudioRecord != null) {
                mAudioRecord.release();
                mAudioRecord = null;
                Log.d("CheckAudioPermission", "无法进入录音初始状态");
            }
            return STATE_NO_PERMISSION;
        }
        if (mAudioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0以下机型都会返回此状态，故使用时需要判断bulid版本
            //检测是否在录音中
            if (mAudioRecord != null) {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
                Log.d("CheckAudioPermission", "录音机被占用");
            }
            return STATE_RECORDING;
        } else {
            isRecording = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isRecording = false;
                }
            }, 1000);
            DataOutputStream dos = null;
            try {
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tempFile)));
                int readSize = 0;
                //检测是否可以获取录音结果
                while (isRecording) {
                    readSize = mAudioRecord.read(point, 0, point.length);
                    if (readSize <= 0) {
                        stopRecording();
                        Log.d("CheckAudioPermission", "录音的结果为空");
                        return STATE_NO_PERMISSION;
                    } else {
                        for (int i = 0; i < readSize; i++) {
                            dos.writeShort(point[i]);
                        }
                    }
                }
                dos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Utils.close(dos);
            }
        }
        stopRecording();
        return STATE_SUCCESS;
    }
}
