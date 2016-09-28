package com.customviewcollection.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Magina on 16/9/27.
 * 类功能介绍:
 * 学习模仿的https://github.com/JeasonWong/ElemeShoppingView
 */

public class ShoppingView extends View {

    private static final int STATE_NUMBER = 0;//显示数字的状态
    private static final int STATE_MOVE = 1;//圆角矩形变化的状态
    private static final int STATE_TEXT = 2;//原始,没有数量的状态

    private Paint mMinusPaint;//减号的画笔
    private Paint mAddPaint;//加号的画笔
    private Paint mNumPaint;//数字的画笔
    private Paint mTextPaint;//数字的画笔
    private Paint mBgPaint;//背景的画笔

    private int mState = STATE_NUMBER;

    private int textSize;
    private int strokeWidth;

    private int mNum;
    private Rect mTextBounds;

    private int mWidth;//控件的宽
    private int mCurrentWidth;//圆角矩形中矩形的当前宽度
    private int mHeight;//控件的高
    private int mAngle;//旋转的角度
    private int mMinusXPosition;//减号圆心的x位置

    private PointF mPoint;//点击点位置
    private PointF mCenterPoint;//圆心
    private OnNumberChangeListener mListener;

    //是否要显示文本。没有加减号的模式
    private boolean isShowText = true;
    private static final int DURATION = 1000;

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

        mNum = 1;
        textSize = 50;
        strokeWidth = 10;

        mHeight = textSize * 2;
        mWidth = mHeight * 5;
        //减号圆心默认在控件最右侧。
        mMinusXPosition = mHeight / 2;


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

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTextBounds = new Rect();
        mPoint = new PointF();
        mCenterPoint = new PointF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == STATE_MOVE) {
            drawBgMove(canvas);
        } else if (mState == STATE_TEXT) {
            drawBgMove(canvas);
            drawShowText(canvas);
        } else if (mState == STATE_NUMBER) {
            drawMinus(canvas);
            drawNumber(canvas);
            drawAdd(canvas);
        }
