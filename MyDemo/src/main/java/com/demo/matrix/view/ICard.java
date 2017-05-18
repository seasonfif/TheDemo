package com.demo.matrix.view;

import com.demo.matrix.annotation.NestMode;

/**
 * 创建时间：2017年05月17日13:58 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public interface ICard {

  /**
   * 是否允许嵌套
   * @return
   */
  @NestMode
  int getNestMode();

  /**
   * 更新卡片
   */
  void update(String data);

  /**
   * 嵌套card，根据index
   * @param index
   * @param card
   */
  void addCard(int index, ICard card);
}
