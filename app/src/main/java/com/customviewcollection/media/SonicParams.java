package com.customviewcollection.media;

import android.media.AudioFormat;
import android.util.Log;

/**
 * Created by jiang on 2017/1/6.
 */

public class SonicParams {
    public final static int sampleRateInHz = 44100;
    public final static int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    public final static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    public final static int DEFAULT_BUFFER_SIZE = 8192;
    public static final int AMPLITUDE = 16384;

    public static final int BASE_FREQ = 17000;
    public static final int STEP = 100;
    public static final int BANDWIDTH = 49;
    public static int MAX_FREQ;
    public static int MIN_FREQ;
    public static int START_FREQ;
    public static int INT_FREQ;
    public static int END_FREQ;

    public static int[] FREQUENCY;
    public static final int DURATION = 45;//毫秒

    static {
        FREQUENCY = new int[13];
        for (int i = 0; i < 10; i++) {//0-9
            FREQUENCY[i] = BASE_FREQ + i * STEP;
        }
        START_FREQ = BASE_FREQ + 12 * STEP;
        INT_FREQ = BASE_FREQ + 13 * STEP;
        END_FREQ = BASE_FREQ + 14 * STEP;

        FREQUENCY[10] = START_FREQ;
        FREQUENCY[11] = INT_FREQ;
        FREQUENCY[12] = END_FREQ;

        MIN_FREQ = FREQUENCY[0] - STEP * 5;
        MAX_FREQ = FREQUENCY[12] + STEP * 5;
    }


    public static void dumpFREQUENCY() {
        for (int i = 0; i < FREQUENCY.length; i++) {
            Log.d("fff", "[" + i + "]: " + FREQUENCY[i]);
        }
    }
}
