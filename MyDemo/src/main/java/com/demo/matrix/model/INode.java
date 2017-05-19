package com.demo.matrix.model;

import java.util.List;

/**
 * 创建时间：2017年05月17日11:04 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public interface INode {

  int getType();

  int getWeight();

  String getDes();

  String getData();

  List<Node> getChildren();
}
