package com.demo.dynamicload.manager;

/**
 * 创建时间：2017年01月19日18:00 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Factory {

  public static Class<?> loadClass(String pluginName, String classFullName){
    return PluginManager.getInstance().findPluginByName(pluginName).loadClass(classFullName);
  }
}
