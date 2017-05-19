package com.seasonfif.matrix.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.helper.NodeComparator;
import com.seasonfif.matrix.model.INode;
import com.seasonfif.matrix.proxy.FactoryProxy;
import com.seasonfif.matrix.card.ICard;
import com.seasonfif.matrix.card.ICardFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

/**
 * 创建时间：2017年05月17日16:20 <br>
 * 作者：zhangqiang <br>
 * 描述：布局引擎
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

  public View layout(@NonNull Context context, @NonNull INode node) {
    this.context = context;
    ICard root;
    root = factory.createCard(context, node.getType());
    root.update(getCardModel(node, root));

    if (!isLeaf(node)){
      layout(node, root);
    }
    return (View)root;
  }

  private void layout(INode node, ICard card) {
    if (!(card instanceof ViewGroup)){
      throw new IllegalStateException("Card [type=" + node.getType() + "]是View，原生不支持嵌套");
    }

    if (card.getNestMode() == NestMode.NONE){
      throw new IllegalStateException("Card [type=" + node.getType() + "]不允许嵌套");
    }
    if (card.getNestMode() == NestMode.AUTO){
      List<? extends INode> children = node.getChildren();
      sortByWeight(children);
      for (INode child : children) {
        ICard childCard = factory.createCard(context, child.getType());
        childCard.update(getCardModel(child, childCard));
        card.addCard(AUTO_FLAG, childCard);
        if (!isLeaf(child)){
          layout(child, childCard);
        }
      }
    }else if(card.getNestMode() == NestMode.MANUAL){
      List<? extends INode> children = node.getChildren();
      for (int i = 0; i < children.size(); i++) {
        INode child = children.get(i);
        ICard childCard = factory.createCard(context, child.getType());
        childCard.update(getCardModel(child, childCard));
        card.addCard(i, childCard);
        if (!isLeaf(child)){
          layout(child, childCard);
        }
      }
    }else{
      throw new IllegalStateException("嵌套类型非法");
    }
  }

  private boolean isLeaf(INode node){
    if (node.getChildren() == null){
      return true;
    }else{
      if (node.getChildren().size() <= 0){
        return true;
      }
      return false;
    }
  }

  private void sortByWeight(List<? extends INode> nodes){
    Collections.sort(nodes, new NodeComparator());
  }

  private Object getCardModel(INode node, ICard card){
    CardModel cardModel = card.getClass().getAnnotation(CardModel.class);
    Class cls = cardModel.value();
    Object obj;
    try {
      obj = new Gson().fromJson((String)node.getData(), cls);
    } catch (JsonSyntaxException e) {
      return null;
    }
    return obj;
  }
}
