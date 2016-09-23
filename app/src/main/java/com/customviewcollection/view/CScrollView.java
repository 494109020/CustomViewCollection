package com.customviewcollection.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Magina on 16/9/18.
 * 关于Scroller的简单使用。
 */
public class CScrollView extends View {

    private Scroller scroller;

    public CScrollView(Context context) {
        super(context);
    }

    public CScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
        scroller.startScroll(0, 0, 100, 100, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

}
