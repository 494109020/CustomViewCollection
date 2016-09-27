package com.customviewcollection.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Magina on 16/9/27.
 * 类功能介绍:
 * 学习模仿的https://github.com/JeasonWong/ElemeShoppingView
 */

public class ShoppingView extends View {

    private static final int STATE_NONE = 0;
    private static final int STATE_CHANGE = 1;
    private static final int STATE_CHANGE_DONE = 2;

    private Paint mMinusPaint;
    private Paint mAddPaint;
    private Paint mNumPaint;
    private Paint mBgPaint;

    private int mState = STATE_NONE;

    private int textSize;
    private int strokeWidth;

    private int mNum;
    private Rect mTextBounds;

    private int mWidth;
    private int mCurrentWidth;
    private int mHeight;

    public ShoppingView(Context context) {
        super(context);
        init();
    }

    public ShoppingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShoppingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mNum = 10;
        textSize = 65;
        strokeWidth = 10;

        mHeight = textSize * 2;
        mWidth = mHeight * 5;


        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.YELLOW);
        mBgPaint.setStyle(Paint.Style.FILL);

        mMinusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinusPaint.setColor(Color.BLUE);
        mMinusPaint.setStyle(Paint.Style.STROKE);
        mMinusPaint.setStrokeWidth(strokeWidth);

        mAddPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAddPaint.setColor(Color.BLUE);
        mAddPaint.setStyle(Paint.Style.STROKE);
        mAddPaint.setStrokeWidth(strokeWidth);

        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setColor(Color.BLACK);
        mNumPaint.setTextSize(textSize);
        mNumPaint.setTextAlign(Paint.Align.CENTER);

        mTextBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == STATE_CHANGE) {
            drawBgMove(canvas);
        } else if (mState == STATE_CHANGE_DONE) {
            drawBgMove(canvas);
            drawShowText(canvas);
        } else if (mState == STATE_NONE) {
            drawMinus(canvas);
            drawNumber(canvas);
            drawAdd(canvas);
        }
    }

    private void drawShowText(Canvas canvas) {
        String value = "改变完成";
        mNumPaint.getTextBounds(value, 0, value.length(), mTextBounds);
        //这样画出来的文字其实并不是垂直居中的。
        //原因参考:http://blog.csdn.net/aigestudio/article/details/41447349
        canvas.drawText(value, mWidth / 2, mHeight / 2 + mTextBounds.height() / 2, mNumPaint);
    }

    private void drawBgMove(Canvas canvas) {
        /**
         * 画圆弧注意事项:
         * 1.首先要知道,canvas的坐标系和平常数学中的有点区别。主要是y轴。在开发中,y轴向下为正。
         * 2.x轴的正方向依然是角度为0,y轴正方向是90度.
         * 3.sweepAngle为正,则扫描方向是顺时针。反之,为负则逆时针。
         */
        canvas.drawArc(new RectF(mWidth - mHeight, 0, mWidth, mHeight), -90, 180, true, mBgPaint);
        canvas.drawRect(new Rect(mWidth - mHeight / 2 - mCurrentWidth, 0, mWidth - mHeight / 2, mHeight), mBgPaint);
        canvas.drawArc(new RectF(mWidth - mHeight - mCurrentWidth, 0, mWidth - mCurrentWidth, mHeight), 90, 180,
                false, mBgPaint);
    }

    //画加号
    private void drawAdd(Canvas canvas) {
        //半径为(mHeight - strokeWidth) / 2。其中减去strokeWidth是为了防止边缘的角化
        canvas.drawCircle(mWidth - mHeight / 2, mHeight / 2, (mHeight - strokeWidth) / 2, mAddPaint);
        canvas.drawLine(mWidth - mHeight, mHeight / 2, mWidth, mHeight / 2, mAddPaint);
        canvas.drawLine(mWidth - mHeight / 2, 0, mWidth - mHeight / 2, mHeight, mAddPaint);
    }

    //画数字
    private void drawNumber(Canvas canvas) {
        String value = String.valueOf(mNum);
        mNumPaint.getTextBounds(value, 0, value.length(), mTextBounds);
        canvas.drawText(value, mWidth / 2, mHeight / 2 + mTextBounds.height() / 2, mNumPaint);
    }

    //画减号
    private void drawMinus(Canvas canvas) {
        canvas.drawCircle(mHeight / 2, mHeight / 2, (mHeight - strokeWidth) / 2, mMinusPaint);
        canvas.drawLine(0, mHeight / 2, mHeight, mHeight / 2, mMinusPaint);
    }

    public void startRoundRectChange() {

        mState = STATE_CHANGE;

        ValueAnimator animator = ValueAnimator.ofInt(0, mWidth - mHeight);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentWidth = (Integer) animation.getAnimatedValue();
                if (mCurrentWidth == mWidth - mHeight) {
                    mState = STATE_CHANGE_DONE;
                }
                invalidate();
            }
        });
        animator.start();
    }
}
