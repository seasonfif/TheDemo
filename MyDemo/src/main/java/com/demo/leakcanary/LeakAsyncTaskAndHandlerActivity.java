package com.demo.leakcanary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;

import com.demo.R;
import com.demo.activity.BaseActivity;
import com.demo.activity.MainActivity;

/**
 * Created by Administrator on 2016/7/10.
 */
public class LeakAsyncTaskAndHandlerActivity extends BaseActivity {

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void setView() {
        setContentView(R.layout.activity_leak);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_task).setOnClickListener(this);
        findViewById(R.id.btn_handler).setOnClickListener(this);
        findViewById(R.id.btn_imm).setOnClickListener(this);
    }

    private void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(10000);
                return null;
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_task:
                startAsyncTask();
                break;
            case R.id.btn_handler:
                handler.sendEmptyMessageDelayed(0, 10000);
                goToOthers(MainActivity.class);
                finish();
                break;
            case R.id.btn_imm:
                goToOthers(Imm.class);
        }
    }
}
