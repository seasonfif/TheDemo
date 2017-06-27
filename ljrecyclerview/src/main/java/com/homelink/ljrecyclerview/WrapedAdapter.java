package com.homelink.ljrecyclerview;

/**
 * 创建时间：2017年06月27日21:25 <br>
 * 作者：zhangqiang <br>
 * 描述：包装adapter需要实现的接口
 */

public interface WrapedAdapter {

  void updateItem(int position, Object obj);

  void insertItem(int position, Object obj);

  Object removeItem(int position);
}
