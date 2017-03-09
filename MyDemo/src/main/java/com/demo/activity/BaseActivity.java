package com.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.demo.R;

/**
 * Created by Administrator on 2016/7/9.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{

    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setView();
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        init();
    }

    private void init() {
        intent = new Intent();
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.title).setOnClickListener(this);
        findViewById(R.id.menu).setOnClickListener(this);
    }

    public abstract void setView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void goToOthers(Class<?> clazz) {
        goToOthersWithBundle(clazz, null);
    }

    protected void goToOthersWithBundle(Class<?> clazz, Bundle bundle) {
        intent.setClass(getApplicationContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
        }
    }
}
