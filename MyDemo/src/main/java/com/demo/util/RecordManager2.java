package com.demo.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordManager2 {

    private static final String TAG = "RecordManager2";
    private static final int SENDTASK_MSG = 1;
    private static final int TIMEOUT_MSG = 2;

    private static Handler handler;

    private static long last;

    private static List<String> caches = Collections.synchronizedList(new ArrayList<String>());

    public static void init(){

        final HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper()){
                @Override
                public void handleMessage(Message msg) {

                    int what = msg.what;
                    switch (what){

                        case SENDTASK_MSG:
                            long time = System.currentTimeMillis();
                            if (System.currentTimeMillis() - last > 500){
                                last = time;
                                List<String> temp = new ArrayList<>(caches);
                                caches.removeAll(temp);
                                handleTask("normal", temp);
//                                handler.dump(new LogPrinter(Log.ERROR, "handler dump"), "dump");
                            }
                            break;

                        case TIMEOUT_MSG:

                            if (!caches.isEmpty()){
                                Log.e(Thread.currentThread().getName()+"超时回调", "timeout");
                                List<String> temp = new ArrayList<>(caches);
                                caches.removeAll(temp);
                                handleTask("timeout", temp);
                            }
                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + what);
                    }
                }
        };
    }

    public static void record(String r){

        Log.e(Thread.currentThread().getName()+"-接收任务", r);

        caches.add(r);

        handler.sendMessage(handler.obtainMessage(SENDTASK_MSG));


        if(!handler.hasMessages(TIMEOUT_MSG)){
            Log.e(Thread.currentThread().getName()+"-发送超时任务", r);
            handler.sendMessageDelayed(handler.obtainMessage(TIMEOUT_MSG), 2000);
        }
    }

    private static void handleTask(String msg, List<String> list){

        if (list.isEmpty()) return;

        Log.e(Thread.currentThread().getName()+"任务响应", msg);

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(",");
        }
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Log.e(Thread.currentThread().getName()+"处理任务::"+msg, sb.toString());
        list.clear();
        list = null;
    }

}
