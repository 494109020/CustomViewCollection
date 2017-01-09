package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.customviewcollection.R;

/**
 * Created by Magina on 1/9/17.
 * 类功能介绍:
 */

public class XfermodeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private Canvas mCanvas;
    private RectF rectF;
    private Bitmap mPic;

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPic = BitmapFactory.decodeResource(getResources(),
                R.mipmap.sishen);

        mPaint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xffff0000);

        mCanvas = new Canvas();
        mBitmap = Bitmap.createBitmap(mPic.getWidth() + 100, mPic.getHeight() + 100, Bitmap.Config.ARGB_4444);
        mCanvas.setBitmap(mBitmap);
        rectF = new RectF(50, 50, mPic.getWidth() + 50, mPic.getHeight() + 50);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        // 绘制一层背景色
        canvas.drawColor(0xff0000ff);
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    public void setMode(PorterDuff.Mode mode) {
        // 清除画布上的原有内容   注意：清除之后，画布上是完全无色透明的。
        mCanvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
        // 先绘制的是DST
        mCanvas.drawRoundRect(rectF, 50, 50, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(mode));
        // 后绘制的是SRC
        mCanvas.drawBitmap(mPic, 100, 100, mPaint);
        mPaint.setXfermode(null);
        invalidate();
    }
}
