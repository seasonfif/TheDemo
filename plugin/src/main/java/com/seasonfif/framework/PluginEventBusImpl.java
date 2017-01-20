package com.seasonfif.framework;

import android.os.RemoteException;
import com.seasonfif.dynamicplugin.IEventBusService;

/**
 * 创建时间：2017年01月20日18:27 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginEventBusImpl extends IEventBusService.Stub{

  @Override public String post(String className, String jsonStr) throws RemoteException {
    return className + "  " +jsonStr;
  }
}
