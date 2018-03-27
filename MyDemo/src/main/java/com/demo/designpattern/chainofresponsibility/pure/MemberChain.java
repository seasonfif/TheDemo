package com.demo.designpattern.chainofresponsibility.pure;

import android.util.Log;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public class MemberChain implements ChainHandler{

  private static final String TAG = "MemberChain";
  private ChainHandler handler;

  @Override public void setChainHandler(ChainHandler handler) {
    this.handler = handler;
  }

  @Override public ChainHandler getChain() {
    return handler;
  }

  @Override public void handle(int money) {
    if (getChain() != null){
      getChain().handle(money);
    }else{
      Log.e(TAG, "上级不在无法处理");
    }
  }
}
