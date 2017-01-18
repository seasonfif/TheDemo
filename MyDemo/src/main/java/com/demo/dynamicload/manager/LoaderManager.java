package com.demo.dynamicload.manager;

import android.content.Context;
import android.os.Environment;
import dalvik.system.DexClassLoader;
import java.io.File;

/**
 * 创建时间：2016年11月08日18:41 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class LoaderManager {

  private volatile static LoaderManager instance;
  private static DexClassLoader dexClassLoader;

  private LoaderManager (Context context){
    File source = new File(
        Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dynamicplugin-debug.apk");
    File optimizedDexOutputPath = context.getDir("dex", Context.MODE_PRIVATE);
    dexClassLoader = new DexClassLoader(source.getAbsolutePath(), optimizedDexOutputPath.getAbsolutePath(), null, context.getClassLoader().getParent());
  }

  public static LoaderManager getInstance(Context context){
    if (instance == null){
      synchronized (LoaderManager.class){
        if (instance == null){
          instance = new LoaderManager(context);
        }
      }
    }
    return instance;
  }

  public Class loadClass(String classFullName){
    Class clazz = null;
    try {
      clazz = dexClassLoader.loadClass(classFullName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return clazz;
  }
}
