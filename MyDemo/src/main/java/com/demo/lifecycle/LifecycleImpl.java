package com.demo.lifecycle;

import android.util.Log;

/**
 * Created by zhangqiang on 2018/3/19.
 */

class LifecycleImpl implements Lifecycle{

  private static final String TAG = "LifecycleImpl";

  private String tag;
  public LifecycleImpl(String tag) {
    this.tag = tag;
  }

  @Override public void onAttach() {
    Log.e(TAG, tag + " onAttach");
  }

  @Override public void onResume() {
    Log.e(TAG, tag + " onResume");
  }

  @Override public void onStop() {
    Log.e(TAG, tag + " onStop");
  }

  @Override public void onDestory() {
    Log.e(TAG, tag + " onDestory");
  }
}
