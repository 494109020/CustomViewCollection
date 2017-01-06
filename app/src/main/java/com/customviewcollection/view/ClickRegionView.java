package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Magina on 1/6/17.
 * 类功能介绍:
 */

public class ClickRegionView extends View {

    private static final String TAG = ClickRegionView.class.getSimpleName();
    private Path mPath;
    private Paint mPaint;
    private Region mClickRegion;
    private int rawX;
    private int rawY;
    private Matrix mMatrix;
    private float[] dst;
    private float[] dstXY;
    private float[] src;
    private int getX;
    private int getY;

    public ClickRegionView(Context context) {
        super(context);
    }

    public ClickRegionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0xff000000);
        mPaint.setTextSize(40);

        mPath = new Path();
        mPath.addCircle(300, 300, 150, Path.Direction.CW);
        RectF rectF = new RectF();
        mPath.computeBounds(rectF, true);

        mClickRegion = new Region();
        mClickRegion.setPath(mPath, new Region((int) rectF.left - 10, (int) rectF.top - 10, (int) rectF.right + 10, (int) rectF.bottom + 10));

        mMatrix = new Matrix();

        dst = new float[2];
        src = new float[2];
        dstXY = new float[2];
    }

    public ClickRegionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0f, 450f, 1080f, 450f, mPaint);// x轴
        canvas.drawLine(450f, 0f, 450f, 1920f, mPaint);// y轴

        mPaint.setColor(0xff000000);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(rawX, rawY, 10, mPaint);
        canvas.drawText("raw", rawX, rawY, mPaint);
        canvas.drawCircle(dst[0], dst[1], 10, mPaint);
        canvas.drawText("dst", dst[0], dst[1], mPaint);
        canvas.drawCircle(getX, getY, 10, mPaint);
        canvas.drawText("get", getX, getY, mPaint);
        canvas.drawCircle(dstXY[0], dstXY[1], 10, mPaint);
        canvas.drawText("dstXY", dstXY[0], dstXY[1], mPaint);

//        canvas.translate(480, 520);
        // 经过测试发现：开启硬件加速和未开启硬件加速的情况下，得到的值是不同的
        // 开启硬件加速的情况下，获得的是当前view坐标的Matrix
        // 未开启硬件加速的情况下，获得的是屏幕物理坐标的Matrix
        Matrix matrix = canvas.getMatrix();

        Log.e(TAG, "canvas的Matrix=" + matrix.toString());

        mMatrix.reset();
        matrix.invert(mMatrix);
        Log.e(TAG, "逆矩阵的Matrix=" + mMatrix);

        mPaint.setColor(0xffff0000);
        canvas.drawPath(mPath, mPaint);
//        canvas.drawCircle(rawX, rawY, 10, mPaint);
//        canvas.drawText("raw", rawX, rawY, mPaint);
//        canvas.drawCircle(dst[0], dst[1], 10, mPaint);
//        canvas.drawText("dst", dst[0], dst[1], mPaint);
//        canvas.drawCircle(getX, getY, 10, mPaint);
//        canvas.drawText("get", getX, getY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        rawX = (int) event.getRawX();
        rawY = (int) event.getRawY();
        Log.e(TAG, "rawX=" + rawX + ";rawY=" + rawY + ";mClickRegion.contains(rawX, rawY)=" + mClickRegion.contains(rawX, rawY));
        src[0] = rawX;
        src[1] = rawY;
        mMatrix.mapPoints(dst, src);
        Log.e(TAG, "dst[0]=" + dst[0] + ";dst[1]=" + dst[1] + ";mClickRegion.contains(dst[0], dst[1])=" + mClickRegion.contains((int) dst[0], (int) dst[1]));

        // 注意，要使用下面这两个值。而不能使用rawX和rawY的值。
        getX = (int) event.getX();
        getY = (int) event.getY();
        src[0] = getX;
        src[1] = getY;
        mMatrix.mapPoints(dstXY, src);
        mClickRegion.contains(getX, getY);
        Log.e(TAG, "dstXY[0]=" + dstXY[0] + ";dstXY[1]=" + dstXY[1] + ";mClickRegion.contains(dstXY[0], dstXY[1])=" + mClickRegion.contains((int) dstXY[0], (int) dstXY[1]));
        Log.e(TAG, "X=" + getX + ";Y=" + getY + ";mClickRegion.contains(X, Y)=" + mClickRegion.contains(getX, getY));

        // 经过测试发现：开启硬件加速和未开启硬件加速的情况下，通过Canvas得到的Matrix的值是不同的
        // 开启硬件加速的情况下，获得的是当前view坐标的Matrix
        // 未开启硬件加速的情况下，获得的是屏幕物理坐标的Matrix
        // 所以，在转换坐标系的时候，如果开启了硬件加速，就应该用event.getX()/event.getY()来进行转换
        // 未开启硬件加速的时候，就应该用event.getRawX()/event.getRawY()来进行坐标转换

        invalidate();

        return super.onTouchEvent(event);
    }
}