//        else if (mState == STATE_START_ROTATE) {
//            startRotateAnimator();
//        } else if (mState == STATE_START_MOVE) {
//            startRoundRectChange();
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPoint.set(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mState == STATE_NUMBER) {
                    //减号
                    mCenterPoint.set(mHeight / 2, mHeight / 2);
                    if (isPointInCircle(mPoint, mCenterPoint)) {
                        if (mNum > 1) {
                            mNum--;
                            invalidate();
                            if (mListener != null)
                                mListener.minus(mNum);
                        } else {
                            isShowText = true;
                            startRotateAnimator();
                            if (mListener != null)
                                mListener.minus(0);
                        }
                    }
                    //加号
                    mCenterPoint.set(mWidth - mHeight / 2, mHeight / 2);
                    if (isPointInCircle(mPoint, mCenterPoint)) {
                        mNum++;
                        invalidate();
                        if (mListener != null) {
                            mListener.add(mNum);
                        }
                    }
                } else if (mState == STATE_TEXT) {
                    isShowText = false;
                    startRoundRectChange();
                }
                break;
        }

        return super.onTouchEvent(event);

    }

    private void drawShowText(Canvas canvas) {
        String value = "ap爱哥1ξτβбпшㄎㄊ";
        mNumPaint.getTextBounds(value, 0, value.length(), mTextBounds);
        //绘制文字参考:http://blog.csdn.net/aigestudio/article/details/41447349

        //这样处理后视觉效果看起来像是在中央。
        //个人发现:正常画的时候,y下方也会有一部分文字,这部分应该就是descent。
        //假设测量出来的高度为H,去掉这部分的高度(descent),剩余的就是正常的文字高度h了。
        //这样的话,要使文字绘制在中央,则需要y值为:控件的中线位置再往下移动h/2
        canvas.drawText(value, mWidth / 2, mHeight / 2 + (mTextBounds.height() - Math.abs(mNumPaint.descent())) / 2, mNumPaint);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mNumPaint);
    }

    private void drawBgMove(Canvas canvas) {
        /**
         * 画圆弧注意事项:
         * 1.首先要知道,canvas的坐标系和平常数学中的有点区别。主要是y轴。在开发中,y轴向下为正。
         * 2.x轴的正方向依然是角度为0,y轴正方向是90度.
         * 3.sweepAngle为正,则扫描方向是顺时针。反之,为负则逆时针。
         */
        //右侧半圆
        canvas.drawArc(new RectF(mWidth - mHeight, 0, mWidth, mHeight), -90, 180, true, mBgPaint);
        //中间矩形
        canvas.drawRect(new Rect(mWidth - mHeight / 2 - mCurrentWidth, 0, mWidth - mHeight / 2, mHeight), mBgPaint);
        //左侧半圆
        canvas.drawArc(new RectF(mWidth - mHeight - mCurrentWidth, 0, mWidth - mCurrentWidth, mHeight), 90, 180,
                false, mBgPaint);
    }

    //画加号
    private void drawAdd(Canvas canvas) {
        //半径为(mHeight - strokeWidth) / 2。其中减去strokeWidth是为了防止边缘的角化,减号也是同理
        canvas.drawCircle(mWidth - mHeight / 2, mHeight / 2, (mHeight - strokeWidth) / 2, mAddPaint);
        canvas.drawLine(mWidth - mHeight, mHeight / 2, mWidth, mHeight / 2, mAddPaint);
        canvas.drawLine(mWidth - mHeight / 2, 0, mWidth - mHeight / 2, mHeight, mAddPaint);
    }

    //画数字,带旋转的。
    private void drawNumber(Canvas canvas) {
        canvas.save();
        //旋转中心位置应该为加减号两个圆心的终点位置
        canvas.rotate(mAngle, (mMinusXPosition + mWidth - mHeight / 2) / 2, mHeight / 2);
        String value = String.valueOf(mNum);
        mNumPaint.getTextBounds(value, 0, value.length(), mTextBounds);
        canvas.drawText(value, (mMinusXPosition + mWidth - mHeight / 2) / 2, mHeight / 2 + mTextBounds.height() / 2, mNumPaint);
        canvas.restore();
    }

    //画减号
    private void drawMinus(Canvas canvas) {
        canvas.save();
        //旋转中心位置应该为减号的圆心位置
        canvas.rotate(mAngle, mMinusXPosition, mHeight / 2);
        canvas.drawCircle(mMinusXPosition, mHeight / 2, (mHeight - strokeWidth) / 2, mMinusPaint);
        canvas.drawLine(mMinusXPosition - mHeight / 2, mHeight / 2, mMinusXPosition + mHeight / 2, mHeight / 2, mMinusPaint);
        canvas.restore();
    }

    /**
     * 开始圆角矩形的变动
     */
    public void startRoundRectChange() {

        mState = STATE_MOVE;

        ValueAnimator animator;
        if (isShowText)
            animator = ValueAnimator.ofInt(0, mWidth - mHeight);
        else
            animator = ValueAnimator.ofInt(mWidth - mHeight, 0);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentWidth = (Integer) animation.getAnimatedValue();
                if (isShowText && mCurrentWidth == mWidth - mHeight) {
                    mState = STATE_TEXT;
                } else if (!isShowText && mCurrentWidth == 0) {
                    //如果是要展示数字,则此时开启旋转的绘画
                    startRotateAnimator();
                }
                invalidate();
            }
        });
        animator.start();
    }


    private void startRotateAnimator() {

        mState = STATE_NUMBER;

        ValueAnimator angleAnimator;
        ValueAnimator xPositionAnimator;
        if (isShowText) {
            angleAnimator = ValueAnimator.ofInt(0, 360);
            xPositionAnimator = ValueAnimator.ofInt(mHeight / 2, mWidth - mHeight / 2);
        } else {
            angleAnimator = ValueAnimator.ofInt(360, 0);
            xPositionAnimator = ValueAnimator.ofInt(mWidth - mHeight / 2, mHeight / 2);
        }

        angleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAngle = (Integer) animation.getAnimatedValue();
                if (isShowText && mAngle == 360) {
                    startRoundRectChange();
                }
                invalidate();
            }
        });

        xPositionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMinusXPosition = (Integer) animation.getAnimatedValue();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.playTogether(angleAnimator, xPositionAnimator);
        animatorSet.start();
    }


    /**
     * 判断一个点是否在圆内
     * 圆的公式为x^2+y^2 = R^2;
     *
     * @param point       判断点
     * @param centerPoint 圆心
     * @return 是否在
     */
    private boolean isPointInCircle(PointF point, PointF centerPoint) {
        return Math.pow((point.x - centerPoint.x), 2) + Math.pow((point.y - centerPoint.y), 2) <= Math.pow(mHeight / 2, 2);
    }


    public interface OnNumberChangeListener {
        void add(int num);

        void minus(int num);
    }

    public void setOnNumberChangeListener(OnNumberChangeListener listener) {
        mListener = listener;
    }
}
