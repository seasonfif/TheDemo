package com.demo.designpattern.chainofresponsibility.nonpure;

/**
 *
 * 不纯的责任链模式
 * Created by zhangqiang on 2018/3/27.
 */

public interface Interceptor {

  void intercept(Chain chain);

  interface Chain{
    String getName();
    void procced(int money, String user);
  }
}
