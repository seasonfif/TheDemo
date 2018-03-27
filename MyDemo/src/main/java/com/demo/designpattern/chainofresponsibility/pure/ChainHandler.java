package com.demo.designpattern.chainofresponsibility.pure;

/**
 * 纯责任链模式
 *
 * Created by zhangqiang on 2018/3/26.
 */

public interface ChainHandler {

  void setChainHandler(ChainHandler handler);

  ChainHandler getChain();

  void handle(int money);
}
