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

  private LayoutEngine engine;

  public static Matrix with(Context context){
    if (singleton == null){
      synchronized (Matrix.class){
        if (singleton == null){
          singleton = new Matrix(context);
        }
      }
    }
    return singleton;
  }

  private Matrix(Context context) {
    ICardFactory factory = null;
    try {
      Class cls = Class.forName("com.demo.matrix.custome.CardFactory");
      factory = (ICardFactory) cls.newInstance();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    engine = new LayoutEngine(context, factory);
  }

  public View produce(Node node){
    return engine.layout(node);
  }
}
