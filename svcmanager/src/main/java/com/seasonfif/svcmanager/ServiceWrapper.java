package com.seasonfif.svcmanager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.lianjia.svcmanager.creators.IServiceChannel;
import java.io.FileDescriptor;

/**
 * 创建时间: 2016/09/27 11:18 <br>
 * 作者: fujia <br>
 * 描叙: A Binder Wrapper class that monitors the death of the underlying remote
 * Binder and recovers it if needed.
 */
/* PACKAGE */class ServiceWrapper implements IBinder, IBinder.DeathRecipient {

  //private static final boolean DEBUG = true;

  private static final String TAG = "ServiceWrapper";

  private final Context mAppContext;

  private IBinder mRemote;

  private final String mName;

  public static IBinder factory(Context context, String name, IBinder binder) {
    String descriptor = null;
    try {
      descriptor = binder.getInterfaceDescriptor();
    } catch (RemoteException e) {
      if (ServiceManagerEnv.isDebug()) {
        Log.d(TAG, "getInterfaceDescriptor()", e);
      }
    }
    IInterface iin = binder.queryLocalInterface(descriptor);
    if (iin != null) {
      /**
       * If the requested interface has local implementation, meaning that
       * it's living in the same process as the one who requests for it,
       * return the binder directly since in such cases our wrapper does
       * not help in any way.
       */
      return binder;
    }
    return new ServiceWrapper(context, name, binder);
  }

  private ServiceWrapper(Context context, String name, IBinder binder) {
    if (context instanceof Application) {
      mAppContext = context;
    } else {
      mAppContext = context.getApplicationContext();
    }
    mRemote = binder;
    mName = name;
    try {
      mRemote.linkToDeath(this, 0);
    } catch (RemoteException e) {
      if (ServiceManagerEnv.isDebug()) {
        Log.d(TAG, "linkToDeath ex", e);
      }
    }
  }

  private IBinder getRemoteBinder() throws RemoteException {
    IBinder remote = mRemote;
    if (remote != null) {
      return remote;
    }
    IServiceChannel serviceChannel = ServiceManager.getServerChannel(mAppContext);
    if (serviceChannel == null) {
      // 在获取Cursor时，可能恰巧常驻进程被停止，则有可能出现获取失败的情况
      throw new RemoteException("fail to get serviceChannel");
    }
    remote = serviceChannel.getService(mName);
    if (remote == null) {
      throw new RemoteException();
    }

    try {
      remote.linkToDeath(this, 0);
    } catch (RemoteException e) {
      if (ServiceManagerEnv.isDebug()) {
        Log.d(TAG, "linkToDeath ex", e);
      }
    }

    mRemote = remote;
    return remote;
  }

  @Override public String getInterfaceDescriptor() throws RemoteException {
    return getRemoteBinder().getInterfaceDescriptor();
  }

  @Override public boolean pingBinder() {
    try {
      return getRemoteBinder().pingBinder();
    } catch (RemoteException e) {
      if (ServiceManagerEnv.isDebug()) {
        Log.d(TAG, "getRemoteBinder()", e);
      }
    }
    return false;
  }

  @Override public boolean isBinderAlive() {
    try {
      return getRemoteBinder().isBinderAlive();
    } catch (RemoteException e) {
      if (ServiceManagerEnv.isDebug()) {
        Log.d(TAG, "isBinderAlive()", e);
      }
    }
    return false;
  }

  @Override public IInterface queryLocalInterface(String descriptor) {
    try {
      return getRemoteBinder().queryLocalInterface(descriptor);
    } catch (RemoteException e) {
      if (ServiceManagerEnv.isDebug()) {
        Log.d(TAG, "queryLocalInterface()", e);
      }
    }
    return null;
  }

  @Override public void dump(FileDescriptor fd, String[] args) throws RemoteException {
    getRemoteBinder().dump(fd, args);
  }

  @Override public boolean transact(int code, Parcel data, Parcel reply, int flags)
      throws RemoteException {
    return getRemoteBinder().transact(code, data, reply, flags);
  }

  @Override public void linkToDeath(DeathRecipient recipient, int flags) throws RemoteException {
    /**
     * ServiceWrapper存在的意义在于能自动检测远程Binder进程死去并且在需要的时候重新获取，
     * 因此对于ServiceWrapper来说再设置DeathRecipient没有任何意义。
     */

    if (ServiceManagerEnv.isDebug()) {
      throw new UnsupportedOperationException("ServiceWrapper does NOT support Death Recipient!");
    }
  }

  @Override public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
    /**
     * ServiceWrapper存在的意义在于能自动检测远程Binder进程死去并且在需要的时候重新获取，
     * 因此对于ServiceWrapper来说再设置DeathRecipient没有任何意义。
     */
    return false;
  }

  @Override public void binderDied() {
    if (ServiceManagerEnv.isDebug()) {
      Log.d(TAG, "ServiceWrapper [binderDied]: " + mName);
    }
    mRemote = null;
    Intent intent = new Intent();
    intent.setAction(ServiceManager.BINDER_DIED_ACTION);
    intent.putExtra(ServiceManager.SERVICE_NAME_KEY, mName);
    LocalBroadcastManager.getInstance(mAppContext).sendBroadcast(intent);
  }

  // Override API 15
  public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {
  }
}
