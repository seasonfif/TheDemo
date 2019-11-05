package com.demo.activity;

import android.os.Bundle;

import com.demo.R;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TestSurfaceViewActivity extends BaseActivity{

    private static final String TAG = "TestSurfaceViewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_test_surface);

    }
}
