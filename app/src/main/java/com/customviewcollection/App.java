package com.customviewcollection;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Magina on 16/10/20.
 * 类功能介绍:
 */

public class App extends Application {


    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        //这里不用RefWatcher貌似也是可以的
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getWatcher(Context context) {
        App app = (App) context.getApplicationContext();
        return app.mRefWatcher;
    }

}
