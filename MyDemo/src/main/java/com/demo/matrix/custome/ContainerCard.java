package com.demo.matrix.custome;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.demo.R;
import com.demo.matrix.view.ICard;
import com.demo.matrix.annotation.NestMode;

/**
 * 创建时间：2017年05月17日18:27 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class ContainerCard extends LinearLayout implements ICard {

  public static final int TYPE_CONTAINER_CARD = 0x0000;

  public ContainerCard(Context context) {
    this(context, null);
  }

  public ContainerCard(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ContainerCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setOrientation(VERTICAL);
    setBackgroundColor(Color.YELLOW);
  }

  @Override public int getNestMode() {
    return NestMode.COMPLY;
  }

  @Override public void update(String data) {

  }

  @Override public void addCard(int index, ICard card) {
    LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    lp.topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
    lp.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
    lp.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
    this.addView((View) card, lp);
  }
}
