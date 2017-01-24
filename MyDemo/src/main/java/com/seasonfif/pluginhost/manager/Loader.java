package com.seasonfif.pluginhost.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.IBinder;
import com.demo.MyApplication;
import com.seasonfif.dynamicplugin.IPlugin;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * 创建时间：2016年11月08日18:41 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class Loader {

  private Context mContext;
  private String mPluginPath;
  private String mPluginName;
  PluginClassLoader mPluginClassLoader;
  Method mCreateMethod;
  PluginProxy mPluginProxy;

  PackageInfo mPackageInfo;
  ComponentList mComponentList;
  Resources mPluginResources;

  private Context mPluginContext;

  static class PluginProxy {

    IPlugin mPlugin;

    PluginProxy(IBinder plugin){
      mPlugin = IPlugin.Stub.asInterface(plugin);
    }
  }

  protected Loader(Context context, String pluginName, String pluginPath){
    this.mContext = context;
    this.mPluginName = pluginName;
    this.mPluginPath = pluginPath;
    String optimizedDexOutputPath = context.getDir(Constant.LOCAL_PLUGIN_ODEX_SUB_DIR, 0).getPath();
    mPluginClassLoader = new PluginClassLoader(pluginPath, optimizedDexOutputPath, null, getClass().getClassLoader());
  }

  final boolean loadDex(){
    PackageManager pm = mContext.getPackageManager();

    mPackageInfo = Plugin.queryCachedPackageInfo(mPluginPath);
    if (mPackageInfo == null){
      mPackageInfo =  pm.getPackageArchiveInfo(mPluginPath,
          PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES | PackageManager.GET_PROVIDERS | PackageManager.GET_RECEIVERS | PackageManager.GET_META_DATA);
      if (mPackageInfo == null || mPackageInfo.applicationInfo == null){
        mPackageInfo = null;
        return false;
      }

      mPackageInfo.applicationInfo.sourceDir = mPluginPath;
      mPackageInfo.applicationInfo.publicSourceDir = mPluginPath;

      synchronized (Plugin.PLUGIN_NAME_2_FILENAME){
        Plugin.PLUGIN_NAME_2_FILENAME.put(mPluginName, mPluginPath);
      }

      synchronized (Plugin.FILENAME_2_PACKAGE_INFO){
        Plugin.FILENAME_2_PACKAGE_INFO.put(mPluginPath, new WeakReference<PackageInfo>(mPackageInfo));
      }
    }

    mComponentList = Plugin.queryCachedComponentList(mPluginPath);
    if (mComponentList == null){
      mComponentList = new ComponentList(mPackageInfo);
      synchronized (Plugin.FILENAME_2_COMPONENT_LIST){
        Plugin.FILENAME_2_COMPONENT_LIST.put(mPluginPath, new WeakReference<ComponentList>(mComponentList));
      }
    }

    mPluginResources = Plugin.queryCachedResources(mPluginPath);
    if (mPluginResources == null){
      try {
        mPluginResources = pm.getResourcesForApplication(mPackageInfo.applicationInfo);
      } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      if (mPluginResources == null){
        return false;
      }

      synchronized (Plugin.FILENAME_2_RESOURCES){
        Plugin.FILENAME_2_RESOURCES.put(mPluginPath, new WeakReference<Resources>(mPluginResources));
      }
    }

    mPluginContext = new PluginContext(mContext,
        android.R.style.Theme, mPluginClassLoader, mPluginResources, mPluginName, this);
    return true;
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
