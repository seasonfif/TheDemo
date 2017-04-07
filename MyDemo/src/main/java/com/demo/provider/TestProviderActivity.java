package com.demo.provider;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.demo.R;
import com.demo.activity.BaseActivity;
import com.seasonfif.dynamicplugin.IEventBusService;
import com.seasonfif.svcmanager.ServiceManager;

public class TestProviderActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    IBinder binder = ServiceManager.getService("EventBusService");
    IEventBusService service = IEventBusService.Stub.asInterface(binder);
    try {
      service.post("zq", "zq");
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void setView() {
    setContentView(R.layout.activity_test_provider);
  }
}
