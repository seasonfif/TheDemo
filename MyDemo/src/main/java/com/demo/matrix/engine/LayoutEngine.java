package com.demo.matrix.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.demo.matrix.model.Node;
import java.util.List;

/**
 * 创建时间：2017年05月17日16:20 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class LayoutEngine {

  private Context context;

  public LayoutEngine(Context context) {
    this.context = context;
  }

  public View layout(@NonNull Node node) {


    if (node.children.size() > 0){

      for (Node child : node.children) {
        flatNode(child);
      }
    }
    return new View(context);
  }

  private Node flatNode(Node node){

  }
}
