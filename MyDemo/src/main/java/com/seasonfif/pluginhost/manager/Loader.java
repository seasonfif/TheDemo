package com.seasonfif.pluginhost.manager;

import android.content.Context;
import android.os.IBinder;
import com.seasonfif.dynamicplugin.IPlugin;
import java.lang.reflect.Method;

/**
 * 创建时间：2016年11月08日18:41 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class Loader {

  private String mPluginPath;
  private String mPluginName;
  PluginClassLoader mPluginClassLoader;
  Method mCreateMethod;
  PluginProxy mPluginProxy;
  private Context mPluginContext;

  static class PluginProxy {

    IPlugin mPlugin;

    PluginProxy(IBinder plugin){
      mPlugin = IPlugin.Stub.asInterface(plugin);
    }
  }

  protected Loader(Context context, String pluginName, String pluginPath){
    this.mPluginName = pluginName;
    this.mPluginPath = pluginPath;
    String optimizedDexOutputPath = context.getDir(Constant.LOCAL_PLUGIN_ODEX_SUB_DIR, 0).getPath();
    mPluginClassLoader = new PluginClassLoader(pluginPath, optimizedDexOutputPath, null, getClass().getClassLoader());
  }

  public boolean loadEntryMethod() {
    try {
      String className = Factory.PLUGIN_ENTRY_PACKAGE_PREFIX + "." + mPluginName + "." + Factory.PLUGIN_ENTRY_CLASS_NAME;
      Class<?> cls = mPluginClassLoader.loadClass(className);
      mCreateMethod = cls.getDeclaredMethod(Factory.PLUGIN_ENTRY_EXPORT_METHOD_NAME, Factory.PLUGIN_ENTRY_EXPORT_METHOD_PARAMS);
    } catch (Throwable e) {
      e.printStackTrace();

    }
    return mCreateMethod != null;
  }

  public boolean invoke() {
    try {
      IBinder binder;
      binder = (IBinder) mCreateMethod.invoke(null, mPluginContext, getClass().getClassLoader());
      if (binder == null){
        return false;
      }
      mPluginProxy = new PluginProxy(binder);
    } catch (Throwable e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

}
