package com.seasonfif.pluginhost.manager;

/**
 * 创建时间：2017年01月19日16:58 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PluginInfo {
  /**
   * 插件名
   */
  public String mName;
  /**
   * 插件在assert中的路径
   */
  public String mAssertPath;

  public PluginInfo(String name, String assertPath){
    this.mName = name;
    this.mAssertPath = assertPath;
  }
}
