package com.seasonfif.pluginhost.manager;

import android.content.Context;
import android.os.IBinder;
import com.seasonfif.pluginhost.utills.FileUtil;
import java.io.File;

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

  private PluginInfo mInfo;
  private Context mContext;

  private Loader mLoader;

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
