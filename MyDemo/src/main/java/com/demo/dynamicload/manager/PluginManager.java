package com.demo.dynamicload.manager;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 创建时间：2017年01月19日17:20 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginManager {

  private volatile static PluginManager instance;
  static List<PluginInfo> mAll = new ArrayList();

  /**
   * 所有插件
   */
  static final HashMap<String, Plugin> mPlugins = new HashMap<String, Plugin>();

  public static void init(Context context){
    mAll.add(new PluginInfo(Constant.LOCAL_PLUGIN_1, Constant.LOCAL_PLUGIN_1_PATH));

    for (PluginInfo info : mAll){
      mPlugins.put(info.mName, Plugin.build(info));
    }

    Iterator it = mPlugins.entrySet().iterator();
    while (it.hasNext()){
      Map.Entry entry = (Map.Entry) it.next();
      Plugin plugin = (Plugin) entry.getValue();
      plugin.attach(context);
      plugin.load();
    }
  }

  public static PluginManager getInstance(){
    if (instance == null){
      synchronized (PluginManager.class){
        if (instance == null){
          instance = new PluginManager();
        }
      }
    }
    return instance;
  }

  public Plugin findPluginByName(String name){
    return mPlugins.get(name);
  }
}
