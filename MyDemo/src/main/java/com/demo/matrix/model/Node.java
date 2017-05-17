package com.demo.matrix.model;

import java.util.List;

/**
 * 创建时间：2017年05月17日11:06 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Node implements INode{
  public int type;
  public String des;
  public String data;
  public List<Node> children;

  @Override public int getType() {
    return type;
  }

  @Override public String getDes() {
    return des;
  }

  @Override public String getData() {
    return data;
  }

  @Override public List<Node> getChildren() {
    return children;
  }
}
