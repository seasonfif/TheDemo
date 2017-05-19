package com.demo.matrix.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.demo.matrix.model.Node;
import com.demo.matrix.view.FactoryProxy;
import com.demo.matrix.view.ICard;
import com.demo.matrix.view.ICardFactory;
import com.demo.matrix.annotation.NestMode;
import java.util.Collections;
import java.util.List;

/**
 * 创建时间：2017年05月17日16:20 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class LayoutEngine {

  /**
   *
   */
  public static int AUTO_FLAG = -1;

  private Context context;
  private FactoryProxy factory;

  public LayoutEngine(ICardFactory factory) {
    this.factory = new FactoryProxy(factory);
  }

  public View layout(@NonNull Context context, @NonNull Node node) {
    this.context = context;
    ICard root;
    root = factory.createCard(context, node.getType());
    root.update(node.getDes());

    if (!node.isLeaf()){
      layout(node, root);
    }
    return (View)root;
  }

  private void layout(Node node, ICard card) {
    if (!(card instanceof ViewGroup)){
      throw new IllegalStateException("Card [type=" + node.getType() + "]是View，原生不支持嵌套");
    }

    if (card.getNestMode() == NestMode.NONE){
      throw new IllegalStateException("Card [type=" + node.getType() + "]不允许嵌套");
    }
    if (card.getNestMode() == NestMode.AUTO){
      List<Node> children = node.getChildren();
      sortByWeight(children);
      for (Node child : children) {
        ICard childCard = factory.createCard(context, child.getType());
        childCard.update(child.getDes());
        card.addCard(AUTO_FLAG, childCard);
        if (!child.isLeaf()){
          layout(child, childCard);
        }
      }
    }else if(card.getNestMode() == NestMode.MANUAL){
      List<Node> children = node.getChildren();
      for (int i = 0; i < children.size(); i++) {
        Node child = children.get(i);
        ICard childCard = factory.createCard(context, child.getType());
        childCard.update(child.getDes());
        card.addCard(i, childCard);
        if (!child.isLeaf()){
          layout(child, childCard);
        }
      }
    }else{
      throw new IllegalStateException("嵌套类型非法");
    }
  }

  private void sortByWeight(List<Node> nodes){
    Collections.sort(nodes, new NodeComparator());
  }
}
