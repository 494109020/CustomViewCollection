/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.customviewcollection.nestedscroll;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

/**
 * Helper class for implementing nested scrolling child views compatible with Android platform
 * versions earlier than Android 5.0 Lollipop (API 21).
 * <p>
 * <p>{@link android.view.View View} subclasses should instantiate a final instance of this
 * class as a field at construction. For each <code>View</code> method that has a matching
 * method signature in this class, delegate the operation to the helper instance in an overriden
 * method implementation. This implements the standard framework policy for nested scrolling.</p>
 * <p>
 * <p>Views invoking nested scrolling functionality should always do so from the relevant
 * {@link ViewCompat}, {@link ViewGroupCompat} or {@link ViewParentCompat} compatibility
 * shim static methods. This ensures interoperability with nested scrolling views on Android
 * 5.0 Lollipop and newer.</p>
 */
public class NestedScrollingChildHelper {
    private final View mView;
    private ViewParent mNestedScrollingParent;
    private boolean mIsNestedScrollingEnabled;
    private int[] mTempNestedScrollConsumed;

    /**
     * 通过给定的子View构造NestedScrollingChildHelper
     */
    public NestedScrollingChildHelper(View view) {
        mView = view;
    }

    /**
     * 检查是否允许嵌套滑动
     */
    public boolean isNestedScrollingEnabled() {
        return mIsNestedScrollingEnabled;
    }

