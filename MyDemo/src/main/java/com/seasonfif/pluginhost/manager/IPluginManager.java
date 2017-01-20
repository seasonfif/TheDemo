package com.seasonfif.pluginhost.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 创建时间：2017年01月20日11:56 <br>
 * 作者：zhangqiang <br>
 * 描述：负责插件和插件之间的interface互通
 */

public interface IPluginManager {

  /**
   * 通过Intent的extra参数指出需要在指定进程中启动
   * 只能启动standard的Activity，不能启动singleTask、singleInstance等
   * 不指定时，自动分配插件进程，即PROCESS_AUTO
   */
  public static final String KEY_PROCESS = "process";

  /**
   * 自动分配插件进程
   */
  public static final int PROCESS_AUTO = Integer.MIN_VALUE;

  /**
   * UI进程
   */
  public static final int PROCESS_UI = -1;

  /**
   * 常驻进程
   */
  public static final int PROCESS_PERSIST = -2;

  /**
   * @param name 插件名
   * @param className 需要查询的类
   * @return
   */
  Object queryObject(String name, String className);

  /**
   * @param name 插件名
   * @param binder 需要查询的binder的类
   * @return
   */
  IBinder query(String name, String binder);

  /**
   * @param name 插件名
   * @param process 需要查询的binder的类
   * @param process 是否在指定进程中启动
   * @return
   */
  IBinder query(String name, String binder, int process);


  /**
   * 加载插件Activity，在startActivity之前调用
   * @param intent
   * @param plugin 插件名
   * @param target 目标Service名，如果传null，则取获取到的第一个
   * @param process 是否在指定进程中启动
   * @return
   */
  ComponentName loadPluginActivity(Intent intent, String plugin, String target, int process);

  /**
   * 启动一个插件中的activity，如果插件不存在会触发下载界面
   * @param context 应用上下文或者Activity上下文
   * @param intent
   * @param plugin 插件名
   * @param activity 待启动的activity类名
   * @param process 是否在指定进程中启动
   * @return 插件机制层是否成功，例如没有插件存在、没有合适的Activity坑
   */
  boolean startActivity(Context context, Intent intent, String plugin, String activity,
      int process);

  /**
   * 启动一个插件中的activity，如果插件不存在会触发下载界面
   * @param context Activity上下文
   * @param intent
   * @param plugin 插件名
   * @param activity 待启动的activity类名
   * @param process 是否在指定进程中启动
   * @return 插件机制层是否成功，例如没有插件存在、没有合适的Activity坑
   */
  boolean startActivityForResult(Activity context, Intent intent, int requestCode, String plugin, String activity,
      int process);

  boolean startActivityForResult(Activity context, Intent intent, int requestCode, Bundle options, String plugin, String activity,
      int process);

}
