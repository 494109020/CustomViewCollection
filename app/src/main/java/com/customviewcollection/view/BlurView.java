package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.customviewcollection.R;

/**
 * Created by Magina on 1/10/17.
 * 类功能介绍:
 */

public class BlurView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private RectF mRectF;
    private Bitmap mAlpahBitmap;

    public BlurView(Context context) {
        super(context);
    }

    public BlurView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xff2f34e2);
        mPaint.setStyle(Paint.Style.FILL);

        mRectF = new RectF(100, 100, 500, 500);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sishen);
        mAlpahBitmap = mBitmap.extractAlpha();
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mRectF, mPaint);

        canvas.drawBitmap(mAlpahBitmap, 100, 700, mPaint);
        canvas.drawBitmap(mBitmap, 100, 700, null);


        canvas.drawBitmap(mBitmap, 100, 1100, mPaint);

    }

    public void setBlurType(BlurMaskFilter.Blur mode) {
        mPaint.setMaskFilter(new BlurMaskFilter(30, mode));
        invalidate();
    }
}
