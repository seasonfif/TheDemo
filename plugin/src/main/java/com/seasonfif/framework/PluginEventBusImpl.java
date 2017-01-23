package com.seasonfif.framework;

import android.os.RemoteException;
import android.widget.Toast;
import com.seasonfif.dynamicplugin.IEventBusService;
import com.seasonfif.plugin.Entry;

/**
 * 创建时间：2017年01月20日18:27 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginEventBusImpl extends IEventBusService.Stub{

  @Override public String post(String className, String jsonStr) throws RemoteException {
    Toast.makeText(Entry.sPluginContext, "插件Toast显示主工程String参数", Toast.LENGTH_SHORT).show();
    return className + "  " +jsonStr;
  }
}
