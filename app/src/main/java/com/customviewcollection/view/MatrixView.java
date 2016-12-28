package com.customviewcollection.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.customviewcollection.R;

/**
 * Created by Magina on 12/14/16.
 * 类功能介绍:
 * 关于Matrix的一点学习。
 */

public class MatrixView extends View {

    private static final String TAG = MatrixView.class.getSimpleName();

    private Bitmap bitmap;
    private Matrix matrix;

    private int mWidth;
    private int mHeight;

    private boolean flag = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private Rect mSrc;
    private ValueAnimator mAnimatorClose;
    private ValueAnimator mAnimatorOpen;

    public MatrixView(Context context) {
        super(context);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sishen);
        matrix = new Matrix();
        matrix.reset();
        Log.i(TAG, matrix.toShortString());

        mSrc = new Rect();

        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean hardwareAccelerated = canvas.isHardwareAccelerated();
        Log.e(TAG, "canvas hardwareAccelerated=" + hardwareAccelerated);
        Log.e(TAG, "view hardwareAccelerated=" + isHardwareAccelerated());
        if (flag) {
            canvas.drawBitmap(bitmap, 0, 0, null);

//        matrix.reset();// 恢复为单位矩阵
//        matrix.postScale(0.5f, 0.5f);// 由于单位矩阵的乘法AE=EA. 所以这里使用postXxx或者preXxx是没有什么实质区别的。
//        matrix.postScale(0.5f, 0.5f, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            // 在矩阵执行过缩放之后的操作一定要注意，
            // 如：现在的bitmap.getWidth() / 2是在已经缩放为0.5之后的矩阵的结果上进行的。
            // 这个时候的bitmap.getWidth()已然是最原始状态的1/2了。如果移动bitmap.getWidth() / 2，
            // 那么实际上它只会移动最原始的宽度的1/4.此时得到的最终bitmap会显示在原始bitmap的正中央
//        matrix.preTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            // 上面那个结论需要注意的是，matrix是在缩放之后执行的平移操作。
            // 如果在缩放之前执行平移操作。注意，这两种方式得到的结果是完全不同的。
            // 这个得到的结果bitmap在原始bitmap的右下角
//        matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//        matrix.postRotate(45);
            // 所以一定要注意，矩阵的乘法顺序。
            canvas.drawBitmap(bitmap, matrix, null);

            //关于post和pre举个例子：
            //M.reset();M.post(A);M.pre(B);M.pre(C);M.post(D);M.pre(E);
            //列出式子为：D*A*M*B*C*E
            // so，懂了么？
        } else {
            // src：bitmap中要绘制的区域。dst：将要绘制到的区域
            // 这个就和matrix无关了。这个要注意的是，如果src和dst不一致，src中的图片会缩放
            // 为dst的大小。
            canvas.drawBitmap(bitmap, mSrc, mSrc, null);
        }
    }

    public void TpostS() {
        flag = true;
        matrix.reset();// 恢复为单位矩阵
        matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.postScale(0.5f, 0.5f);
        invalidate();
    }

    public void TpreS() {
        flag = true;
        matrix.reset();// 恢复为单位矩阵
        matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.preScale(0.5f, 0.5f);
        invalidate();
    }

    public void TpostR() {
        flag = true;
        matrix.reset();// 恢复为单位矩阵
        matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.postRotate(45);
        invalidate();
    }

    public void TpreR() {
        flag = true;
        matrix.reset();// 恢复为单位矩阵
        matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.preRotate(45);
        invalidate();
    }

    public void SpostR() {
        flag = true;
        matrix.reset();// 恢复为单位矩阵
        matrix.preScale(0.5f, 0.5f);
        matrix.postRotate(45);
        invalidate();
    }

    public void SpreR() {
        flag = true;
        matrix.reset();// 恢复为单位矩阵
        matrix.preScale(0.5f, 0.5f);
        matrix.preRotate(45);
        invalidate();
    }

    public void clipOpen() {
        flag = false;
        if (mAnimatorOpen == null) {
            mAnimatorOpen = ValueAnimator.ofInt(0, bitmap.getWidth());
            mAnimatorOpen.setDuration(1000);
            mAnimatorOpen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (Integer) animation.getAnimatedValue();
                    mSrc.set(0, 0, animatedValue, bitmap.getHeight());
                    invalidate();
                }
            });
        }
        if (mAnimatorOpen.isStarted())
            mAnimatorOpen.cancel();
        mAnimatorOpen.start();
    }

    public void clipClose() {
        flag = false;
        if (mAnimatorClose == null) {
            mAnimatorClose = ValueAnimator.ofInt(bitmap.getWidth(), 0);
            mAnimatorClose.setDuration(1000);
            mAnimatorClose.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (Integer) animation.getAnimatedValue();
                    mSrc.set(0, 0, animatedValue, bitmap.getHeight());
                    invalidate();
                }
            });
        }
        if (mAnimatorClose.isStarted())
            mAnimatorClose.cancel();
        mAnimatorClose.start();
    }
}
