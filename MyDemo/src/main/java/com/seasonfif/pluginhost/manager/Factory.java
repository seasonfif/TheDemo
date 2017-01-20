package com.seasonfif.pluginhost.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 创建时间：2017年01月19日18:00 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public final class Factory{

  /**
   * 插件的入口包名前缀
   * 在插件中，该包名不能混淆
   * 例如，Demo插件的入口类为：com.seasonfif.plugin.Entry
   *
   * @hide 内部框架使用
   */
  public static final String PLUGIN_ENTRY_PACKAGE_PREFIX = "com.seasonfif";

  /**
   * 插件的入口类
   * 在插件中，该名字不能混淆
   *
   * @hide 内部框架使用
   */
  public static final String PLUGIN_ENTRY_CLASS_NAME = "Entry";

  /**
   * 插件的入口类导出函数
   * 在插件中，该方法名不能混淆
   * 通过该函数创建IPlugin对象
   *
   * @hide 内部框架使用
   */
  public static final String PLUGIN_ENTRY_EXPORT_METHOD_NAME = "create";

  /**
   * 参数1：插件上下文，可通过它获取插件应用的资源等
   * 参数2：HOST的类加载器即主工程ClassLoader
   * 返回：插件 IPlugin.aidl
   *
   * @hide 内部框架使用
   */
  public static final Class<?> PLUGIN_ENTRY_EXPORT_METHOD_PARAMS[] = {
      Context.class, ClassLoader.class
  };

  /**
   * @hide 内部框架使用
   */
  public static IPluginManager sPluginManager;


  /**
   * 调用此接口会在当前进程加载插件,并实例话插件中相应的类然后然后返回此类
   *
   * @param name 插件名
   * @param className 需要查询的class的名称
   */
  public static Object queryObject(String name, String className) {
    return sPluginManager.queryObject(name, className);
  }

  /**
   * 调用此接口会在当前进程加载插件
   *
   * @param name 插件名
   * @param binder 需要查询的binder的名称（不要用IXXX.class.getName，因为不再建议keep IXXX类，IXXX有可能被混淆）
   */
  public static IBinder query(String name, String binder) {
    return sPluginManager.query(name, binder);
  }

  /**
   * 调用此接口会在指定进程加载插件
   *
   * @param name 插件名
   * @param binder 需要查询的binder的名称（不要用IXXX.class.getName，因为不再建议keep IXXX类，IXXX有可能被混淆）
   * @param process 是否在指定进程中启动
   */
  public static IBinder query(String name, String binder, int process) {
    return sPluginManager.query(name, binder, process);
  }

  public static ComponentName loadPluginActivity(Intent intent, String plugin, String target,
      int process) {
    return sPluginManager.loadPluginActivity(intent, plugin, target, process);
  }

  public static boolean startActivity(Context context, Intent intent, String plugin, String activity,
      int process) {
    return sPluginManager.startActivity(context, intent, plugin, activity, process);
  }

  public static boolean startActivityForResult(Activity context, Intent intent, int requestCode,
      String plugin, String activity, int process) {
    return sPluginManager.startActivityForResult(context, intent, requestCode, plugin, activity, process);
  }

  public static boolean startActivityForResult(Activity context, Intent intent, int requestCode,
      Bundle options, String plugin, String activity, int process) {
    return sPluginManager.startActivityForResult(context, intent, requestCode, options, plugin, activity, process);
  }

}
