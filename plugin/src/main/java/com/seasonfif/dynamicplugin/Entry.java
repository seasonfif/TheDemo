package com.seasonfif.dynamicplugin;

import android.content.Context;
import android.os.IBinder;

/**
 * 创建时间：2017年01月18日18:19 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Entry {

  public static IBinder create(Context context, ClassLoader cls){
    IBinder binder;
    binder = new Plugin(context, cls);
    return binder;
  }
}
