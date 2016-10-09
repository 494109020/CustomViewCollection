package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.customviewcollection.BaseActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Magina on 16/9/22.
 * 类功能介绍:
 * RxJava的简单使用
 * 参考文章:https://gank.io/post/560e15be2dca930e00da1083
 */
public class RxJavaActivity extends BaseActivity {

    private static final String TAG = "RxJava";
    private TextView textView;
    private StringBuilder sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                test();
            }
        }).start();
//        test();
        sb = new StringBuilder();
    }

    public void test() {
        Log.i(TAG, "test()执行在" + Thread.currentThread().getName());
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG, "call()执行在" + Thread.currentThread().getName());
                sb.append("call()执行在").append(Thread.currentThread().getName()).append("\r\n");
                textView.setText(sb.toString());
                //call()方法执行的线程为subscribeOn()方法所指定的线程。
                //此处注意:onStart()方法执行的线程与subscribeOn()方法指定的线程没有关系。
                //也就是说可能和call()执行的线程不是同一个。详细看onStart()方法的注释说明。
                //而onNext()和onCompleted()必然在同一个线程中执行。该线程由observeOn()指定
                //也同样和call()执行所在的线程没有关系。这里一定要搞清楚。
                subscriber.onStart();
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        })
                //subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
                //subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。
                .subscribeOn(Schedulers.newThread())

                //默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
                // 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，
                // 它将执行在离它最近的 subscribeOn() 所指定的线程。
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "doOnSubscribe()执行在" + Thread.currentThread().getName());
                        sb.append("doOnSubscribe()执行在").append(Thread.currentThread().getName()).append("\r\n");
                        textView.setText(sb.toString());
                    }
                })
                //这里是为了搭配doOnSubscribe()方法使用的。
                // 除此以外,只有第一个subscribeOn()才有效。
                .subscribeOn(AndroidSchedulers.mainThread())
                /**
                 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
                 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
                 * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
                 *      行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
                 *      可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
                 *      不要把计算工作放在 io() 中，可以避免创建不必要的线程。
                 * Schedulers.computation(): 计算所使用的 Scheduler。
                 *      这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
                 *      例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。
                 *      不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
                 * 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
                 */
                // observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
                //它可以多次指定线程,需要注意的是:observeOn() 指定的是它之后的操作所在的线程。
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    /**
                     *这里注意:用Subscriber或者Observer都是可以的。
                     * Observer会被封装为一个ObserverSubscriber对象。
                     * 而ObserverSubscriber对象是实现Subscriber接口的。
                     *
                     * * 选择 Observer 和 Subscriber 是完全一样的。它们的区别对于使用者来说主要有两点：
                     * 1.onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，
                     * 而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。
                     * 这是一个可选方法，默认情况下它的实现为空。
                     * 需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
                     * onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。
                     * 要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
                     * <p>
                     * 2.unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，
                     * 用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。
                     * 一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。
                     * unsubscribe() 这个方法很重要，因为在 subscribe() 之后，
                     * Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。
                     * 所以最好保持一个原则：
                     * 要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）
                     * 调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
                     */


                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart()总是在 subscribe 所发生的线程被调用，而不能指定线程。
                        //即: 调用subscribe()方法的线程与onStart()方法执行的线程为同一个线程
                        //注意此处并不是指subscribeOn()方法所指定的线程。
                        Log.i(TAG, "onStart()执行在" + Thread.currentThread().getName());
                        sb.append("onStart()执行在").append(Thread.currentThread().getName()).append("\r\n");
                        textView.setText(sb.toString());
                    }

                    @Override
                    public void onCompleted() {
                        sb.append("onCompleted()执行在").append(Thread.currentThread().getName()).append("\r\n");
                        textView.setText(sb.toString());

                        Log.i(TAG, "onCompleted()执行在" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext()执行在" + Thread.currentThread().getName());

                        sb.append("onNext()执行在").append(Thread.currentThread().getName()).append("\r\n");
                        textView.setText(sb.toString());

                    }
                });
        /**
         * 解除注册。不过在subscribe()方法执行后,Subscriber对象会被封装为
         * SafeSubscriber对象。该对象重写了onCompleted()方法。在该方法中,
         * 它会默认调用解除注册的方法。也就是说,正常情况下,不需要自己去做解注册。
         * 不过自己调用和自动调用的区别在于:自己调用时机随意。而自动调用是在事件执行完成之后。
         */
//        if (!subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
        /**
         * subscribe()方法会返回一个Subscription对象。方便取消注册。
         * OnSubscribe.call(Subscriber)这个方法是在subscribe()方法执行的时候开始执行的。
         * 具体可查看源码
         */

    }
}
