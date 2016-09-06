package com.customviewcollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.customviewcollection.ui.CustomTextViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View btn_custom1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_custom1 = findViewById(R.id.btn_custom1);
        btn_custom1.setOnClickListener(this);
    }

    private void start(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_custom1:
                start(CustomTextViewActivity.class);
                break;
        }
    }
}
