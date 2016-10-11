package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.proxy.TestInterface;
import com.customviewcollection.proxy.TestInterfaceImpl;
import com.customviewcollection.proxy.TestInvocationHandler;

import java.lang.reflect.Proxy;


/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 * java动态代理的学习
 */
public class DynamicProxyActivity extends BaseActivity {

    private static final String TAG = "DynamicProxyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_proxy);
        TestInterfaceImpl impl = new TestInterfaceImpl();
        TestInvocationHandler invocationHandler = new TestInvocationHandler(impl);

        //这里传入InvocationHandler对象,是为了在创建代理类的时候作为构造参数传入的。
        //当动态代理调用方法的时候,实际上是调用了InvocationHandler.invoke方法。
        //然后InvocationHandler再调用委托类中的具体实现方法。
        TestInterface testInterface = (TestInterface) Proxy.newProxyInstance(TestInterface.class.getClassLoader(), new Class[]{TestInterface.class}, invocationHandler);
        testInterface.test();

    }


}
