package com.demo.matrix.engine;

import android.content.Context;
import android.view.View;
import com.demo.matrix.model.Node;
import com.demo.matrix.view.ICardFactory;

/**
 * 创建时间：2017年05月17日14:18 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Matrix {

  private static volatile Matrix singleton = null;

  private static ICardFactory sFactory;

  private LayoutEngine engine;

  public static void init(ICardFactory factory){
    sFactory = factory;
  }

  private Matrix(ICardFactory factory) {
    engine = new LayoutEngine(factory);
  }

  public static Matrix getEngine(){
    if (singleton == null){
      synchronized (Matrix.class){
        if (singleton == null){
          singleton = new Matrix(sFactory);
        }
      }
    }
    return singleton;
  }

  public View produce(Context context, Node node){
    return engine.layout(context, node);
  }
}
