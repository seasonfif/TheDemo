package com.demo.matrix.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.demo.matrix.model.Node;
import com.demo.matrix.view.FactoryProxy;
import com.demo.matrix.view.ICard;
import com.demo.matrix.view.ICardFactory;
import com.demo.matrix.annotation.NestMode;

/**
 * 创建时间：2017年05月17日16:20 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class LayoutEngine {

  public static int COMPLY_FLAG = -1;

  private Context context;
  private FactoryProxy factory;

  public LayoutEngine(Context context, ICardFactory factory) {
    this.context = context;
    this.factory = new FactoryProxy(factory);
  }

  public View layout(@NonNull Node node) {
    ICard card;
    card = factory.createCard(context, node.getType());
    card.update(node.getData());

    if (!node.isLeaf()){
      layout(node, card);
    }
    return (View)card;
  }

  private void layout(Node node, ICard card) {
    if (card.getNestMode() == NestMode.NONE){
      throw new IllegalStateException("该数据节点含有子节点，card却不允许嵌套");
    }
    if (card.getNestMode() == NestMode.COMPLY){
      for (Node child : node.getChildren()) {
        ICard childCard = factory.createCard(context, child.getType());
        childCard.update(child.getData());
        card.addCard(COMPLY_FLAG, childCard);
        if (!child.isLeaf()){
          layout(child, childCard);
        }
      }
    }else if(card.getNestMode() == NestMode.ADJUST){
      for (int i = 0; i < node.getChildren().size(); i++) {
        Node child = node.getChildren().get(i);
        ICard childCard = factory.createCard(context, node.getType());
        childCard.update(child.getData());
        card.addCard(i, childCard);
        if (!child.isLeaf()){
          layout(child, childCard);
        }
      }
    }else{
      throw new IllegalStateException("嵌套类型非法");
    }
  }
}
