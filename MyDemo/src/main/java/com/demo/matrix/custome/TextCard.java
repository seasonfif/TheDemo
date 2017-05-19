package com.demo.matrix.custome;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.card.ICard;

/**
 * 创建时间：2017年05月19日15:32 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
@CardModel(CardBean.class)
public class TextCard extends TextView implements ICard{

  public static final int TYPE_TEXT_CARD = 0x0002;

  public TextCard(Context context) {
    this(context, null);
  }

  public TextCard(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TextCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setBackgroundColor(Color.BLUE);
  }

  @Override public int getNestMode() {
      return NestMode.MANUAL;
  }

  @Override
  public void update(Object data) {
    if (data != null){
    }
  }

  @Override public void addCard(int index, ICard card) {

  }
}
