package com.demo.dynamicload.manager;

import dalvik.system.DexClassLoader;

/**
 * 创建时间：2017年01月19日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginClassLoader extends DexClassLoader{

  private ClassLoader mMainClassLoader;

  /**
   * 构造插件ClassLoader
   * @param dexPath 需要装载的APK或者Jar文件的路径。包含多个路径用File.pathSeparator间隔开,在Android上默认是 “:”
   * @param optimizedDirectory 优化后的dex文件存放目录，不能为null
   * @param libraryPath 目标类中使用的C/C++库so文件的路径,每个目录用File.pathSeparator间隔开; 可以为null
   * @param mainClassLoader 该类装载器的父装载器，一般用当前执行类的装载器
   */
  public PluginClassLoader(String dexPath, String optimizedDirectory, String libraryPath,
      ClassLoader mainClassLoader) {
    super(dexPath, optimizedDirectory, libraryPath, mainClassLoader.getParent());
    mMainClassLoader = mainClassLoader;
  }

  @Override protected Class<?> loadClass(String className, boolean resolve)
      throws ClassNotFoundException {
    Class<?> clz = null;
    // 先用插件的ClassLoader加载，加载不到然后再用主工程的ClassLoader类加载
    try {
      clz = super.loadClass(className, resolve);
    }catch (Throwable e){
      e.getMessage();
    }
    if (clz == null){
      clz = mMainClassLoader.loadClass(className);
    }
    return clz;
  }

  @Override public String toString() {
    return getClass().getName() + "[mainClassLoader" + mMainClassLoader.toString() + "]";
  }
}
