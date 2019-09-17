package com.demo.util;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RecordManager {

    private static final String TAG = "RecordManager";

    private static volatile LinkedBlockingQueue<String> sRecordQueue = new LinkedBlockingQueue<>(5);

    public static volatile boolean flag;

    public static void init(){

        new PollThread("RecordManager-t1").start();

        new PollThread("RecordManager-t2").start();
    }

    public static void record(String r){

        try {
            boolean b = sRecordQueue.offer(r, 2000, TimeUnit.MILLISECONDS);
            if (!b){
                Log.e(TAG, "入队失败"+r);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class PollThread extends Thread{

        public PollThread(String name){
            super(name);
        }

        @Override
        public void run() {
            while(true){
                if (flag && !sRecordQueue.isEmpty()){
                    Log.e(getName(), "队列数量"+sRecordQueue.size());
                    String r = null;
                    try {
                        r = sRecordQueue.poll(2000, TimeUnit.MILLISECONDS);
                        Log.e(getName()+"正在处理", "任务："+r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    /*try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
    }

}
