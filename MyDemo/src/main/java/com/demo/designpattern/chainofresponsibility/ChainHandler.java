package com.demo.designpattern.chainofresponsibility;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public interface ChainHandler {

  void setChainHandler(ChainHandler handler);

  ChainHandler getChain();

  void handle(int money);
}
