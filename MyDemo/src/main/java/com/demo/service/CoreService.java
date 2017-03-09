package com.demo.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 创建时间：2017年03月09日14:56 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class CoreService extends Service{

  private static final String TAG = "CoreService";
  public int count;

  private Core.Stub mBinder = new Core.Stub() {
    @Override public int getCount() throws RemoteException {
      return count;
    }
  };

  @Override public IBinder onBind(Intent intent) {
    Log.e(TAG, "onBind");
    return mBinder;
  }

  @Override public void onRebind(Intent intent) {
    super.onRebind(intent);
    Log.e(TAG, "onRebind");
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

  @Override public void unbindService(ServiceConnection conn) {
    super.unbindService(conn);
    Log.e(TAG, "unbindService");
  }

  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestory");
  }
}
