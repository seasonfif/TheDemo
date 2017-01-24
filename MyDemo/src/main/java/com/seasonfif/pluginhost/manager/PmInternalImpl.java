package com.seasonfif.pluginhost.manager;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.json.JSONArray;

/**
 * 创建时间：2017年01月24日17:52 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PmInternalImpl implements IPluginActivityManager {

  /**
   *
   */
  PmBase mPluginMgr;

  PmInternalImpl(PmBase pm){
    this.mPluginMgr = pm;
  }

  @Override public boolean startActivity(Activity activity, Intent intent) {

    String pluginName = intent.getStringExtra(IPluginManager.KEY_PLUGIN);
    String activityName = intent.getStringExtra(IPluginManager.KEY_ACTIVITY);

    return Factory.startActivity(activity, intent, pluginName, activityName, 0);
  }

  @Override
  public boolean startActivity(Context context, Intent intent, String plugin, String activity,
      int process, boolean download) {

    ComponentName cn = mPluginMgr.mLocal.loadPluginActivity(intent, plugin, activity, process);
    if (cn == null){
      return false;
    }
    intent.setComponent(cn);
    context.startActivity(intent);

    return true;
  }

  @Override public Context createActivityContext(Activity activity, Context newBase) {
    return null;
  }

  @Override public void handleActivityCreateBefore(Activity activity, Bundle savedInstanceState) {

  }

  @Override public void handleActivityCreate(Activity activity, Bundle savedInstanceState) {

  }

  @Override public void handleActivityDestroy(Activity activity) {

  }

  @Override public void handleRestoreInstanceState(Activity activity, Bundle savedInstanceState) {

  }

  @Override public void handleServiceCreate(Service service) {

  }

  @Override public void handleServiceDestroy(Service service) {

  }

  @Override public JSONArray fetchPlugins(String name) {
    return null;
  }

  @Override
  public boolean registerDynamicClass(String className, String plugin, String type, String target) {
    return false;
  }
}
