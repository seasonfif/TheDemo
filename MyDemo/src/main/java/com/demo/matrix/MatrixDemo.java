package com.demo.matrix;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.demo.R;
import com.demo.matrix.engine.Matrix;
import com.demo.matrix.model.Node;
import com.demo.util.DataUtil;
import java.io.IOException;

/**
 * 创建时间：2017年05月17日11:57 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class MatrixDemo extends Activity{

  private static final String TAG = "MatrixDemo";
  private Node node;
  private TextView tv;
  private View view;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_matrix_layout);
    //tv = (TextView) findViewById(R.id.tv);
    try {
      String jsonStr = DataUtil.getStrFromAssets(getAssets().open("nodes.json"));
      node = DataUtil.getData(jsonStr, Node.class);
      if (node == null){
        Log.e(TAG, "");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    view = Matrix.with(this).produce(node);
    setContentView(view);

  }
}
