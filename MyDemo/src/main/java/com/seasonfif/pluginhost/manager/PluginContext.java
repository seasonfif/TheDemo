package com.seasonfif.pluginhost.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;

/**
 * 创建时间：2017年01月22日14:49 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginContext extends ContextThemeWrapper{

  private final ClassLoader mPluginClassLoader;
  private final Resources mPluginResources;
  private final String mPluginName;
  private final Loader mLoader;

  public PluginContext(Context base, int themeres, ClassLoader cl, Resources r, String plugin, Loader loader) {
    super(base, themeres);

    mPluginClassLoader = cl;
    mPluginResources = r;
    mPluginName = plugin;
    mLoader = loader;
  }

  @Override public ClassLoader getClassLoader() {
    if (mPluginClassLoader != null){
      return mPluginClassLoader;
    }
    return super.getClassLoader();
  }

  @Override public Resources getResources() {
    if (mPluginResources != null){
      return mPluginResources;
    }
    return super.getResources();
  }

  @Override public AssetManager getAssets() {
    if (mPluginResources != null){
      return mPluginResources.getAssets();
    }
    return super.getAssets();
  }
}
