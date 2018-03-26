package com.demo.designpattern.chainofresponsibility;

import android.util.Log;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public class VPChain implements ChainHandler {

  private static final String TAG = "VPChain";

  private ChainHandler handler;

  @Override public void setChainHandler(ChainHandler handler) {
    this.handler = handler;
  }

  @Override public ChainHandler getChain() {
    return handler;
  }

  @Override public void handle(int money) {
    if (money <= 5000){
      Log.e(TAG, "已处理" + money);
    }else{
      if (getChain() != null){
        getChain().handle(money);
      }else{
        Log.e(TAG, "上级不在无法处理");
      }
    }
  }
}
