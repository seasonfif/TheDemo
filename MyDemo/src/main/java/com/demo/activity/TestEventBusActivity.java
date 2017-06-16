package com.demo.activity;

import android.os.Bundle;
import android.widget.Toast;
import com.demo.FirstEvent;
import com.demo.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 创建时间：2017年06月14日11:31 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class TestEventBusActivity extends BaseActivity{
  @Override public void setView() {
    setContentView(R.layout.activity_event);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
  }

  @Subscribe(sticky = true)
  public void onEvent(FirstEvent event) {
    Toast.makeText(this, "onEvent by Sticky\n" + event.getMsg(), Toast.LENGTH_LONG).show();
  }

  @Override protected void onDestroy() {
    EventBus.getDefault().removeAllStickyEvents();
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }
}
