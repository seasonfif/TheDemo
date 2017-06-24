package com.demo.template;

/**
 * 创建时间：2017年06月16日11:51 <br>
 * 作者：zhangqiang <br>
 * 描述：模板化通用Model
 */

public class UiModel {

  //文本数据 目前暂定10个
  public String text1;
  public String text2;
  public String text3;
  public String text4;
  public String text5;
  public String text6;
  public String text7;
  public String text8;
  public String text9;
  public String text10;

  //图片数据 目前暂定5个
  public String image1;
  public String image2;
  public String image3;
  public String image4;
  public String image5;

  //按钮数据 目前暂定5个
  public Buttoner button1;
  public Buttoner button2;
  public Buttoner button3;
  public Buttoner button4;
  public Buttoner button5;

  //按钮结构
  static class Buttoner{
    public String text;
    public String image;
    public String action;
  }
}
