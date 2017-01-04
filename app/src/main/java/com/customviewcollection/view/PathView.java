package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Magina on 12/28/16.
 * 类功能介绍:Path的学习使用.这个记得要关闭activity的硬件加速。要不然效果可能有偏差
 * http://gcssloop.com/customview/CustomViewIndex
 */

public class PathView extends View {

    private static final String TAG = PathView.class.getSimpleName();
    private Path mPath;
    private int mHeight;
    private int mWidth;
    private Path.Direction mInnerDirection, mOuterDirection;
    private Paint mPaint;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mInnerDirection = mOuterDirection = Path.Direction.CW;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffff0000);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPath.reset();
        mPath.addRect(-200, -200, 200, 200, mInnerDirection);
        mPath.addRect(-300, -300, 300, 300, mOuterDirection);
        canvas.drawPath(mPath, mPaint);
    }

    public void setInnerDirection(Path.Direction direction) {
        mInnerDirection = direction;
        invalidate();
    }

    public void setOuterDirection(Path.Direction direction) {
        mOuterDirection = direction;
        invalidate();
    }

    public void setRule(Path.FillType type) {
        mPath.setFillType(type);
        invalidate();
    }


}
