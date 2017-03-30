package com.customviewcollection.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.util.ArrayList;

/**
 * Created by jiang on 2017/1/10.
 */

public class SonicSender {
    private static final String TAG = "send";
    private AudioTrack audioTrack;
    boolean is_sending = false;
    Thread toneThread;
    private String idstr = "12345678";//8个数字0-9
    ArrayList arrayList;
    short[] toneArr;

    private int sendInterval = 1;//单位秒

    public SonicSender() {
        try {
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SonicParams.sampleRateInHz,
                    AudioFormat.CHANNEL_OUT_MONO, SonicParams.audioFormat,
                    SonicParams.DEFAULT_BUFFER_SIZE,
                    AudioTrack.MODE_STREAM);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private short[] concatenateArrays(short[] a1, short[] a2) {
        short[] data = new short[a1.length + a2.length];
        for (int i = 0; i < a1.length; i++) {
            data[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            data[i + a1.length] = a2[i];
        }
        return data;
    }

    private void genToneArr() {
        arrayList = new ArrayList();
        String idstrck = Encoder.addcheck(idstr);
        ArrayList arrayListFreq = Encoder.messageToFreq(idstrck);
        for (int i = 0; i < arrayListFreq.size(); i++) {
            short[] tone = Encoder.genTone((int) arrayListFreq.get(i), SonicParams.DURATION);
            arrayList.add(tone);
        }
        toneArr = new short[0];
        for (int i = 0; i < arrayList.size(); i++) {
            short[] tone = (short[]) arrayList.get(i);
            toneArr = concatenateArrays(toneArr, tone);
        }
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public void setSendInterval(int sendInterval) {
        this.sendInterval = sendInterval;
    }

    public void start() {
        genToneArr();
        is_sending = true;
        toneThread = new Thread(new Runnable() {
            @Override
            public void run() {
                audioTrack.play();
                while (is_sending) {
                    audioTrack.write(toneArr, 0, toneArr.length);
                    try {
                        Thread.sleep(sendInterval * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                audioTrack.pause();
                audioTrack.flush();
                audioTrack.stop();
//                audioTrack.release();

            }
        });
        toneThread.start();
    }

    public void stop() {
        if (is_sending) {
            is_sending = false;
            toneThread.interrupt();
        }
    }
}
