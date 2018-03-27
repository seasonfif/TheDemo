package com.demo.designpattern.chainofresponsibility.pure;

import android.util.Log;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public class CEOChain implements ChainHandler {

  private static final String TAG = "CEOChain";

  private ChainHandler handler;

  @Override public void setChainHandler(ChainHandler handler) {
    this.handler = handler;
  }

  @Override public ChainHandler getChain() {
    return handler;
  }

  @Override public void handle(int money) {
    Log.e(TAG, "已到最高层处理" + money);
  }
}
