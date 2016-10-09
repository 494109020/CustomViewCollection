package com.customviewcollection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;

/**
 * Created by Magina on 16/10/8.
 * 类功能介绍:
 * 初步学习Scene以及Transition的使用。
 */

public class SceneActivity extends BaseActivity {

    private static final String TAG = "SceneActivity";
    private FrameLayout flContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        flContainer = (FrameLayout) findViewById(R.id.fl_container);

        /**
         * 在使用Scene的时候,要做动画效果的view记得id设置为一样的。
         */

        final Scene scene1 = Scene.getSceneForLayout(flContainer, R.layout.layout_scene1, this);
        final Scene scene2 = Scene.getSceneForLayout(flContainer, R.layout.layout_scene2, this);

        final Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.transition_set);
//        final Transition transition = new Fade();

        scene1.setEnterAction(new Runnable() {
            @Override
            public void run() {
                View img = flContainer.findViewById(R.id.img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TransitionManager.go(scene2, transition);
                    }
                });
                Log.i(TAG, "Scene1进入了");
            }
        });

        scene1.setExitAction(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Scene1退出了");
            }
        });

        scene2.setEnterAction(new Runnable() {
            @Override
            public void run() {
                View img = flContainer.findViewById(R.id.img1);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        TransitionManager.go(scene1, transition);
//                        startActivityWithShareElement();
                        startActivityWithShareElements();

                    }
                });
                Log.i(TAG, "Scene2进入了");
            }
        });

        scene2.setExitAction(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Scene2退出了");
            }
        });

        TransitionManager.go(scene1, transition);

    }

    /**
     * Activity间的单元素共享
     * 界面间的元素共享可以通过对指定的View设置该属性android:transitionName=""
     * 不过这要求两个页面间该属性指定的名称是相同的。
     */
    private void startActivityWithShareElement() {
        Intent intent = new Intent(SceneActivity.this, SecondSceneActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SceneActivity.this, flContainer.findViewById(R.id.img1), "transitionName1");
        startActivity(intent, optionsCompat.toBundle());
    }

    /**
     * Activity间的多元素共享
     * 这是另一种方式,这种方式可以完成元素共享。它可以不在xml中配置该属性android:transitionName=""
     * 不过要求在接收页面对 对应的View设置transitionName
     * {@link android.support.v4.view.ViewCompat#setTransitionName(View, String)}
     * 这种方式也是要求一一对应的。
     */
    private void startActivityWithShareElements() {
        Intent intent = new Intent(SceneActivity.this, SecondSceneActivity.class);

        Pair<View, String> pair1 = new Pair<>(flContainer.findViewById(R.id.img1), "transitionName1");
        Pair<View, String> pair2 = new Pair<>(flContainer.findViewById(R.id.img2), "transitionName2");
        Pair<View, String> pair3 = new Pair<>(flContainer.findViewById(R.id.img3), "transitionName3");

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SceneActivity.this, pair1, pair2, pair3);
        startActivity(intent, optionsCompat.toBundle());
    }
}
