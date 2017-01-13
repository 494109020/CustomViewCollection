package com.customviewcollection.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.customviewcollection.R;
import com.customviewcollection.impl.AnimatorListenerImpl;

import java.util.ArrayList;

/**
 * Created by Magina on 1/11/17.
 * 类功能介绍: 翻页效果
 * 模仿学习：http://blog.csdn.net/aigestudio/article/details/42712269
 */

public class PageView extends View {

    private static final int[] IMGS = new int[]{R.mipmap.sishen, R.mipmap.ico_1, R.mipmap.ico_2, R.mipmap.ico_3, R.mipmap.ic_launcher};
    private Path mFoldPath, mFirstPath, mNextPath, mFoldAndNextPath;
    private Paint mPaint;
    private ArrayList<Bitmap> mImgs;
    private float mValidX, mValidY;// 当前实际有效的触摸坐标
    private int mWidth, mHeight;// view的宽高
    private int mCurrentPage;// 当前显示的页码

    private boolean canTurn;//能否翻页
    private boolean isForward;// 是向前翻？还是向后翻
    private boolean isTurning;

    private Path mFullPath;

    public PageView(Context context) {
        super(context);
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mFoldPath = new Path();
        mNextPath = new Path();
        mFirstPath = new Path();
        mFoldAndNextPath = new Path();

    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        if (mImgs == null) {
            mImgs = new ArrayList<>();
            for (int img : IMGS) {
                mImgs.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), img), w, h, true));
            }
        }

        mFullPath = new Path();
        mFullPath.addRect(0, 0, mWidth, mHeight, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 根据当前有效点的位置，计算path

        final float K = mHeight - mValidY;
        final float L = mWidth - mValidX;

        final double temp = Math.pow(K, 2) + Math.pow(L, 2);
        // 折叠区域的x，y长度
        final float x = (float) (temp / (2 * L));
        final float y = (float) (temp / (2 * K));

        mFoldPath.reset();
        mNextPath.reset();
        mFirstPath.reset();
        mFoldAndNextPath.reset();
        // 移动路径起点至实际有效的触摸点
        mFoldPath.moveTo(mValidX, mValidY);
        mFoldAndNextPath.moveTo(mValidX, mValidY);

        if (y > mHeight) {  // 超出屏幕范围了，五边形

            // 计算各个坐标
            final float AN = y - mHeight;
            final float MN = AN / (y - K) * L;// 相似三角形  边MN的长
            final float QN = AN / y * x;

            final float M = mWidth - MN;// M点的x坐标
            final float Q = mWidth - QN;// Q点的X坐标
            final float B = mWidth - x;// B点的X坐标
            // 这个是折起来的区域
            mFoldPath.lineTo(M, 0);
            mFoldPath.lineTo(Q, 0);
            mFoldPath.lineTo(B, mHeight);
            mFoldPath.close();
            // 这个是包含折起来的以及下一页暴露出来的区域
            mFoldAndNextPath.lineTo(M, 0);
            mFoldAndNextPath.lineTo(mWidth, 0);
            mFoldAndNextPath.lineTo(mWidth, mHeight);
            mFoldAndNextPath.lineTo(B, mHeight);
            mFoldAndNextPath.close();

        } else { // 三角形

            final float B = mHeight - y;
            final float A = mWidth - x;
            // 这个是折起来的区域
            mFoldPath.lineTo(mWidth, B);
            mFoldPath.lineTo(A, mHeight);
            mFoldPath.close();
            // 这个是包含折起来的以及下一页暴露出来的区域
            mFoldAndNextPath.lineTo(mWidth, B);
            mFoldAndNextPath.lineTo(mWidth, mHeight);
            mFoldAndNextPath.lineTo(A, mHeight);
            mFoldAndNextPath.close();

        }


        if (isTurning) {// 正在翻页
            // 由于在touch事件中，已经做过了限制。所以这里不需要判断了。
            final int firstPage = mCurrentPage + (isForward ? -1 : 0);
            final int lastPage = firstPage + 1;
//            canvas.drawBitmap(mImgs.get(lastPage), 0, 0, null);
//            canvas.save();
//            canvas.clipRect(0, 0, mCurrtX, mHeight);
//            canvas.drawBitmap(mImgs.get(firstPage), 0, 0, null);
//            canvas.restore();


            // 画当前页面
            canvas.save();
            mFirstPath.op(mFullPath, mFoldAndNextPath, Path.Op.DIFFERENCE);
            canvas.clipPath(mFirstPath);
            canvas.drawBitmap(mImgs.get(firstPage), 0, 0, null);
            canvas.restore();

            // 画折叠部分
            canvas.save();
            canvas.clipPath(mFoldPath);
            canvas.translate(mWidth + mValidX, -(mHeight - mValidY));
            canvas.scale(-1, 1);
            final float degrees = (float) (Math.asin((mHeight - mValidY) / x) / Math.PI * 180);
            if (mWidth - x < mValidX) {// 旋转大于90度了。
                canvas.rotate(-(180 - degrees), mWidth, mHeight);
            } else {
                canvas.rotate(-degrees, mWidth, mHeight);
            }
            canvas.drawBitmap(mImgs.get(firstPage), 0, 0, null);
            canvas.restore();


            // 画下一页
            canvas.save();
            mNextPath.op(mFoldAndNextPath, mFoldPath, Path.Op.DIFFERENCE);
            canvas.clipPath(mNextPath);
            canvas.drawBitmap(mImgs.get(lastPage), 0, 0, null);
            canvas.restore();
        } else {
            canvas.drawBitmap(mImgs.get(mCurrentPage), 0, 0, null);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float currtX = event.getX();
        float currtY = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (((currtX < 200 && mCurrentPage > 0) || (currtX > mWidth - 200 && mCurrentPage < mImgs.size() - 1)) && currtY > mHeight - 200) {
                    canTurn = true;
                    isForward = currtX < 200;
                } else {
                    canTurn = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isTurning = canTurn;
                // 如果落点超出范围，则根据当前的x计算y
                double r = Math.pow(mWidth, 2);
                double x = Math.pow(currtX, 2);
                if (x + Math.pow(mHeight - currtY, 2) > r) {
                    mValidY = (float) (mHeight - Math.sqrt(r - x));
                } else {
                    mValidY = currtY;
                }
                mValidX = currtX;
                break;
            case MotionEvent.ACTION_UP:
                if (isTurning) {
                    autoTurnPage();
                }
                break;
        }
        if (canTurn) {
            invalidate();
        }
        return true;
    }

    private void autoTurnPage() {
        final float end = mValidX > mWidth / 2 ? mWidth : 0;
        final float startY = mValidY;
        ValueAnimator turnPageXAnimator = ValueAnimator.ofFloat(mValidX, end).setDuration(200);
        turnPageXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValidX = (Float) animation.getAnimatedValue();
                mValidY = startY + animation.getAnimatedFraction() * (mHeight - startY);
                invalidate();
            }
        });
        turnPageXAnimator.addListener(new AnimatorListenerImpl() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isForward && end == mWidth) {
                    mCurrentPage--;
                    if (mCurrentPage < 0) {
                        mCurrentPage = 0;
                    }
                } else if (!isForward && end == 0) {
                    mCurrentPage++;
                    if (mCurrentPage >= mImgs.size()) {
                        mCurrentPage = mImgs.size() - 1;
                    }
                }
                isTurning = false;
            }
        });
        turnPageXAnimator.start();
    }

}
