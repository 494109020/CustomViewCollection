package com.customviewcollection.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.customviewcollection.R;

/**
 * Created by Magina on 12/30/16.
 * 类功能介绍: 旋转的箭头
 * 模仿学习：http://gcssloop.com/customview/CustomViewIndex
 */

public class RotateArrowView extends View {

    private Path mPath;
    private Paint mPaint;
    private Bitmap bitmap;
    private int mWidth;
    private int mHeight;
    private PathMeasure mPathMeasure;
    private float[] tan;
    private float[] pos;
    private float mCurrentValue;
    private Matrix mMatrix;

    public RotateArrowView(Context context) {
        super(context);
    }

    public RotateArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff000000);

        pos = new float[2];
        tan = new float[2];

        mMatrix = new Matrix();

        mPath = new Path();
        mPathMeasure = new PathMeasure();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow, options);
    }

    public RotateArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        mPathMeasure.setPath(mPath, false);

        // 下面两种方式都能够实现效果。不过第二中代码层面来说更简洁一点。

        // 获取对应位置点的坐标以及tan值
//        mPathMeasure.getPosTan(mPathMeasure.getLength() * mCurrentValue, pos, tan);
//        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
//        mMatrix.reset();
//        mMatrix.postRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        // 这里其实是对bitmap的移动，要搞清楚了。
//        mMatrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);

        // 这个方法注意最后一个参数flag。 这里可以只设置一个
        // PathMeasure.POSITION_MATRIX_FLAG  这个是位置的matrix
        // PathMeasure.TANGENT_MATRIX_FLAG   这个是tan值的matrix
        mPathMeasure.getMatrix(mPathMeasure.getLength() * mCurrentValue, mMatrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        mMatrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);

        canvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(bitmap, mMatrix, null);

        mCurrentValue += 0.001f;
        if (mCurrentValue > 1) {
            mCurrentValue = 0;
        }
        invalidate();
    }

    public void setSimpleSize(int simpleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = simpleSize;
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow, options);
        invalidate();
    }
}
