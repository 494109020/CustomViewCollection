package com.customviewcollection.transition;

import android.animation.Animator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by Magina on 16/10/9.
 * 类功能介绍:
 * 自定义的Transtion
 */

public class CTransition extends Transition {

    /**
     * 框架会对开始Scene中的每一个对象调用captureStartValues()方法，
     * 方法的参数是TransitionValues 对象，
     * 这个对象包含对应这个View的一个引用和一个Map实例，
     * 这个Map实例用来保存你需要的属性值，
     * 为了保证属性值的Key不与其他的TransitionValues 的Key 冲突，推荐使用如下的命名规则。
     * package_name:transition_name:property_name
     */
    private static final String PROPNAME_BACKGROUND =
            "com.customviewcollection.transition:CTransition:background";


    /**
     * 计算初始值
     *
     * @param transitionValues
     */
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /**
     * 计算结束值
     *
     * @param transitionValues
     */
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        //获取对应的View的引用以及Map实例。
        View view = transitionValues.view;
        Map<String, Object> values = transitionValues.values;
    }


    /**
     * 这个不是必需重写的,视具体的情况而定。
     */
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        //当startValues和endValues同时存在的时候,这个Transition才可以应用到View上。
        if (startValues == null || endValues == null) return null;
        //startValues.view和endValues.view指的是同一个对象
        View view = startValues.view;
//        View view = endValues.view;

        //... 这里就是做具体的变化操作了。最后将创建的Animator对象返回。

        //默认返回空
        return null;
    }
}
