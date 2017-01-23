package com.seasonfif.plugin;

import android.content.Context;
import android.os.IBinder;

/**
 * 创建时间：2017年01月18日18:19 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Entry {

  public static Context sPluginContext;
  public static ClassLoader sClassLoader;

  public static final IBinder create(Context context, ClassLoader cls){
    sPluginContext = context;
    sClassLoader = cls;
    Plugin plugin= new Plugin(cls);
    return plugin;
  }
}
