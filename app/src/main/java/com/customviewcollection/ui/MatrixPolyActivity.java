package com.customviewcollection.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioGroup;

import com.customviewcollection.BaseActivity;
import com.customviewcollection.R;
import com.customviewcollection.view.MatrixPolyView;

/**
 * Created by Magina on 1/3/17.
 * 类功能介绍:
 */

public class MatrixPolyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_poly);

        final MatrixPolyView poly = (MatrixPolyView) findViewById(R.id.mpv);

        RadioGroup group = (RadioGroup) findViewById(R.id.group);
        assert group != null;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.point0:
                        poly.setTestPoint(0);
                        break;
                    case R.id.point1:
                        poly.setTestPoint(1);
                        break;
                    case R.id.point2:
                        poly.setTestPoint(2);
                        break;
                    case R.id.point3:
                        poly.setTestPoint(3);
                        break;
                    case R.id.point4:
                        poly.setTestPoint(4);
                        break;
                }
            }
        });
    }
}
