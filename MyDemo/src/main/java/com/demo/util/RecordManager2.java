package com.demo.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public class RecordManager2 {

    private static final String TAG = "RecordManager2";

    private static Handler handler;

    public static void init(){

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper()){
                @Override
                public void handleMessage(Message msg) {
                    String r = (String) msg.obj;
                    Log.e(Thread.currentThread().getName()+"正在处理", "任务："+r);
                }
        };
    }

    public static void record(String r){

        Message msg = Message.obtain();
        msg.obj = r;
        handler.sendMessage(msg);
    }

}
