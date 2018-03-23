package com.demo.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by zhangqiang on 2018/3/19.
 */

public class EasyLifecycle {

  private static final String FRAGMENT_TAG = "BLANK_FRAGMENT";

  private static volatile EasyLifecycle lifecycle;

  public static EasyLifecycle with(){
    if (lifecycle == null){
      synchronized (EasyLifecycle.class){
        if (lifecycle == null){
          lifecycle = new EasyLifecycle();
        }
      }
    }
    return lifecycle;
  }

  public void init(Context ctx){
    if (ctx instanceof Activity){
      if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && ((Activity) ctx).isDestroyed()){
        throw new IllegalArgumentException("You cannot start a observe for a destoryed activity");
      }
    }

    if (Looper.myLooper() != Looper.getMainLooper()){
      throw new IllegalStateException("只能在主线程调用");
    }

    LifcycleHost host = null;
    if (ctx instanceof Activity){
      host = getFragment(((Activity) ctx).getFragmentManager());
    } else if (ctx instanceof FragmentActivity){
      host = getV4Fragment(((FragmentActivity) ctx).getSupportFragmentManager());
    }
    bindLifecycle(host, ctx.getClass().getSimpleName());
  }

  public void init(Fragment fragment){
    LifcycleHost host = null;
    host = getFragment(fragment.getChildFragmentManager());
    bindLifecycle(host, fragment.getClass().getSimpleName());
  }

  private BlankFragment4Lifecycle getFragment(android.app.FragmentManager fm) {
    BlankFragment4Lifecycle fragment;
    fragment = (BlankFragment4Lifecycle) fm.findFragmentByTag(FRAGMENT_TAG);
    if (fragment == null){
      fragment = new BlankFragment4Lifecycle();
      fm.beginTransaction().add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
    }
    return fragment;
  }

  private BlankFragment4LifecycleV4 getV4Fragment(FragmentManager fm) {
    BlankFragment4LifecycleV4 fragment;
    fragment = (BlankFragment4LifecycleV4) fm.findFragmentByTag(FRAGMENT_TAG);
    if (fragment == null){
      fragment = new BlankFragment4LifecycleV4();
      fm.beginTransaction().add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
    }
    return fragment;
  }

  private void bindLifecycle(LifcycleHost host, String tag) {
    LifecycleManager lifecycleManager = host.getLifecycleManager();
    if (lifecycleManager == null){
      lifecycleManager = new LifecycleManager();
    }
    host.setLifecycleManager(lifecycleManager);
    lifecycleManager.addListener(new LifecycleImpl(tag));
  }
}
