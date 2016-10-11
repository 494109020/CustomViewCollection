package com.customviewcollection.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Magina on 16/10/11.
 * 类功能介绍:
 * 动态代理必须要自己实现{@link InvocationHandler}。
 */

public class TestInvocationHandler implements InvocationHandler {

    private static final String TAG = "TestInvocationHandler";

    private Object mTarget;//这里是实际的委托类

    public TestInvocationHandler() {}

    public TestInvocationHandler(Object target) {
        this.mTarget = target;
    }

    /**
     * InvocationHandler算是一个中间类,连接了动态代理类(A)和委托类(C)
     * 动态代理类(A)-(委托类)InvocationHandler(代理类)-委托类(C)
     * 算是二次代理吧。
     * 调用流程为:
     * 动态代理类.代理方法
     * ➡️InvocationHandler.invoke(Object proxy, Method method, Object[] args)
     * ➡️委托类的方法
     *
     * @param proxy  动态代理类
     * @param method 要调用的委托类的方法
     * @param args   方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //这里做一些对代理类方法的统一操作,如验证,或者函数执行时间等等。
        long l = System.currentTimeMillis();

        //注意:这里的proxy是动态代理类将自身对象传回来了。
        //下面这行代码在传参数的时候一定要注意不能传proxy。
        //否则就是自身无限调用了。
        Object invoke = method.invoke(mTarget, args);

        Log.i(TAG, "方法执行耗时为" + (System.currentTimeMillis() - l) + "ms");
        return invoke;
    }

}
