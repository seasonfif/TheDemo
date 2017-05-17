package com.demo.matrix.custome;

import com.demo.matrix.view.ICard;

/**
 * 创建时间：2017年05月17日13:55 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class ImageCard implements ICard {

  public static final int TYPE_IMAGE_CARD = 0x0001;

  @Override public boolean allowGroup() {
    return false;
  }

  @Override public void update(String data) {

  }

  @Override public void addCard(int index, ICard card) {

  }
}
