package com.seasonfif.dynamicplugin;

/**
 * 创建时间：2016年11月09日11:54 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class DynamicImpl implements DynamicInterface{
  @Override public String getString() {
    return "来自插件";
  }
}
