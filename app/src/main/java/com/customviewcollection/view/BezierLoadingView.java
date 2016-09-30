package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Magina on 16/9/29.
 * 类功能介绍:贝赛尔动画
 * 模仿学习:https://github.com/JeasonWong/BezierLoadingView
 */

public class BezierLoadingView extends View {

    private Paint mPaint;

    private Path mPath = new Path();

    private int mWidth, mHeight;

    private int mSmallRadius;//外面一圈小圆的半径
    private int mBigRadius;//大圆整体的半径(小圆圆心到大圆圆心的距离)

    private int mCircleCount;

    private int mCurrentAngle;

    private ArrayList<PointF> mPoints = new ArrayList<>();
    private int mRadian;//两点间的角度

    private boolean isEvenCyclic;//是否是偶数圈

    public BezierLoadingView(Context context) {
        super(context);
        init();
    }

    public BezierLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mWidth = 400;
        mHeight = 400;
        mBigRadius = 150;
        mSmallRadius = 20;
        mCircleCount = 10;
        mRadian = 360 / mCircleCount;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        createPoints();
    }

    //外面一圈小圆的圆心
    private void createPoints() {
        for (int i = 0; i < mCircleCount; i++) {
            mPoints.add(new PointF(getCircleX(360 / mCircleCount * i, mBigRadius), getCircleY(360 / mCircleCount * i, mBigRadius)));
        }
    }

    private void ads() {
        mCurrentAngle++;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawBezier(canvas);

    }

    private void drawBezier(Canvas canvas) {
        if (mCurrentAngle % mRadian == 0) return;
        mPath.reset();

        int currentIndex = mCurrentAngle / mRadian;

        float currentX = getCircleX(mCurrentAngle, mBigRadius);
        float currentY = getCircleY(mCurrentAngle, mBigRadius);

        int index;
        if (isEvenCyclic) {//从有到无
            index = currentIndex + 1;
            if (currentIndex + 1 == mCircleCount)
                return;
        } else {//从无到有
            index = currentIndex;
        }

        PointF pointF1 = new PointF(getCircleX(mCurrentAngle, mBigRadius - mSmallRadius), getCircleY(mCurrentAngle, mBigRadius - mSmallRadius));
        PointF pointF2 = new PointF(getCircleX(mCurrentAngle, mBigRadius + mSmallRadius), getCircleY(mCurrentAngle, mBigRadius + mSmallRadius));

        PointF pointF3 = new PointF(getCircleX(mRadian * index, mBigRadius - mSmallRadius), getCircleY(mRadian * index, mBigRadius - mSmallRadius));
        PointF pointF4 = new PointF(getCircleX(mRadian * index, mBigRadius + mSmallRadius), getCircleY(mRadian * index, mBigRadius + mSmallRadius));

//        PointF pointF5 = new PointF(getCircleX((mCurrentAngle + mRadian * index) / 2, mBigRadius), getCircleY((mCurrentAngle + mRadian * index) / 2, mBigRadius));

        //这里是以对方的圆心作为控制点的。但是这样出来效果不够好。
        mPaint.setColor(Color.BLACK);
        if (isEvenCyclic) {//从有到无
            mPath.moveTo(pointF1.x, pointF1.y);
            mPath.quadTo(mPoints.get(index).x, mPoints.get(index).y, pointF2.x, pointF2.y);
            mPath.lineTo(pointF1.x, pointF1.y);

            mPath.moveTo(pointF4.x, pointF4.y);
            mPath.quadTo(currentX, currentY, pointF3.x, pointF3.y);
            mPath.lineTo(pointF4.x, pointF4.y);
        } else {//从无到有
            mPath.moveTo(pointF1.x, pointF1.y);
            mPath.quadTo(mPoints.get(index).x, mPoints.get(index).y, pointF2.x, pointF2.y);
            mPath.lineTo(pointF1.x, pointF1.y);

            mPath.moveTo(pointF4.x, pointF4.y);
            mPath.quadTo(currentX, currentY, pointF3.x, pointF3.y);
            mPath.lineTo(pointF4.x, pointF4.y);
        }

//        mPath.moveTo(pointF1.x, pointF1.y);
//        mPath.quadTo((currentX + mPoints.get(index).x) / 2, (currentY + mPoints.get(index).y) / 2, pointF2.x, pointF2.y);
//        mPath.lineTo(pointF1.x, pointF1.y);
//
//        mPath.moveTo(pointF4.x, pointF4.y);
//        mPath.quadTo((currentX + mPoints.get(index).x) / 2, (currentY + mPoints.get(index).y) / 2, pointF3.x, pointF3.y);
//        mPath.lineTo(pointF4.x, pointF4.y);

        mPath.close();
        canvas.drawPath(mPath, mPaint);

//        canvas.drawPoint(pointF1.x, pointF1.y, mPaint);
//        canvas.drawText("1", pointF1.x, pointF1.y, mPaint);
//        canvas.drawPoint(pointF2.x, pointF2.y, mPaint);
//        canvas.drawText("2", pointF2.x, pointF2.y, mPaint);
//        canvas.drawPoint(pointF3.x, pointF3.y, mPaint);
//        canvas.drawText("3", pointF3.x, pointF3.y, mPaint);
//        canvas.drawPoint(pointF4.x, pointF4.y, mPaint);
//        canvas.drawText("4", pointF4.x, pointF4.y, mPaint);
//        canvas.drawPoint(pointF5.x, pointF5.y, mPaint);
//        canvas.drawText("5", pointF5.x, pointF5.y, mPaint);

    }


    /**
     * 获取theta值
     *
     * @param pointCenterLeft
     * @param pointCenterRight
     * @return
     */
    private double getTheta(PointF pointCenterLeft, PointF pointCenterRight) {
        double theta = Math.atan((pointCenterRight.y - pointCenterLeft.y) / (pointCenterRight.x - pointCenterLeft.x));
        return theta;
    }


    private void drawCircle(Canvas canvas) {
        mPaint.setColor(0xff000000);
        for (int i = 0; i < mCircleCount; i++) {
            if (isEvenCyclic) {//从有到无
//                if (i > getCurrentIndex() + 1) {
//                    canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
//                } else if (i == getCurrentIndex() + 1) {
//                    if (mCurrentAngle % mRadian == 0) {//正好到原有点的位置
//                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
//                    } else {//在两点中间的时候,index+1位置上的圆要变小一些
//                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
//                    }
//                } else if (i == getCurrentIndex()) {
//                    if (mCurrentAngle % mRadian == 0) {//正好到原有点的位置
//                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
//                    } else {//超过原有位置,则原有位置不画圆。在当前角度位置画圆。
//                        canvas.drawCircle(getCircleX(mCurrentAngle, mBigRadius), getCircleY(mCurrentAngle, mBigRadius), mSmallRadius, mPaint);
//                    }
//                }


                //第二种方式
                if (mCurrentAngle % mRadian == 0) {//正好到原有点的位置
                    if (i > getCurrentIndex()) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
                    } else if (i == getCurrentIndex()) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius + mSmallRadius / mCircleCount * i, mPaint);
                    }
                } else {//在两点中间的时候
                    if (i > getCurrentIndex() + 1) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
                    } else if (i == getCurrentIndex() + 1) {//画小一点的圆
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
                    } else if (i == getCurrentIndex()) {//超过原有位置,则原有位置不画圆。在当前角度位置画圆。
                        canvas.drawCircle(getCircleX(mCurrentAngle, mBigRadius), getCircleY(mCurrentAngle, mBigRadius), mSmallRadius + mSmallRadius / mCircleCount * i, mPaint);
                    }
                }


            } else {//从无到有
                if (mCurrentAngle % mRadian == 0) {//正好到原有点的位置
                    if (i < getCurrentIndex()) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
                    } else if (i == getCurrentIndex()) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, 2 * mSmallRadius - mSmallRadius / mCircleCount * i, mPaint);
                    }
                } else {//在两点中间
                    if (i < getCurrentIndex()) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
                    } else if (i == getCurrentIndex()) {//当前位置的圆向外移动,所在位置画一个小圆
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mSmallRadius, mPaint);
                        canvas.drawCircle(getCircleX(mCurrentAngle, mBigRadius), getCircleY(mCurrentAngle, mBigRadius), 2 * mSmallRadius - mSmallRadius / mCircleCount * i, mPaint);
                    }
                }
            }
        }
    }


    private int getCurrentIndex() {
        return mCurrentAngle / mRadian;
    }

    /**
     * java中Math.cos(double)中的角度是以弧度来计算的。
     * 所以,穿进去的角度要转换为弧度才能正确计算
     */
    private float getCircleX(int angle, int radius) {
        return mWidth / 2 + (float) Math.cos(angle * Math.PI / 180) * radius;
    }

    /**
     * java中Math.sin(double)中的角度是以弧度来计算的。
     * 所以,穿进去的角度要转换为弧度才能正确计算
     * Math.PI = 180度
     * 1度= Math.PI/180 弧度
     */
    private float getCircleY(int angle, int radius) {
        return mHeight / 2 + (float) Math.sin(angle * Math.PI / 180) * radius;
    }


    /**
     * 启动定时器
     */
    public void start() {
        Observable.interval(50, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mCurrentAngle++;
                        if (mCurrentAngle == 360) {
                            mCurrentAngle = 0;
                            isEvenCyclic = !isEvenCyclic;
                        }
                        postInvalidate();
                    }
                });
    }

}
