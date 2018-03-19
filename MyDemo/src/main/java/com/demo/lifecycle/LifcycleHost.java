package com.demo.lifecycle;

/**
 * Created by zhangqiang on 2018/3/19.
 */

interface LifcycleHost {

  LifecycleManager getLifecycleManager();

  void setLifecycleManager(LifecycleManager lifecycleManager);
}
