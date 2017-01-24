package com.seasonfif.pluginhost.manager;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 创建时间：2017年01月20日13:32 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PmBase {

  /**
   *
   */
  private final Context mContext;

  /**
   *
   */
  IPluginManager mLocal;

  /**
   *
   */
  IPluginActivityManager mInternal;

  private List<PluginInfo> mAll = new ArrayList();

  /**
   * 所有插件
   */
  private final HashMap<String, Plugin> mPlugins = new HashMap<>();

  PmBase(Context context){
    this.mContext = context;

    mLocal = new PmLocalImpl(context, this);
    mInternal = new PmInternalImpl(this);
  }

  void init(){
    mAll.add(new PluginInfo(Constant.LOCAL_PLUGIN_1, Constant.LOCAL_PLUGIN_1_PATH));
    for (PluginInfo info : mAll){
      mPlugins.put(info.mName, Plugin.build(info));
    }
  }

  public void attach() {
    Iterator it = mPlugins.entrySet().iterator();
    while (it.hasNext()){
      Map.Entry entry = (Map.Entry) it.next();
      Plugin plugin = (Plugin) entry.getValue();
      plugin.attach(mContext);
      plugin.load();
    }
  }

  final Plugin loadDexPlugin(String pluginName){
    synchronized (mPlugins){
      Plugin p = mPlugins.get(pluginName);
      return p;
    }
  }
}
