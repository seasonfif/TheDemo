package com.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.seasonfif.pluginhost.manager.PMF;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);

        int pid = android.os.Process.myPid();
        Log.e(TAG, "MyApplication is oncreate====="+"pid="+pid);
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
