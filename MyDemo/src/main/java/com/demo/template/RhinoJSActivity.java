package com.demo.template;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
  private WebView wb;

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
    /*try {
      String js = DataUtil.getStrFromAssets(getAssets().open("expression.js"));
      result = execJs(js);
    } catch (IOException e) {
      e.printStackTrace();
    }*/
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

  private Object execJs(String js){
    Object result = null;
    wb = new WebView(this);
    wb.setWebViewClient(new WebViewClient(){
      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.e("RhinoJSActivity", "onPageFinished()");
        wb.loadUrl("javascript:function add() {console.log(02020);}");
        wb.loadUrl("javascript:add()");
      }
    });
    wb.getSettings().setJavaScriptEnabled(true);
    wb.addJavascriptInterface(new JsInterface(), "expression");
    wb.loadUrl("file:///android_asset/test.html");
    //wb.loadUrl("javascript:function add() {console.log(02020);}");

    /*new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        Log.e("RhinoJSActivity", "javascript:add()");
        wb.loadUrl("javascript:add()");
      }
    }, 2000);*/
    return result;
  }

  class JsInterface{
    @JavascriptInterface
    void add(Object obj){
      Log.e("RhinoJSActivity", obj + "");
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
