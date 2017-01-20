package com.demo;

import android.app.Application;
import android.content.Context;
import com.seasonfif.pluginhost.manager.PMF;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MyApplication extends Application {

    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
