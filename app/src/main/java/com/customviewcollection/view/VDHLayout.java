package com.customviewcollection.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Magina on 11/23/16.
 * 类功能介绍:
 */

public class VDHLayout extends ViewGroup {

    private final ViewDragHelper mViewDragHelper;

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 第二个参数：主要用于设置touchSlop，传入越大，mTouchSlop的值就会越小。
         */
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 当ViewDragHelper状态发生变化时回调（IDLE,DRAGGING,SETTING[自动滚动时]）
             * @param state
             */
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            /**
             * 当captureview的位置发生改变时回调
             * @param changedView
             * @param left
             * @param top
             * @param dx
             * @param dy
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            /**
             * 当captureview被捕获时回调
             * @param capturedChild
             * @param activePointerId
             */
            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            /**
             * 手指释放时候的回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            /**
             * 当触摸到边界时回调。
             * @param edgeFlags
             * @param pointerId
             */
            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            /**
             * true的时候会锁住当前的边界，false则unLock。
             * @param edgeFlags
             * @return
             */
            @Override
            public boolean onEdgeLock(int edgeFlags) {
                return super.onEdgeLock(edgeFlags);
            }

            /**
             * 在边界拖动时回调
             * 注意：如果要使用边界检测，则需要添加
             * {@link ViewDragHelper#setEdgeTrackingEnabled(int)}
             * @param edgeFlags
             * @param pointerId
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            /**
             * 改变同一个坐标（x,y）去寻找captureView位置的方法。（具体在：findTopChildUnder方法中）
             * @param index
             * @return
             */
            @Override
            public int getOrderedChildIndex(int index) {
                return super.getOrderedChildIndex(index);
            }

            /**
             * view的水平方向拖动范围
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            /**
             * view的竖直方向拖动范围
             * @param child
             * @return
             */
            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }

            /**
             * view的水平方向边界
             * @param child
             * @param left
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            /**
             * view的竖直方向边界
             * @param child
             * @param top
             * @param dy
             * @return
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return super.clampViewPositionVertical(child, top, dy);
            }

            /**
             * 是否要捕获这个view。true为捕获
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return false;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
