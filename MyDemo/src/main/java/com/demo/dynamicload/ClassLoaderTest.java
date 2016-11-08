package com.demo.dynamicload;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.demo.R;
import com.demo.activity.BaseActivity;

/**
 * 创建时间：2016年11月02日20:39 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class ClassLoaderTest extends BaseActivity{


  private String TAG = ClassLoaderTest.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //printClassloader();

    findViewById(R.id.simple_mode).setOnClickListener(this);
  }

  @Override public void setView() {
    setContentView(R.layout.activity_classloader);
  }

  @Override public void onClick(View v) {
    super.onClick(v);
    switch (v.getId()){
      case R.id.simple_mode:
        testSimpleDynamicLoader();
        break;
    }
  }

  private void testSimpleDynamicLoader() {

    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
  }

  private void printClassloader(){
    int i = 1;
    ClassLoader cl = getClassLoader();
    if (cl != null){
      Log.e(TAG, "[onCreate] classLoader " + i + " : " + cl.toString());
      while (cl.getParent() != null){
        cl = cl.getParent();
        i++;
        Log.e(TAG, "[onCreate] classLoader " + i + " : " + cl.toString());
      }
    }
  }
}
