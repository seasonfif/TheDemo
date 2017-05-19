package com.seasonfif.matrix.card;

import com.seasonfif.matrix.annotation.NestMode;

/**
 * 创建时间：2017年05月17日13:58 <br>
 * 作者：zhangqiang <br>
 * 描述：card接口
 */

public interface ICard<T> {

  /**
   * 是否允许嵌套
   * @return {@link NestMode}
   */
  @NestMode
  int getNestMode();

  /**
   * 更新卡片
   */
  void update(T data);

  /**
   * 嵌套card，根据index
   *
   * @param index 当NestMode为{@link NestMode.NONE}时不回调
   *              当NestMode为{@link NestMode.AUTO}时，index=-1
   *              当NestMode为{@link NestMode.MANUAL}时，index为并列节点的index
   *
   * @param card 生成的card
   */
  void addCard(int index, ICard card);
}
