package com.demo.lifecycle;

/**
 * Created by zhangqiang on 2018/3/19.
 */

interface Lifecycle {

  void onAttach();

  void onResume();

  void onStop();

  void onDestory();
}
