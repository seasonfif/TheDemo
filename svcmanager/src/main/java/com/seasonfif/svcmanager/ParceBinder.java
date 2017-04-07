package com.seasonfif.svcmanager;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间：2017年04月07日16:46 <br>
 * 作者：zhangqiang <br>
 * 描述：Binder的序列化类
 */

public class ParceBinder implements Parcelable{

  private final IBinder mBinder;

  public ParceBinder(Parcel in) {
    mBinder = in.readStrongBinder();
  }

  public ParceBinder(IBinder binder) {
    this.mBinder = binder;
  }

  public IBinder getIBinder() {
    return mBinder;
  }

  public static final Creator<ParceBinder> CREATOR = new Creator<ParceBinder>() {
    @Override public ParceBinder createFromParcel(Parcel in) {
      return new ParceBinder(in);
    }

    @Override public ParceBinder[] newArray(int size) {
      return new ParceBinder[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeStrongBinder(mBinder);
  }
}
