package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.BuildConfig;
import com.demo.R;
import com.demo.leakcanary.LeakAsyncTaskAndHandlerActivity;

/**
 * Created by Administrator on 2016/7/9.
 */
public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TextView)findViewById(R.id.vername)).setText(BuildConfig.VERSION_NAME);
        findViewById(R.id.views).setOnClickListener(this);
        findViewById(R.id.viewgroup).setOnClickListener(this);
        findViewById(R.id.leakcanary).setOnClickListener(this);
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.views:
                goToOthers(TestViewActivity.class);
                break;
            case R.id.viewgroup:
                goToOthers(TestViewGroupActivity.class);
                break;
            case R.id.leakcanary:
                goToOthers(LeakAsyncTaskAndHandlerActivity.class);
        }
    }
}