    /**
     * 检查是否有嵌套滑动对应的父View
     */
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingParent != null;
    }

    /**
     * 告诉父View准备开始嵌套滑动(这个函数做的事情就是去找嵌套滑动对应父View并且判断该父View是否接收嵌套滑动的事件)
     * 讲道理的话该函数会调用到嵌套对应的父View的onStartNestedScroll 和 onNestedScrollAccepted函数
     *
     * @param axes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return true 找到了嵌套滑动的父View，并且父View会接受嵌套滑动事件。
     */
    public boolean startNestedScroll(int axes) {
        if (hasNestedScrollingParent()) {
            // Already in progress
            return true;
        }
        if (isNestedScrollingEnabled()) {
            ViewParent p = mView.getParent();
            View child = mView;
            while (p != null) {
                if (ViewParentCompat.onStartNestedScroll(p, child, mView, axes)) {
                    mNestedScrollingParent = p;
                    ViewParentCompat.onNestedScrollAccepted(p, child, mView, axes);
                    return true;
                }
                if (p instanceof View) {
                    child = (View) p;
                }
                p = p.getParent();
            }
        }
        return false;
    }

    /**
     * 设置是否允许嵌套滑动(如果当前View已经设置了嵌套滑动则会调用到当前View的stopNestedScroll)
     * 其实最后也是调用了嵌套对应的父View的onStopNestedScroll函数
     * 注意和{@link #stopNestedScroll()}的区分
     *
     * @param enabled true 是否允许嵌套滑动
     */
    public void setNestedScrollingEnabled(boolean enabled) {
        if (mIsNestedScrollingEnabled) {
            //这里其实是做了一下版本兼容。版本21(含)以上的,View自带了这个方法。
            //默认实现为:
            //if (mNestedScrollingParent != null) {
            //    mNestedScrollingParent.onStopNestedScroll(this);
            //    mNestedScrollingParent = null;
            //}
            ViewCompat.stopNestedScroll(mView);
        }
        mIsNestedScrollingEnabled = enabled;
    }

    /**
     * 告诉嵌套滑动对于的父View嵌套滑动结束
     * 讲道理的话该函数会调用到嵌套对应的父View的onStopNestedScroll函数
     * 即:parent.onStopNestedScroll(target);
     */
    public void stopNestedScroll() {
        /**
         * 这里个人有点疑问:方法实现为什么不是调用ViewCompat.stopNestedScroll(mView);
         * 参考{@link #setNestedScrollingEnabled(boolean)}方法。
         * ViewCompat.stopNestedScroll(mView);该方法最终也是实现此效果。
         * 反之,为什么在{@link #setNestedScrollingEnabled(boolean)}方法中不直接调用本方法,
         * 而是通过ViewCompat.stopNestedScroll(mView)来实现。
         */
        if (mNestedScrollingParent != null) {
            ViewParentCompat.onStopNestedScroll(mNestedScrollingParent, mView);
            mNestedScrollingParent = null;
        }
    }

    /**
     * 在子View处理了滑动动作之后告诉嵌套滑动对于的父View子View的滑动情况。参数和NestedScrollingChild里面的对应。
     * 讲道理的话该函数会调用到嵌套对应的父View的onNestedScroll函数
     * 这里注意下offsetInWindow这个参数是个出参 是子View位置的变化值。
     * 这个参数的变化值，不用我们在嵌套滑动的父View里面去设置，在这个函数里面已经设置了。
     *
     * @return true 如果嵌套滑动对应的父View有滑动
     */
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
            if (dxConsumed != 0 || dyConsumed != 0 || dxUnconsumed != 0 || dyUnconsumed != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }

                ViewParentCompat.onNestedScroll(mNestedScrollingParent, mView, dxConsumed,
                        dyConsumed, dxUnconsumed, dyUnconsumed);

                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] -= startX;
                    offsetInWindow[1] -= startY;
                }
                return true;
            } else if (offsetInWindow != null) {
                // No motion, no dispatch. Keep offsetInWindow up to date.
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    /**
     * 在子View处理滑动事件之前，告诉嵌套滑动对应的父View滑动的情况。
     * 参数的意思和NestedScrollingChild里面的对应
     * 讲道理的话该函数会调用到嵌套对应的父View的onNestedPreScroll函数
     * 同时也可以看到offsetInWindow不用我们在父View里面去设置，但是consumed这个参数是要我们在父View里面去设置的。
     *
     * @return true 嵌套滑动对应的父View有滑动。
     */
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
            if (dx != 0 || dy != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }

                if (consumed == null) {
                    if (mTempNestedScrollConsumed == null) {
                        mTempNestedScrollConsumed = new int[2];
                    }
                    consumed = mTempNestedScrollConsumed;
                }
                consumed[0] = 0;
                consumed[1] = 0;
                ViewParentCompat.onNestedPreScroll(mNestedScrollingParent, mView, dx, dy, consumed);

                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] -= startX;
                    offsetInWindow[1] -= startY;
                }
                return consumed[0] != 0 || consumed[1] != 0;
            } else if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    /**
     * 子View fling之后把fling的情况报告给嵌套滑动对应的父View
     * 讲道理的话该函数会调用到嵌套对应的父View的onNestedFling函数
     *
     * @return true 嵌套滑动对于的父View fling了
     */
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
            return ViewParentCompat.onNestedFling(mNestedScrollingParent, mView, velocityX,
                    velocityY, consumed);
        }
        return false;
    }

    /**
     * 子View fling之前 告诉嵌套滑动对应的父View fling的情况
     * 讲道理的话该函数会调用到嵌套对应的父View的onNestedPreFling函数
     *
     * @return true 嵌套滑动对应的父View消耗了fling事件
     */
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
            return ViewParentCompat.onNestedPreFling(mNestedScrollingParent, mView, velocityX,
                    velocityY);
        }
        return false;
    }

    /**
     * 当子View脱离窗口的时候调用该函数告知停止嵌套滑动
     * 该函数会调用到当前View(嵌套滑动的子View)的stopNestedScroll函数
     */
    public void onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(mView);
    }

    /**
     * 告知停止嵌套滑动
     * 该函数会调用到当前View(嵌套滑动的子View)的stopNestedScroll函数
     */
    public void onStopNestedScroll(View child) {
        ViewCompat.stopNestedScroll(mView);
    }
}
