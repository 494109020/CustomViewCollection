package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Magina on 12/27/16.
 * 类功能介绍:
 */

public class PieView extends View {

    private static final int[] COLORS = {0xffff0000, 0xff00ff00, 0xff0000ff};
    private static final int SWEEP_ANGLE = 30;

    private Paint mPaint;
    private RectF mRectF;
    private int mCurrentAngle;

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mRectF = new RectF(0, 0, 400, 400);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(400, 400);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 12; i++) {
            mPaint.setColor(COLORS[i % 3]);
            canvas.drawArc(mRectF, mCurrentAngle, SWEEP_ANGLE, true, mPaint);
            mCurrentAngle += SWEEP_ANGLE;
        }
    }
}
