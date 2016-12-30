package com.customviewcollection.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import static com.customviewcollection.view.SearchView.State.SEARCHING;

/**
 * Created by Magina on 12/30/16.
 * 类功能介绍:
 */

public class SearchView extends View {

    private Path mOutCircle;
    private Path mSearch;
    private Path mDst;

    private Paint mDefPaint;
    private int mWidth, mHeight;
    private PathMeasure mPathMeasure;
    private State mState = State.NONE;
    private float mCurrentValue;
    private ValueAnimator mSearchStart;
    private ValueAnimator mCircleStart;
    private ValueAnimator mSearchEnd;

    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPath();
        initListener();
    }

    private void initListener() {
        mSearchStart = ValueAnimator.ofFloat(0, 1).setDuration(2000);
        mCircleStart = ValueAnimator.ofFloat(0, 1).setDuration(2000);
        mSearchEnd = ValueAnimator.ofFloat(1, 0).setDuration(2000);

        ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        };

        mSearchStart.addUpdateListener(listener);
        mCircleStart.addUpdateListener(listener);
        mSearchEnd.addUpdateListener(listener);


        ValueAnimator.AnimatorListener animatorListener = new ValueAnimator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mState == State.START) {
                    mState = SEARCHING;
                    mCircleStart.start();
                } else if (mState == SEARCHING) {
                    mState = State.DONE;
                    mSearchEnd.start();
                } else if (mState == State.DONE) {
                    mState = State.START;
                    mSearchStart.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        };
        mSearchStart.addListener(animatorListener);
        mCircleStart.addListener(animatorListener);
        mSearchEnd.addListener(animatorListener);
    }

    private void initPath() {
        mDefPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mDefPaint.setStyle(Paint.Style.STROKE);
        mDefPaint.setColor(0xff000000);
        mDefPaint.setStrokeWidth(2);

        mSearch = new Path();
        // 注意这里不要360。
//        mSearch.addArc(-50, -50, 50, 50, 45, 359.9f);  这个是api21才添加的方法
        RectF rectF1 = new RectF(-40, -40, 40, 40);
        mSearch.addArc(rectF1, 45, 359.9f);

        mOutCircle = new Path();
        RectF rectF2 = new RectF(-100, -100, 100, 100);
        mOutCircle.addArc(rectF2, 45, 359.9f);

        mPathMeasure = new PathMeasure();

        float[] pos = new float[2];
        mPathMeasure.setPath(mOutCircle, false);
        mPathMeasure.getPosTan(0, pos, null);

        mSearch.lineTo(pos[0], pos[1]);

        mDst = new Path();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        switch (mState) {
            case NONE:
                canvas.drawPath(mSearch, mDefPaint);
                canvas.drawPath(mOutCircle, mDefPaint);
                break;
            case START:
            case DONE:
                mPathMeasure.setPath(mSearch, false);
                mDst.reset();
                mPathMeasure.getSegment(mPathMeasure.getLength() * mCurrentValue, mPathMeasure.getLength(), mDst, true);
                canvas.drawPath(mDst, mDefPaint);
                break;
            case SEARCHING:
                mPathMeasure.setPath(mOutCircle, false);
                mDst.reset();
                mPathMeasure.getSegment(mPathMeasure.getLength() * mCurrentValue - (float) (100 * (0.5 - Math.abs(mCurrentValue - 0.5))), mPathMeasure.getLength() * mCurrentValue, mDst, true);
                canvas.drawPath(mDst, mDefPaint);
                break;
        }
    }


    public void stop() {
        cancelAll();
    }

    public void start() {
        cancelAll();
        mState = State.START;
        mSearchStart.start();
    }


    enum State {
        NONE, SEARCHING, DONE, START
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelAll();
        super.onDetachedFromWindow();
    }

    private void cancelAll() {
        mState = State.NONE;
        // 尼玛，cancel可能会触发onAnimationStart(),onAnimationEnd(),onAnimationCancel()这几个方法
        mSearchStart.cancel();
        mSearchEnd.cancel();
        mCircleStart.cancel();
        invalidate();
    }
}
