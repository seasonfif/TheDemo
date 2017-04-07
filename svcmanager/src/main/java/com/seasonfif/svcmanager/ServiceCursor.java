package com.seasonfif.svcmanager;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 创建时间：2017年04月07日16:33 <br>
 * 作者：zhangqiang <br>
 * 描述：A Matrix Cursor implementation that a binder object is embedded in its extra
 * data.
 */

public class ServiceCursor extends MatrixCursor{

  public static final String SERVER_CHANNEL_BUNDLE_KEY = "servicechannel";

  protected static final String[] DEFAULT_COLUMNS = {
      "s"
  };

  private Bundle mBundle = new Bundle();

  public static ServiceCursor makeCursor(IBinder binder){
    return new ServiceCursor(DEFAULT_COLUMNS, binder);
  }

  public static IBinder getBinder(Cursor cursor){
    Bundle bundle = cursor.getExtras();
    bundle.setClassLoader(ParceBinder.class.getClassLoader());
    ParceBinder parceBinder = bundle.getParcelable(SERVER_CHANNEL_BUNDLE_KEY);
    return parceBinder.getIBinder();
  }

  private ServiceCursor(String[] columnNames, IBinder binder) {
    super(columnNames);
    mBundle.putParcelable(SERVER_CHANNEL_BUNDLE_KEY, new ParceBinder(binder));
  }

  @Override public Bundle getExtras() {
    return mBundle;
  }
}
