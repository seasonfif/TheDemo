package com.demo.designpattern.chainofresponsibility.nonpure;

import android.util.Log;
import java.util.List;

/**
 * Created by zhangqiang on 2018/3/27.
 */

public class RealChain implements Interceptor.Chain {

  private static final String TAG = "RealChain";

  private List<Interceptor> interceptors;
  private int index;
  private int money;
  private String user;

  public RealChain(List<Interceptor> interceptors, int index, String user){
    this.interceptors = interceptors;
    this.index = index;
    this.user = user;
  }

  @Override public String getName() {
    return this.user;
  }

  @Override public void procced(int money, String user) {
    this.money = money;
    this.user = user;
    if (index >= interceptors.size()){
      Log.e(TAG, user);
      Log.e(TAG, "审批流程结束");
      return;
    }

    RealChain next = new RealChain(interceptors, index + 1, user);
    Interceptor interceptor = interceptors.get(index);
    interceptor.intercept(next);
  }
}
