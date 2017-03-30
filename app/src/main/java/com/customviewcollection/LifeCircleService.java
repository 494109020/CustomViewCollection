package com.customviewcollection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Magina on 2/17/17.
 * 类功能介绍:
 */

public class LifeCircleService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("LifeCircleService", "onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("LifeCircleService", "onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LifeCircleService", "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("LifeCircleService", "onBind()");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("LifeCircleService", "onRebind()");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("LifeCircleService", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("LifeCircleService", "onUnbind()");
        return super.onUnbind(intent);
    }

}