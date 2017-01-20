package com.seasonfif.framework;

/**
 * 创建时间：2017年01月20日11:00 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginFramework {

  static final Object LOCK = new Object();

  static boolean mInitialized;

  public static final boolean init(ClassLoader cls){
    synchronized (LOCK){
      boolean r = initLocked(cls);
      return r;
    }
  }

  private static boolean initLocked(ClassLoader cls) {
    if (mInitialized){
      return mInitialized;
    }

    mInitialized = true;
    return  mInitialized;
  }
}
