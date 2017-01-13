package com.customviewcollection.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.customviewcollection.R;

/**
 * Created by Magina on 16/9/18.
 * 关于Scroller的简单使用。以及各种看起来像View的移动或者说View内容的移动
 */
public class CScrollView extends View {

    private static final String TAG = CScrollView.class.getSimpleName();

    private Scroller mScroller;
    private Bitmap mBitmap;
    private ValueAnimator mAnimator;
    private Type mCurrentType;
    private ValueAnimator.AnimatorUpdateListener mAnimatorListener =
            new ValueAnimator.AnimatorUpdateListener()

            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    switch (mCurrentType) {
                        case SCROLLTO:
                            // 这个是基于绝对坐标的View内容的移动
                            // 最终改变的是mScrollX 和 mScrollY的值
                            // 值为正的时候，移动的是负方向。切记
                            // 会触发重绘。只是显示的内容位置发生了变化
                            // 点击区域仍在原始位置
                            scrollTo(value, value);
                            break;
                        case SCROLLBY:
                            // 这个是基于相对当前坐标的View的内容的移动
                            // 最终改变的是mScrollX 和 mScrollY的值
                            // 值为正的时候，移动的是负方向。切记
                            // 会触发重绘。当然，看源码就会发现，他是基于scrollTo的
                            // 点击区域仍在原始位置
                            scrollBy(value, value);
                            break;
                        case OFFSET:
                            // ＊这个是可以改变View在布局中的位置的＊
                            // 移动是基于当前位置进行的
                            // 根据测试发现，最终left = getLeft()等这4个方法的值发生了变化
                            // 同时，因为 x = left + translationX，所以x也随着发生了变化。
                            // 这个方法不会触发重绘
                            offsetLeftAndRight(value);
                            offsetTopAndBottom(value);
                            break;
                        case SETXY:
                            // 这个是改变 x 和 y 的值的
                            // 由于 x = left + translationX
                            // 所以，同时也相当于改变了 translationX
                            // 这个方法不会触发重绘
                            // 看源码可知，这个方法内部是基于setTranslationX()的。
                            setX(value);
                            setY(value);
                            break;
                        case SETTRANSLATIONXY:
                            // 这个是改变 translationX 和 translationY 的值的
                            // 由于 x = left + translationX
                            // 所以，同时也相当于改变了 x
                            // 这个方法不会触发重绘
                            // 由于translationX/translationX 是基于当前布局位置的偏移量，
                            // 可以理解为改变了布局位置。在开启查看布局位置时可以发现，
                            // 实际的布局位置还是在原处。不过点击区域已经不在原处了。
                            // 而在实际的内容显示区域
                            setTranslationX(value);
                            setTranslationY(value);
                            break;
                    }
                    Log.e(TAG, "getTranslationX()=" + getTranslationX() +
                            "-getTranslationY()=" + getTranslationY() +
                            "-getX()=" + getX() +
                            "-getY()" + getY() +
                            "-getScrollX()=" + getScrollX() +
                            "-getScrollY()" + getScrollY() +
                            "-getLeft()=" + getLeft() +
                            "-getTop()=" + getTop() +
                            "-getRight()=" + getRight() +
                            "-getBottom()=" + getBottom());
                }
            };

    public CScrollView(Context context) {
        super(context);
    }

    public CScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.sishen);
        mScroller = new Scroller(getContext());

        setLayerType(LAYER_TYPE_HARDWARE, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        Log.e(TAG, "invalidate()触发");
    }

    public void setScroll(final Type type) {
        mCurrentType = type;
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, 10).setDuration(1);
            mAnimator.addUpdateListener(mAnimatorListener);
        }
        if (mAnimator.isStarted())
            mAnimator.cancel();
        mAnimator.start();
    }

    public void startScroll() {
        mScroller.startScroll(0, 0, -100, -100, 1000);
        invalidate();
    }

    public enum Type {
        SCROLLTO, SCROLLBY, SETXY, OFFSET, SETTRANSLATIONXY
    }

}
