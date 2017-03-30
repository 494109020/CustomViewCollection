package com.customviewcollection.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioTrackManager {
    public static final int RATE = 44100;
    public static final float MAX_VOLUME = 100f;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int DOUBLE = 3;

    AudioTrack audioTrack;
    float volume;
    int channel;
    int minLength;
    int length;
    private short[] wave;

    public AudioTrackManager() {
        minLength = AudioTrack.getMinBufferSize(RATE, AudioFormat.CHANNEL_CONFIGURATION_STEREO, // CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        length = minLength;
    }

    public void start(int frequency, int amplitude) {
        stop();
        if (frequency > 0) {
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_STEREO, // CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, length, AudioTrack.MODE_STREAM);

//            wave = generateData(frequency, amplitude);
            wave = genTone(frequency, amplitude);
            if (audioTrack != null) {
                audioTrack.play();
            }
        }

    }

    private short[] generateData(int frequency, int amplitude) {
        short[] wave = new short[RATE];
        int waveLen = RATE;
        for (int i = 0; i < length; i++) {
            wave[i] = (short) (127 * (1 - Math.sin(2 * Math.PI
                    * ((i % waveLen) * 1.00 / waveLen))));

//            wave[i] = (short) (amplitude * (1 - Math.sin(TWO_PI
//                    * ((i % waveLen) * 1.00 / waveLen))));

//            wave[i] = (short) (127 * Math.sin(i * 1.00 / waveLen * 2 * Math.PI * frequency));
        }
        return wave;
    }

    public void play() {
        if (audioTrack != null) {
            audioTrack.write(wave, 0, length);
        }
    }

    public void stop() {
        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (audioTrack != null) {
            switch (channel) {
                case LEFT:
                    audioTrack.setStereoVolume(volume / MAX_VOLUME, 0f);
                    break;
                case RIGHT:
                    audioTrack.setStereoVolume(0f, volume / MAX_VOLUME);
                    break;
                case DOUBLE:
                    audioTrack.setStereoVolume(volume / MAX_VOLUME, volume / MAX_VOLUME);
                    break;
            }
        }
    }

    public void setChannel(int channel) {
        this.channel = channel;
        setVolume(volume);
    }
//
//    public static short[] genTone(int frequency, int amplitude) {
//        int sampleRateInHz = RATE; // Hz
////        int totalCount = (duration * sampleRateInHz) / 1000;//除以1000毫秒
//        int totalCount = RATE;//除以1000毫秒
//        double per = (frequency / (double) sampleRateInHz) * 2 * Math.PI;
//        short[] tone = new short[totalCount];
//
//        for (int i = 0; i < totalCount; i++) {
//            tone[i] = (short) (Math.sin(per * i) * amplitude);
//        }
//        return tone;
//    }

    public static short[] genTone(int frequency, int duration) {
        int sampleRateInHz = SonicParams.sampleRateInHz; // Hz
        int totalCount = (duration * sampleRateInHz) / 1000;//除以1000毫秒
        double per = (frequency / (double) sampleRateInHz) * 2 * Math.PI;
        short[] tone = new short[totalCount];

        for (int i = 0; i < totalCount; i++) {
            tone[i] = (short) (Math.sin(per * i) * SonicParams.AMPLITUDE);
        }
        return tone;
    }
}

