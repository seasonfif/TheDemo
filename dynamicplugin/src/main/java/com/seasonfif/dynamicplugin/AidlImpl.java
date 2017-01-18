package com.seasonfif.dynamicplugin;

import android.os.RemoteException;
import android.util.Log;

/**
 * 创建时间：2017年01月18日16:59 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class AidlImpl extends IMyAidlInterface.Stub{

  @Override
  public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
      String aString) throws RemoteException {
    Log.e("AidlImpl", aString);
  }
}
