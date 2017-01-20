package com.seasonfif.pluginhost.manager;

import android.content.Context;

/**
 * 创建时间：2017年01月20日13:44 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class PMF {

  static PmBase sPluginMgr;

  public static final void init(Context context){
    sPluginMgr = new PmBase(context);
    sPluginMgr.init();

    Factory.sPluginManager = PMF.getLocal();
  }

  public static final void attach(){
    sPluginMgr.attach();
  }

  /**
   * @return
   */
  public static final IPluginManager getLocal() {
    return sPluginMgr.mLocal;
  }
}
