package com.demo.util;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RecordManager {

    private static final String TAG = "RecordManager";

    private static volatile ConcurrentLinkedQueue<String> sRecordQueue = new ConcurrentLinkedQueue();

    public static void init(){

        new PollThread("RecordManager-t1").start();

        new PollThread("RecordManager-t2").start();
    }

    public static void record(String r){
        sRecordQueue.offer(r);
    }


    static class PollThread extends Thread{

        public PollThread(String name){
            super(name);
        }

        @Override
        public void run() {
            while(true){
                if (!sRecordQueue.isEmpty()){
                    Log.e(getName(), "队列数量"+sRecordQueue.size());
                    String r = sRecordQueue.poll();
                    if (null != r){
                        for (int i = 0; i < 5; i++) {
                            Log.e(getName()+"正在处理", r);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

}
