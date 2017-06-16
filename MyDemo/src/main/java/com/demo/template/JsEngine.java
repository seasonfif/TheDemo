package com.demo.template;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

/**
 * 创建时间：2017年06月16日12:16 <br>
 * 作者：zhangqiang <br>
 * 描述：Rhino引擎执行js代码
 */

public class JsEngine {

  private static volatile JsEngine sInstance;
  private final Context rhino;
  private final ScriptableObject scriptable;

  public static JsEngine getEngine(){
    if (sInstance == null){
      synchronized (JsEngine.class){
        if (sInstance == null){
          sInstance = new JsEngine();
        }
      }
    }
    return sInstance;
  }

  private JsEngine(){
    rhino = Context.enter();
    rhino.setOptimizationLevel(-1);
    scriptable = rhino.initStandardObjects();
  }

  public Object exec(String js, String jsName, String fName, Object...args) {
    Object result;
    rhino.evaluateString(scriptable, js, jsName, 1, null);
    Function f = (Function) scriptable.get(fName, scriptable);
    result = f.call(rhino, scriptable, scriptable, args);
    return result;
  }
}
