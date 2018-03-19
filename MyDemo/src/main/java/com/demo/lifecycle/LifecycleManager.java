package com.demo.lifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqiang on 2018/3/19.
 */

class LifecycleManager {

  private List<Lifecycle> lifecycles;

  public LifecycleManager(){
    lifecycles = new ArrayList<>();
  }

  public void addListener(Lifecycle lifecycle) {
    if (!lifecycles.contains(lifecycle)){
      lifecycles.add(lifecycle);
    }
  }

  private void removeListener(Lifecycle lifecycle) {
    lifecycles.remove(lifecycle);
    lifecycle = null;
  }

  public void onAttach() {
    for (Lifecycle life : lifecycles) {
      life.onAttach();
    }
  }

  public void onResume() {
    for (Lifecycle life : lifecycles) {
      life.onResume();
    }
  }

  public void onStop() {
    for (Lifecycle life : lifecycles) {
      life.onStop();
    }
  }

  public void onDestory() {
    for (Lifecycle life : lifecycles) {
      life.onDestory();
      removeListener(life);
    }
  }
}
