package com.demo.matrix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.R;
import com.demo.util.DataUtil;
import com.seasonfif.matrix.engine.Matrix;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建时间：2017年05月17日11:57 <br>
 * 作者：zhangqiang <br>
 * 描述：Matrix Demo
 */

public class MatrixDemo extends Activity{

  private static final String TAG = "MatrixDemo";
  private Node node;
  private ScrollView scroll;
  private TextView tv;
  private View view;
  private ProgressDialog dialog;
  OkHttpClient client = new OkHttpClient();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_matrix_layout);
    tv = (TextView) findViewById(R.id.tv);
    scroll = (ScrollView) findViewById(R.id.scroll);

    dialog = new ProgressDialog(this, android.app.AlertDialog.THEME_HOLO_LIGHT);
    dialog.setCancelable(false);
    dialog.setIndeterminate(true);

    refresh();

    tv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        refresh();
      }
    });
  }

  private void refresh() {
    dialog.show();
    Request request = new Request.Builder()
            .url("http://seasonfif.com/nodes.json")
            .build();
    //同步
    /*try {
      Response response = client.newCall(request).execute();
      if (!response.isSuccessful()){

      }else{

      }
    } catch (IOException e) {
      e.printStackTrace();
    }*/

    //异步
    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        Log.e(TAG, Thread.currentThread().getName());
        Log.e(TAG, e.getMessage());
        node = DataUtil.getData(loadJsonAssets(), Node.class);
        view = Matrix.getEngine().produce(MatrixDemo.this, node);
        scroll.post(new Runnable() {
          @Override public void run() {
            dialog.dismiss();
            scroll.removeAllViews();
            scroll.addView(view);
          }
        });
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        //这是在子线程
        Log.e(TAG, Thread.currentThread().getName());
        if (response.isSuccessful()){
          String jsonStr = response.body().string();
          node = DataUtil.getData(jsonStr, Node.class);
          view = Matrix.getEngine().produce(MatrixDemo.this, node);
          scroll.post(new Runnable() {
            @Override public void run() {
              dialog.dismiss();
              scroll.removeAllViews();
              scroll.addView(view);
            }
          });
        }
      }
    });
  }

  private String loadJsonAssets(){
    String jsonStr = null;
    try {
      jsonStr = DataUtil.getStrFromAssets(getAssets().open("nodes.json"));
      if (node == null){
        Log.e(TAG, "");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  return jsonStr;
  }
}
