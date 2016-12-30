package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Magina on 12/28/16.
 * 类功能介绍:Path的学习使用
 */

public class PathOPView extends View {

    private static final String TAG = PathOPView.class.getSimpleName();
    private Path mPath, mPath1, mPath2;
    private int mHeight;
    private int mWidth;
    private Path.Direction mInnerDirection, mOuterDirection;
    private Paint mPaint;
    private Path.Op mCurrentOp = Path.Op.UNION;

    public PathOPView(Context context) {
        super(context);
    }

    public PathOPView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPath1 = new Path();
        mPath1.addCircle(100, 100, 100, Path.Direction.CW);
        mPath2 = new Path();
        mPath2.addCircle(250, 100, 100, Path.Direction.CW);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffff0000);
    }

    public PathOPView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPath.reset();
        // 这种是将两个op操作的结果存入mPath
        mPath.op(mPath1, mPath2, mCurrentOp);
        // 这种是将op操作结果存入mPath1
        // mPath1.op(mPath2, mCurrentOp);
        canvas.drawPath(mPath, mPaint);
    }


    public void setOP(Path.Op op) {
        mCurrentOp = op;
        invalidate();
    }


}
