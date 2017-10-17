package com.demo.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.demo.R;
import com.demo.activity.BaseActivity;

/**
 * 创建时间：2017年06月22日11:17 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class TestWebViewActivity extends BaseActivity{

  private WebView wb;

  @Override public void setView() {
    setContentView(R.layout.activity_webview);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    wb = (WebView) findViewById(R.id.webView);
    initWeb();
  }

  private void initWeb(){
    wb.setWebViewClient(new WebViewClient(){
      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Toast.makeText(TestWebViewActivity.this, url, Toast.LENGTH_LONG).show();
        return true;
      }
    });
    wb.setWebChromeClient(new WebChromeClient());
    wb.loadUrl("file:///android_asset/test.html");
    wb.getSettings().setJavaScriptEnabled(true);
    wb.getSettings().setAllowFileAccess(true);
    wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口
    wb.addJavascriptInterface(new JsInterface(), "bridge");
  }

  //在java中调用js代码
  public void sendInfoToJs(View v) {

    String msg = "Java调用JS";
    //调用js中的函数：showInfoFromJava(msg)
    wb.loadUrl("javascript:showInfoFromJava('" + msg + "')");
  }

  public class JsInterface{

    @JavascriptInterface
    public void toast(String msg){
      Log.e("TestWebViewActivity", msg + "");
      Toast.makeText(TestWebViewActivity.this, msg, Toast.LENGTH_LONG).show();;
    }
  }
}
