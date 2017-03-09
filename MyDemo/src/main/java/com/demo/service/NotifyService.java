package com.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 创建时间：2017年03月09日12:19 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class NotifyService extends Service{

  public int count;
  private static final String TAG = "NotifyService";

  @Override public IBinder onBind(Intent intent) {
    Log.e(TAG, "onBind");
    return null;
  }

  @Override public void onCreate() {
    super.onCreate();
    Log.e(TAG, "onCreate");
    new Thread(new Runnable() {
      public void run() {
        while (true){
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {

          }
          count++;
          Log.e(TAG, "Count is" + count);
        }
      }
    }).start();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.e(TAG, "onStartCommand");
    return START_STICKY;
  }
  @Override
  public void onDestroy() {
    Log.e(TAG,"onDestroy");
  }
}
