package com.customviewcollection.media;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jiang on 2017/1/9.
 */

public class Encoder {
    private static final String TAG = "Encoder";
    public static final int ID_BYTES_LEN = 4;
    public static final int ID_HEX_STR_LEN = 8;
    public static final int ID_HEX_STR_LEN_CK = 10;

    /**
     * @param freq
     * @return 返回字符的值, 48-57,65-70
     */
    public static int freqToCharValue(int freq) {
        int fidx = 0;
        for (int i = 0; i < SonicParams.FREQUENCY.length; i++) {
            if (freq == SonicParams.FREQUENCY[i]) {
                fidx = i;
                break;
            }
        }
        if (fidx >= 0 && fidx <= 9) {
            return (48 + fidx);
        } else if (fidx >= 10 && fidx <= 15) {
            return (65 + fidx - 10);
        }
        return -1;
    }

    /**
     * 0-9,A-F对应频率数组的下标0-15
     *
     * @param character
     * @return
     */
    public static int characterToIdx(int character) {
        int idx = -1;
        if (character >= 48 && character <= 57) {//0-9
            idx = (character - 48);
        } else if (character >= 65 && character <= 70) {//A-F
            idx = (character - 65) + 10;
        }
        return idx;
    }

    /**
     * 0,1,2---9
     * 48, 49-- 57
     * A ... F
     * 65 ... 70
     *
     * @return
     */
    public static int getfreqByChar(int character) {
        int idx = characterToIdx(character);

        return (idx != -1) ? (SonicParams.FREQUENCY[idx]) : 0;
    }

    public static int getfreqByIdx(int idx) {
        return (idx != -1) ? (SonicParams.FREQUENCY[idx]) : 0;
    }

    public static byte[] getChecksum(byte[] idbytes) {
        byte[] check = new byte[1];
        check[0] = 0;
        if (idbytes.length < ID_BYTES_LEN) {
            Log.d(TAG, "id bytes length error " + idbytes.length);
            return check;
        }
        for (int i = 0; i < ID_BYTES_LEN; i++) {
            check[0] ^= idbytes[i];
        }
        check[0] &= 0x77;
        return check;
    }

    public static String addcheck(String idstr) {
        Log.d(TAG, "idstr: " + idstr);
        if (idstr.length() != ID_HEX_STR_LEN) {
            return "";
        }

        byte[] idbytes = hexStringToByteArray(idstr);
        byte[] check = getChecksum(idbytes);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(idstr);
        stringBuffer.append(bytesToHex(check));
        String idck = stringBuffer.toString();
        Log.d(TAG, "idck: " + idck);
        return idck;
    }

    /**
     * @param idstr 5个字节的10个HEX字符
     * @return
     */
    public static ArrayList messageToFreq(String idstr) {
        ArrayList arrayListFreq = new ArrayList();
        idstr = idstr.toUpperCase();//a-f转为大写

        if (idstr.length() != 10) {
//            return arrayListFreq;
        }
        arrayListFreq.add(SonicParams.START_FREQ);
        int last_freq = -1;
        for (int i = 0; i < idstr.length(); i++) {
            int value = Integer.valueOf(idstr.charAt(i));
            int freq = getfreqByChar(value);
            Log.d(TAG, "value[" + i + "]: " + value);
            Log.d(TAG, "freq: " + freq);
            if (freq == last_freq) {
                arrayListFreq.add(SonicParams.INT_FREQ);
            }
            arrayListFreq.add(freq);
            last_freq = freq;
        }
        arrayListFreq.add(SonicParams.END_FREQ);

        for (int i = 0; i < arrayListFreq.size(); i++) {
            Log.d(TAG, "msgfreq[" + i + "]: " + arrayListFreq.get(i));
        }
        return arrayListFreq;
    }


    /**
     * @param frequency
     * @param duration  持续多少毫秒
     * @return
     */
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


    public static String bytesToHex(byte[] in) {
        StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
