package com.customviewcollection.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.customviewcollection.R;

/**
 * Created by Magina on 11/21/16.
 * 类功能介绍: 桌面小组件
 * 它的实现方式其实和广播接收者有点像。记得要在AndroidManifest中注册
 */

public class DesktopWidget extends AppWidgetProvider {

    private static final String TAG = DesktopWidget.class.getName();

    public static final String CLICK_ACTION = "com.customviewcollection.action.CLICK";

    /**
     * 小组件第一次添加到桌面的时候调用。重复添加也只是第一次才调用
     *
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i(TAG, "onEnabled()");
    }

    /**
     * 其实，其余的回调方法大部分都是通过该方法进行回调的。具体可查看父类源码
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive()");
        String action = intent.getAction();
        // 这里判断是否是自己要处理的action。
        if (CLICK_ACTION.equals(action)) {
            Log.i(TAG, "我的桌面小组件被单机了");
        }

    }

    /**
     * 每次桌面小组件更新都会调用的方法。系统回调。
     * 更新时间由res/xml 下的资源文件指定。该资源文件会在AndroidManifest中指定
     * 注意：SDK1.5之后此android:updatePeriodMillis就失效了，要自己创建service更新
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(TAG, "onUpdate()");

        if (appWidgetIds != null && appWidgetIds.length > 0) {
            for (int appWidgetId : appWidgetIds) {
                // 这么搞貌似不行呢。最好还是直接利用xml中声明的id来做处理吧。
                onWidgetUpdate(context, appWidgetManager, appWidgetId);
            }
        }
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG, "onWidgetUpdate()");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_desktop);

        // 桌面小组件单机事件
        Intent clickIntent = new Intent();
        clickIntent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.tv, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * 桌面小组件每次删除都会调用
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i(TAG, "onDeleted()");
    }

    /**
     * ＃最后一个＃被删除的时候会调用
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i(TAG, "onDisabled()");
    }
}
