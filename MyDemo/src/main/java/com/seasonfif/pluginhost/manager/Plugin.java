package com.seasonfif.pluginhost.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.IBinder;
import android.text.TextUtils;
import com.seasonfif.pluginhost.utills.FileUtil;
import java.io.File;
import java.lang.ref.WeakReference;
import java.security.Key;
import java.util.HashMap;

/**
 * 创建时间：2017年01月19日15:56 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Plugin {

  // 只加载Service/Activity/ProviderInfo信息（包含ComponentList）
  static final int LOAD_INFO = 0;

  // 加载LOAD_INFO和插件资源
  static final int LOAD_RESOURCES = 1;

  // 加载LOAD_INFO、LOAD_RESOURCES和Dex代码
  static final int LOAD_ALL = 2;

  private Object LOCK = new Object();

  /**
   *  插件路径缓存
   */
  static final HashMap<String, String> PLUGIN_NAME_2_FILENAME = new HashMap<String, String>();

  /**
   *  插件Resources缓存
   */
  static final HashMap<String, WeakReference<Resources>> FILENAME_2_RESOURCES = new HashMap<String, WeakReference<Resources>>();

  /**
   *  插件PackageInfo缓存
   */
  static final HashMap<String, WeakReference<PackageInfo>> FILENAME_2_PACKAGE_INFO = new HashMap<String, WeakReference<PackageInfo>>();

  /**
   *  插件ComponentList缓存
   */
  static final HashMap<String, WeakReference<ComponentList>> FILENAME_2_COMPONENT_LIST = new HashMap<>();

  private PluginInfo mInfo;
  private Context mContext;

  private Loader mLoader;

  static final PackageInfo queryCachedPackageInfo(String key){
    PackageInfo packageInfo = null;
    if (!TextUtils.isEmpty(key)){
      WeakReference<PackageInfo> ref = FILENAME_2_PACKAGE_INFO.get(key);
      if (ref != null){
        packageInfo = ref.get();
      }
      if (ref == null){
        FILENAME_2_PACKAGE_INFO.remove(key);
      }
    }
    return packageInfo;
  }

  static final Resources queryCachedResources(String key){
    Resources resources = null;
    if (!TextUtils.isEmpty(key)){
      synchronized (FILENAME_2_RESOURCES){
        WeakReference<Resources> ref = FILENAME_2_RESOURCES.get(key);
        if (ref != null){
          resources = ref.get();
        }
        if (resources == null){
          FILENAME_2_RESOURCES.remove(key);
        }
      }
    }
    return resources;
  }

  static final ComponentList queryCachedComponentList(String key){
    ComponentList cl = null;
    if (!TextUtils.isEmpty(key)){
      synchronized (FILENAME_2_COMPONENT_LIST){
        WeakReference<ComponentList> ref = FILENAME_2_COMPONENT_LIST.get(key);
        if (ref != null){
          cl = ref.get();
        }
        if (cl == null){
          FILENAME_2_COMPONENT_LIST.remove(key);
        }
      }
    }
    return cl;
  }

  static Plugin build(PluginInfo info){
    return new Plugin(info);
  }

  private Plugin(PluginInfo info){
    this.mInfo = info;
  }

  void attach(Context context){
    this.mContext = context;
  }

  boolean load(){
    boolean rc = false;
    synchronized (LOCK){
      rc = loadLocked();
    }
    return rc;
  }

  private final boolean loadLocked(){
    File dir = mContext.getDir(Constant.LOCAL_PLUGIN_SUB_DIR, 0);
    File odexdir = mContext.getDir(Constant.LOCAL_PLUGIN_ODEX_SUB_DIR, 0);
    String dstName = mInfo.mName + ".apk";
    FileUtil.quickExtractTo(mContext, mInfo.mAssertPath, dir.getAbsolutePath(), dstName, false, null, odexdir.getAbsolutePath());
    mLoader = new Loader(mContext, mInfo.mName, new File(dir, dstName).getAbsolutePath());

    if (!mLoader.loadDex()){
      return false;
    }

    if (mLoader.loadEntryMethod()){
      if(!mLoader.invoke()){
        return false;
      }
    }
    return true;
  }

  final Object queryObject(String name) {
    try {
      Class c = mLoader.mPluginClassLoader.loadClass(name);
      if (c!=null) {
        return c.newInstance();
      }
    } catch (Exception e) {

    }
    return null;
  }

  final IBinder query(String binder) {
    try {
      return mLoader.mPluginProxy.mPlugin.query(binder);
    } catch (Throwable e) {

    }
    return null;
  }
}
