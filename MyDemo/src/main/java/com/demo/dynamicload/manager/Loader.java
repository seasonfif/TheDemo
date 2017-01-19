package com.demo.dynamicload.manager;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/**
 * 创建时间：2016年11月08日18:41 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class Loader {

  private String mPluginPath;
  private String mPluginName;
  PluginClassLoader mPluginClassLoader;

  protected Loader(Context context, String pluginName, String pluginPath){
    this.mPluginName = pluginName;
    this.mPluginPath = pluginPath;
    String optimizedDexOutputPath = context.getDir(Constant.LOCAL_PLUGIN_ODEX_SUB_DIR, 0).getPath();
    mPluginClassLoader = new PluginClassLoader(pluginPath, optimizedDexOutputPath, null, getClass().getClassLoader());
  }
}
