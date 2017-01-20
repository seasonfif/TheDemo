package com.seasonfif.plugin;

import android.os.IBinder;
import android.os.RemoteException;
import com.seasonfif.dynamicplugin.IPlugin;
import com.seasonfif.framework.PluginEventBusImpl;
import com.seasonfif.framework.PluginFramework;

/**
 * 创建时间：2017年01月18日16:59 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Plugin extends IPlugin.Stub {

  public Plugin(ClassLoader cls) {
    PluginFramework.init(cls);
  }

  @Override public IBinder query(String name) throws RemoteException {
    return new PluginEventBusImpl();
  }
}
