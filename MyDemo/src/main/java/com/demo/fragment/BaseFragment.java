package com.demo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.demo.lifecycle.EasyLifecycle;

/**
 * Created by zhangqiang on 2018/3/21.
 */

public class BaseFragment extends Fragment {

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EasyLifecycle.with().init(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
