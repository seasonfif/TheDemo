package com.seasonfif.svcmanager;

import android.os.IBinder;
import android.os.RemoteException;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建时间：2017年04月07日18:34 <br>
 * 作者：zhangqiang <br>
 * 描述：以同步调用的方式获取一个服务实现的接口类
 */

public final class ServiceManager {

  private static final String TAG = "ServiceManager";

  private static IServiceHandle sServiceHandle;

  private static Map<String, SoftReference<IBinder>> sIBinderCache = new ConcurrentHashMap<>();

  public static void addService(String index, IBinder binder){
    sIBinderCache.put(index, new SoftReference<IBinder>(binder));
  }

  public static IBinder getService(String index){

    IBinder service= null;

    //local
    SoftReference<IBinder> ref = sIBinderCache.get(index);
    if (ref != null){
      service = ref.get();
      if (service != null){
        return service;
      }
    }

    IServiceHandle serviceHandle = getServiceHandle();
    if (serviceHandle == null) {
      return null;
    }

    try {
      service = serviceHandle.getService(index);

    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return service;
  }

  public static void removeService(String index){

  }

  public static IServiceHandle getServiceHandle() {


    return serviceHandle;
  }
}
