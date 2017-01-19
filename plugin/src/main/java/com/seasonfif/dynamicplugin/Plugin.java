package com.seasonfif.dynamicplugin;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

/**
 * 创建时间：2017年01月18日16:59 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Plugin extends IPlugin.Stub{

  public Plugin(Context context, ClassLoader cls) {
    Toast.makeText(context, "插件Toast", Toast.LENGTH_SHORT).show();
  }

  @Override public IBinder query(String name) throws RemoteException {
    return null;
  }
}
