package com.customviewcollection.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.customviewcollection.R;

/**
 * Created by Magina on 16/9/6.
 * 自定义一个简单的TextView。了解其文本绘制原理
 */
public class CustomTextView extends View {

    private int mTextColor;
    private int mTextSize;
    private String mText;
    private Rect mRect;
    private Paint mPaint;

    public CustomTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView1, defStyleAttr, 0);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView1, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.CustomTextView1_txtColor:
                    mTextColor = a.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomTextView1_txtSize:
                    mTextSize = a.getDimensionPixelOffset(index, 15);
                    break;
                case R.styleable.CustomTextView1_txt:
                    mText = a.getString(index);
                    break;
            }
        }

//        注意，属性的获取还有这么一种方式。两种对比：
//        上面的那种方式，只会获取到xml中配置的属性，而没有配置的属性将不会执行到。
//        下面的这种方式，不论你在xml中又没有配置该属性，都会为该属性赋值，值为xml中配置的或者默认的
//        mTextColor = a.getColor(R.styleable.CustomTextView1_txtColor, Color.BLACK);
//        mTextSize = a.getDimensionPixelOffset(R.styleable.CustomTextView1_txtSize, 15);
//        mText = a.getString(R.styleable.CustomTextView1_txt);

        a.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        mRect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mRect);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里可以的到控件最新的宽高
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;

        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mRect);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mRect.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mRect.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //关于文字绘制,可以参考http://blog.csdn.net/aigestudio/article/details/41447349

//        要知道，控件的宽高是这么得来的
//        getHeight() = mRect.height()+getPaddingTop()+getPaddingBottom();
//        getWidth() = mRect.width() +getPaddingLeft()+getPaddingRight()

        //TextView的文字默认是从左到右，从下到上进行绘制的。
        //当设置了Align之后，Align.LEFT,Align.CENTER,Align.RIGHT分别对应x为文字的开始位置，文本居中位置，文本结束位置。
        float x = getPaddingLeft();
        //y为文字最下方的位置,且不受Align的影响
        float y = getHeight() - getPaddingBottom();
        // 计算Baseline绘制的Y坐标
        int baseY = (int) ((canvas.getHeight() / 2) + ((Math.abs(mPaint.ascent() - Math.abs(mPaint.descent()))) / 2));
        canvas.drawText(mText, x, y, mPaint);
        //绘制两条辅助线
        canvas.drawLine(0, y, getWidth(), y, mPaint);
        canvas.drawLine(x, 0, x, getHeight(), mPaint);
    }

    public void setTextAlign(Paint.Align align) {
        mPaint.setTextAlign(align);
        invalidate();
    }
}