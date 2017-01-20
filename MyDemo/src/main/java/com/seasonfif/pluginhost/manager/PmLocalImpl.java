package com.seasonfif.pluginhost.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 创建时间：2017年01月20日11:59 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PmLocalImpl implements IPluginManager {

  /**
   *
   */
  Context mContext;

  /**
   *
   */
  PmBase mPluginMgr;

  PmLocalImpl(Context context, PmBase pm){
    this.mContext = context;
    this.mPluginMgr = pm;
  }

  @Override public Object queryObject(String name, String className) {
    Plugin p = mPluginMgr.loadDexPlugin(name);
    if (p == null){
      return null;
    }
    return p.queryObject(className);
  }

  @Override public IBinder query(String name, String binder) {
    Plugin p = mPluginMgr.loadDexPlugin(name);
    if (p == null){
      return null;
    }
    return p.query(binder);
  }

  @Override public IBinder query(String name, String binder, int process) {
    return null;
  }

  @Override public ComponentName loadPluginActivity(Intent intent, String plugin, String target,
      int process) {
    return null;
  }

  @Override
  public boolean startActivity(Context context, Intent intent, String plugin, String activity,
      int process) {
    return false;
  }

  @Override public boolean startActivityForResult(Activity context, Intent intent, int requestCode,
      String plugin, String activity, int process) {
    return false;
  }

  @Override public boolean startActivityForResult(Activity context, Intent intent, int requestCode,
      Bundle options, String plugin, String activity, int process) {
    return false;
  }
}
