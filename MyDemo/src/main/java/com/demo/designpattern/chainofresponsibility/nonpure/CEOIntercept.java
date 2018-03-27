package com.demo.designpattern.chainofresponsibility.nonpure;

import android.util.Log;

/**
 * Created by zhangqiang on 2018/3/27.
 */

public class CEOIntercept implements Interceptor {

  private static final String TAG = "CEOIntercept";

  @Override public void intercept(Chain chain) {
    chain.procced(600, chain.getName()+" CEO审批通过");
  }
}
