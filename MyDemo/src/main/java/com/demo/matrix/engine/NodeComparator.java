package com.demo.matrix.engine;

import com.demo.matrix.model.INode;
import java.util.Comparator;

/**
 * 创建时间：2017年05月19日13:00 <br>
 * 作者：zhangqiang <br>
 * 描述：树节点按照权重weight排序
 */

public class NodeComparator implements Comparator<INode> {

  @Override public int compare(INode lhs, INode rhs) {
    return rhs.getWeight() - lhs.getWeight();
  }
}
