package com.homelink.ljrecyclerview;

/**
 * 创建时间：2017年07月03日21:16 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public interface PaginationWrapper {

  /**
   * “加载更多”的viewtype
   */
  int VIEW_TYPE_LOADMORE = 0x30000000;

  /**
   * 下拉刷新
   */
  void refresh();

  /**
   * 上拉加载
   */
  void loadMore();

  boolean shouldLoadMore();
}
