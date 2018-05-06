package com.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.demo.matrix.custome.CardFactory;
import com.seasonfif.matrix.engine.Matrix;
import com.seasonfif.pluginhost.manager.PMF;
import com.sogou.dnsguard.DNSGuard;
import com.sogou.dnsguard.Guardian;
import com.sogou.dnsguard.GuardianMap;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);
        PMF.init(this);
        PMF.attach();
        int pid = android.os.Process.myPid();
        Log.e(TAG, "MyApplication is oncreate====="+"pid="+pid);
        Matrix.init(new CardFactory());

        DNSGuard.guard(
                new GuardianMap()
                        .put("www.sogou.com", new Guardian(new String[]{"111.202.102.49","111.202.102.51"}, "111.202.\\d{1,3}.\\d{1,3}"))
                        .put("www.baidu.com", new Guardian(new String[]{"61.135.169.121"}, "61.135.169.\\d{1,3}")));
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
