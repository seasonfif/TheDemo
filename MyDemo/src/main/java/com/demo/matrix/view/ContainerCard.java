package com.demo.matrix.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 创建时间：2017年05月17日18:27 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class ContainerCard extends LinearLayout implements ICard{

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

  }

  @Override public boolean allowGroup() {
    return true;
  }

  @Override public void update(String data) {

  }

  @Override public void addCard(int index, ICard card) {

  }
}
