package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.widget.Toast;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;

/**
 * Created by Magina on 16/10/8.
 * 类功能介绍:
 * 初步学习Scene以及Transition的使用。
 */

public class SecondSceneActivity extends BaseActivity {

    private static final String TAG = "SceneActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_second);
        //其中,有一个是通过xml配置的
        ViewCompat.setTransitionName(findViewById(R.id.img2), "transitionName2");
        ViewCompat.setTransitionName(findViewById(R.id.img3), "transitionName3");

        Transition transition = getWindow().getSharedElementEnterTransition();
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Toast.makeText(SecondSceneActivity.this, "transition结束了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

    }
}
