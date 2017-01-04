package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.customviewcollection.R;

/**
 * Created by Magina on 1/3/17.
 * 类功能介绍:
 * 关于matrix的matrix.setPolyToPoly()方法的学习。
 * 关于matrix的matrix.setRectToRect()方法的学习。
 * 记得关闭硬件加速
 */

public class MatrixPolyView extends View {

    private static final String TAG = MatrixPolyView.class.getSimpleName();
    private int testPoint = 0;
    private int triggerRadius = 180;    // 触发半径为180px

    private Bitmap mBitmap;             // 要绘制的图片
    private Matrix mPolyMatrix;         // 测试setPolyToPoly用的Matrix

    private float[] src = new float[8];
    private float[] dst = new float[8];

    private int mCurrentPoint = -1;

    private Paint pointPaint;
    private RectF mBitmapRect;
    private RectF mScreenRect;
    private Matrix mRectToRect;
    private boolean isPoly;

    public MatrixPolyView(Context context) {
        this(context, null);
    }

    public MatrixPolyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixPolyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmapAndMatrix();
    }

    private void initBitmapAndMatrix() {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.sishen);

        float[] temp = {0, 0,                                    // 左上
                mBitmap.getWidth(), 0,                          // 右上
                mBitmap.getWidth(), mBitmap.getHeight(),        // 右下
                0, mBitmap.getHeight()};                        // 左下
        src = temp.clone();
        dst = temp.clone();

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(50);
        pointPaint.setColor(0xffd19165);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

        mPolyMatrix = new Matrix();
        mPolyMatrix.setPolyToPoly(src, 0, src, 0, 4);


        mBitmapRect = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mRectToRect = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenRect = new RectF(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 这里要用屏幕的坐标
                float tempX = event.getX();
                float tempY = event.getY();

                // 根据触控位置改变dst
                for (int i = 0; i < testPoint * 2; i += 2) {
                    if (Math.abs(tempX - dst[i]) <= triggerRadius && Math.abs(tempY - dst[i + 1]) <= triggerRadius && (mCurrentPoint == -1 || mCurrentPoint == i)) {
                        dst[i] = tempX;
                        dst[i + 1] = tempY;
                        mCurrentPoint = i;
                        break;  // 防止两个点的位置重合
                    }
                }

                resetPolyMatrix(testPoint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCurrentPoint = -1;
                break;
        }

        return true;
    }

    public void resetPolyMatrix(int pointCount) {
        mPolyMatrix.reset();
        // 核心要点
        mPolyMatrix.setPolyToPoly(src, 0, dst, 0, pointCount);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isPoly) {
            // 根据Matrix绘制一个变换后的图片
            canvas.drawBitmap(mBitmap, mPolyMatrix, null);

            float[] dst = new float[8];
            mPolyMatrix.mapPoints(dst, src);

            // 绘制触控点
            for (int i = 0; i < testPoint * 2; i += 2) {
                canvas.drawPoint(dst[i], dst[i + 1], pointPaint);
            }
        } else {
            canvas.drawBitmap(mBitmap, mRectToRect, null);
        }
    }

    public void setTestPoint(int testPoint) {
        isPoly = true;
        this.testPoint = testPoint > 4 || testPoint < 0 ? 4 : testPoint;
        dst = src.clone();
        resetPolyMatrix(this.testPoint);
        invalidate();
    }

    public void setRect(Matrix.ScaleToFit type) {
        isPoly = false;
        mRectToRect.reset();
        mRectToRect.setRectToRect(mBitmapRect, mScreenRect, type);
        invalidate();
    }
}
