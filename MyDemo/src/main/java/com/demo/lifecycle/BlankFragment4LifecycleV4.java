package com.demo.lifecycle;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by zhangqiang on 2018/3/19.
 */

public class BlankFragment4LifecycleV4 extends Fragment implements LifcycleHost{

  private LifecycleManager lifecycleManager;

  public void setLifecycleManager(LifecycleManager lifecycleManager) {
    this.lifecycleManager = lifecycleManager;
  }

  public LifecycleManager getLifecycleManager() {
    return lifecycleManager;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (lifecycleManager != null){
      lifecycleManager.onAttach();
    }
  }

  @Override public void onResume() {
    super.onResume();
    if (lifecycleManager != null){
      lifecycleManager.onResume();
    }
  }

  @Override public void onStop() {
    super.onStop();
    if (lifecycleManager != null){
      lifecycleManager.onStop();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (lifecycleManager != null){
      lifecycleManager.onDestory();
      lifecycleManager = null;
    }
  }
}
