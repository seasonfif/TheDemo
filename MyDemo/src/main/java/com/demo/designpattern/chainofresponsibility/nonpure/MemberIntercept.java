package com.demo.designpattern.chainofresponsibility.nonpure;

/**
 * Created by zhangqiang on 2018/3/27.
 */

public class MemberIntercept implements Interceptor {

  @Override public void intercept(Chain chain) {
    chain.procced(600, chain.getName()+" 职员审批通过");
  }
}
