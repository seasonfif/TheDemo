package com.demo.matrix.annotation;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.demo.matrix.annotation.NestMode.ADJUST;
import static com.demo.matrix.annotation.NestMode.COMPLY;
import static com.demo.matrix.annotation.NestMode.NONE;

/**
 * 创建时间：2017年05月18日11:57 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

@IntDef({ ADJUST,COMPLY,NONE})
@Retention(RetentionPolicy.SOURCE)
public @interface NestMode{

   /**
   * 不允许嵌套
   */
   int NONE = 0;

   /**
   * 根据viewgroup特性顺应嵌套
   * 比如LinearLayout的VERTICAL与HORIZONTAL
   */
   int COMPLY = 1;

   /**
   * 自己布局嵌套
   */
   int ADJUST = 2;
}

