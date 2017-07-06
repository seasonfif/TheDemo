package com.homelink.ljrecyclerview;

import android.view.View;

import java.util.ArrayList;

/**
 * 创建时间：2017年07月03日21:07 <br>
 * 作者：zhangqiang <br>
 * 描述：Header、Footer
 */

public interface HeaderWrapper {

  /**
   * header
   */
  int VIEW_TYPE_HEADER = 0x10000000;

  /**
   * footer
   */
  int VIEW_TYPE_FOOTER = 0x20000000;

  /**
   * 添加header
   * @param headers
   */
  void setHeaderViews(ArrayList<View> headers);

  /**
   * 添加footer
   * @param footers
   */
  void setFooterViews(ArrayList<View> footers);

  /**
   * 获取header数量
   * @return
   */
  int getHeadersCount();

  /**
   * 获取footer数量
   * @return
   */
  int getFootersCount();

  void setEmptyView(View empty);
}
