package com.demo.matrix.custome;

import com.demo.matrix.view.ICard;
import com.demo.matrix.view.ICardFactory;

/**
 * 创建时间：2017年05月17日14:08 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class ImageCardFactory implements ICardFactory {
  @Override public ICard createCard() {
    return new ImageCard();
  }
}
