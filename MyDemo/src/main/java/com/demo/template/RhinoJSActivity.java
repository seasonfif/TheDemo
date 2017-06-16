package com.demo.template;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.demo.R;
import com.demo.activity.BaseActivity;
import com.demo.util.DataUtil;
import java.io.IOException;

/**
 * 创建时间：2017年06月16日11:25 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class RhinoJSActivity extends BaseActivity {

  private TextView tv;
  private Object result;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tv = (TextView) findViewById(R.id.tv);
    String json = null;
    try {
      json = DataUtil.getStrFromAssets(getAssets().open("test.json"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    long s = System.currentTimeMillis();
    result = execJs2("demo.js", "transfer", new Object[]{json});
    long e = System.currentTimeMillis();
    Log.e("RhinoJSActivity", (e-s) + " ms ");
    tv.setText(result + "");
  }

  @Override public void setView() {
    setContentView(R.layout.activity_rhino_js);
  }

  private Object execJs2(String jsName, String fName, Object...args) {
    Object result = null;
    try {
      String js = DataUtil.getStrFromAssets(getAssets().open(jsName));
      result = JsEngine.getEngine().exec(js, jsName, fName, args);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
