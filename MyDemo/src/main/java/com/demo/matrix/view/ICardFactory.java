package com.demo.matrix.view;

import android.content.Context;

/**
 * 创建时间：2017年05月17日13:49 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public interface ICardFactory {

  ICard createCard(Context context, int type);
}
