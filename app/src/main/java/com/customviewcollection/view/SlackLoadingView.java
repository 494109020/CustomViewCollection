package com.customviewcollection.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.customviewcollection.impl.AnimatorListenerImpl;

import java.util.ArrayList;

/**
 * Created by Magina on 16/9/28.
 * 类功能介绍:loadingview
 * 模仿学习:https://github.com/JeasonWong/SlackLoadingView
 */

public class SlackLoadingView extends View {

    private static final int DURATION = 1500;

    private Paint mLinePaint, mCirclePaint;
    private int mWidth;

    private int mCircleRadius;
    private int mRotate;
    private int mLineLength;
    private int mCircleYOffset;

    private ArrayList<Animator> animators = new ArrayList<>();

    //0:线To圆;1:圆转圈;2:圆To线
    private int step;

    private boolean isLoading;

    public SlackLoadingView(Context context) {
        super(context);
        init();
    }

    public SlackLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlackLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        step = 0;
        mWidth = 400;
        mCircleRadius = 10;
        mRotate = 45;
        mLineLength = mWidth / 2;

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setStrokeWidth(mCircleRadius * 2);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
    }


    private void reset() {
        mCircleYOffset = 0;
        mLineLength = mWidth / 2;
        mRotate = 45;
        step = 0;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(mRotate, mWidth / 2, mWidth / 2);
        switch (step % 3) {
            case 0:
                drawLine(canvas);
                break;
            case 1:
                drawCircle(canvas);
                break;
            case 2:
                drawLine(canvas);
                break;
        }
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < 4; i++) {
            canvas.drawCircle(mWidth / 4 + mCircleYOffset, mWidth / 5 * 2 + mCircleYOffset, mCircleRadius, mCirclePaint);
            canvas.rotate(90, mWidth / 2, mWidth / 2);
        }

    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(mWidth / 4, mWidth / 5 * 2, mWidth / 4 + mLineLength, mWidth / 5 * 2, mLinePaint);
            canvas.rotate(90, mWidth / 2, mWidth / 2);
        }
    }

    private void startLine2Circle() {
        ValueAnimator animatorRotate = ValueAnimator.ofInt(mRotate, mRotate + 360);
        animatorRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (Integer) animation.getAnimatedValue();
            }
        });

        ValueAnimator animatorLength = ValueAnimator.ofInt(mWidth / 2, 0);
        animatorLength.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineLength = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });


        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION);
        set.playTogether(animatorRotate, animatorLength);
        set.setInterpolator(new LinearInterpolator());
        set.addListener(new AnimatorListenerImpl() {
            @Override
            public void onAnimationEnd(Animator animation) {
                step++;
                if (isLoading)
                    startCircleRotate();
            }
        });
        set.start();

        animators.add(set);
    }

    private void startCircleRotate() {
        ValueAnimator animatorRotate = ValueAnimator.ofInt(mRotate, mRotate + 360);
        animatorRotate.setDuration(DURATION);
        animatorRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });

        ValueAnimator animatorY = ValueAnimator.ofInt(0, mWidth / 5, 0);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleYOffset = (Integer) animation.getAnimatedValue();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorRotate, animatorY);
        set.setDuration(DURATION);
        set.setInterpolator(new LinearInterpolator());
        set.addListener(new AnimatorListenerImpl() {
            @Override
            public void onAnimationEnd(Animator animation) {
                step++;
                if (isLoading)
                    startCircle2Line();
            }
        });
        set.start();

        animators.add(set);
    }

    private void startCircle2Line() {
        ValueAnimator animatorRotate = ValueAnimator.ofInt(mRotate, mRotate + 360);
        animatorRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (Integer) animation.getAnimatedValue();
            }
        });

        ValueAnimator animatorLength = ValueAnimator.ofInt(1, mWidth / 2);
        animatorLength.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineLength = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });


        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION);
        set.playTogether(animatorRotate, animatorLength);
        set.setInterpolator(new LinearInterpolator());
        set.addListener(new AnimatorListenerImpl() {
            @Override
            public void onAnimationEnd(Animator animation) {
                step++;
                if (isLoading)
                    startLine2Circle();
            }
        });
        set.start();

        animators.add(set);

    }


    public void start() {
        isLoading = true;
        animators.clear();
        startLine2Circle();
    }


    public void stop() {
        isLoading = false;
        for (Animator animator : animators) {
            animator.cancel();
        }
        reset();
    }
}
