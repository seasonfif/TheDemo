package com.demo.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.demo.LogicOperation.TestLogicActivity;
import com.demo.R;
import com.demo.dynamicload.ClassLoaderTest;
import com.demo.leakcanary.LeakAsyncTaskAndHandlerActivity;
import com.demo.provider.TestProviderActivity;
import com.demo.service.Core;
import com.demo.service.CoreService;
import com.demo.service.CoreUIService;
import com.demo.service.NotifyService;
import com.seasonfif.pluginhost.manager.PMF;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/9.
 */
public class MainActivity extends BaseActivity{

    Core core;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
        ((TextView)findViewById(R.id.vername)).setText(sdf.format(getLast()));
        findViewById(R.id.views).setOnClickListener(this);
        findViewById(R.id.viewgroup).setOnClickListener(this);
        findViewById(R.id.leakcanary).setOnClickListener(this);
        findViewById(R.id.logic).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.dynamic).setOnClickListener(this);
        findViewById(R.id.process).setOnClickListener(this);
        findViewById(R.id.provider).setOnClickListener(this);
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
    }

    private long getLast(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.views:
                goToOthers(TestViewActivity.class);
                //goToOthers(TestMarqueeActivity.class);
                break;
            case R.id.viewgroup:
                goToOthers(TestViewGroupActivity.class);
                break;
            case R.id.leakcanary:
                goToOthers(LeakAsyncTaskAndHandlerActivity.class);
                break;
            case R.id.logic:
                goToOthers(TestLogicActivity.class);
                Intent myServiceIntent=new Intent(this, NotifyService.class);
                this.startService(myServiceIntent);
                break;
            case R.id.share:
                goToOthers(ShareAcivity.class);
                break;
            case R.id.dynamic:
                //PMF.init(this);
                //PMF.attach();
                goToOthers(ClassLoaderTest.class);
                break;
            case R.id.process:
                if (core!= null) try {
                    Log.e("count", "" + core.getCount());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Intent intentUIService = new Intent(this, CoreUIService.class);
                startService(intentUIService);
                /*Intent intentNotifyService = new Intent(this, NotifyService.class);
                startService(intentNotifyService);*/
                /*Intent intentNotifyService = new Intent(this, NotifyService.class);
                bindService(intentNotifyService, new ServiceConnection() {
                    @Override public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override public void onServiceDisconnected(ComponentName name) {

                    }
                }, BIND_AUTO_CREATE);*/
                /*Intent intentCoreService = new Intent(this, CoreService.class);
                startService(intentCoreService);*/
                Intent intentCoreService = new Intent(this, CoreService.class);
                bindService(intentCoreService, new ServiceConnection() {
                    @Override public void onServiceConnected(ComponentName name, IBinder binder) {
                        core = Core.Stub.asInterface(binder);
                    }

                    @Override public void onServiceDisconnected(ComponentName name) {

                    }
                }, Service.BIND_AUTO_CREATE);
                break;
            case R.id.provider:
                goToOthers(TestProviderActivity.class);
                break;

        }
    }
}
