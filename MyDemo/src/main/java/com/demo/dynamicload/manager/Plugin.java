package com.demo.dynamicload.manager;

import android.content.Context;
import com.demo.dynamicload.utills.FileUtil;
import java.io.File;

/**
 * 创建时间：2017年01月19日15:56 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Plugin {

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
    File dir = mContext.getDir(Constant.LOCAL_PLUGIN_SUB_DIR, 0);
    File odexdir = mContext.getDir(Constant.LOCAL_PLUGIN_ODEX_SUB_DIR, 0);
    String dstName = mInfo.mName + ".apk";
    FileUtil.quickExtractTo(mContext, mInfo.mAssertPath, dir.getAbsolutePath(), dstName, false, null, odexdir.getAbsolutePath());
    mLoader = new Loader(mContext, dstName, new File(dir, dstName).getAbsolutePath());
    return true;
  }

  public Class<?> loadClass(String name){
    try {
      Class cls = mLoader.mPluginClassLoader.loadClass(name);
      return cls;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
