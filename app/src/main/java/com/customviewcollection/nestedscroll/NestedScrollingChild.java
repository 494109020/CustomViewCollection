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

/**
 * This interface should be implemented by {@link android.view.View View} subclasses that wish
 * to support dispatching nested scrolling operations to a cooperating parent
 * {@link android.view.ViewGroup ViewGroup}.
 * <p>
 * <p>Classes implementing this interface should create a final instance of a
 * {@link NestedScrollingChildHelper} as a field and delegate any View methods to the
 * <code>NestedScrollingChildHelper</code> methods of the same signature.</p>
 * <p>
 * <p>Views invoking nested scrolling functionality should always do so from the relevant
 * {@link ViewCompat}, {@link ViewGroupCompat} or {@link ViewParentCompat} compatibility
 * shim static methods. This ensures interoperability with nested scrolling views on Android
 * 5.0 Lollipop and newer.</p>
 */
public interface NestedScrollingChild {

    // 设置是否允许嵌套滑动
    public void setNestedScrollingEnabled(boolean enabled);

    // 是否允许嵌套滑动
    public boolean isNestedScrollingEnabled();

    /**
     * 告诉开始嵌套滑动流程，调用这个函数的时候会去找嵌套滑动的父控件。
     * 如果找到了父控件并且父控件说可以滑动就返回true，否则返回false
     * (一般ACTION_DOWN里面调用)
     *
     * @param axes:支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return true 父控件说可以滑动，false 父控件说不可以滑动
     */
    public boolean startNestedScroll(int axes);

    // 停止嵌套滑动流程(一般ACTION_UP里面调用)
    public void stopNestedScroll();

    // 是否有嵌套滑动对应的父控件
    public boolean hasNestedScrollingParent();

    /**
     * 在嵌套滑动的子View滑动之后再调用该函数向父View汇报滑动情况。
     *
     * @param dxConsumed     子View水平方向滑动的距离
     * @param dyConsumed     子View垂直方向滑动的距离
     * @param dxUnconsumed   子View水平方向没有滑动的距离
     * @param dyUnconsumed   子View垂直方向没有滑动的距离
     * @param offsetInWindow 出参 如果父View滑动导致子View的窗口发生了变化（子View的位置发生了变化）
     *                       该参数返回x(offsetInWindow[0]) y(offsetInWindow[1])方向的变化
     *                       如果你记录了手指最后的位置，需要根据参数offsetInWindow计算偏移量，才能保证下一次的touch事件的计算是正确的。
     * @return true 如果父View有滑动做了相应的处理, false 父View没有滑动.
     */
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow);

    /**
     * 在嵌套滑动的子View滑动之前，告诉父View滑动的距离，让父View做相应的处理。
     *
     * @param dx             告诉父View水平方向需要滑动的距离
     * @param dy             告诉父View垂直方向需要滑动的距离
     * @param consumed       出参. 如果不是null, 则告诉子View父View滑动的情况， consumed[0]父View告诉子View水平方向滑动的距离(dx)
     *                       consumed[1]父View告诉子View垂直方向滑动的距离(dy).
     * @param offsetInWindow 可选 length=2的数组，如果父View滑动导致子View的窗口发生了变化（子View的位置发生了变化）
     *                       该参数返回x(offsetInWindow[0]) y(offsetInWindow[1])方向的变化
     *                       如果你记录了手指最后的位置，需要根据参数offsetInWindow计算偏移量，才能保证下一次的touch事件的计算是正确的。
     * @return true 父View滑动了，false 父View没有滑动。
     */
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow);

    /**
     * 在嵌套滑动的子View fling之后再调用该函数向父View汇报fling情况。
     *
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @param consumed  true 如果子View fling了, false 如果子View没有fling
     * @return true 如果父View fling了
     */
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);

    /**
     * 在嵌套滑动的子View fling之前告诉父View fling的情况。
     *
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @return 如果父View fling了
     */
    public boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
