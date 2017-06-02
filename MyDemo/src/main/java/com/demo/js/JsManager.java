package com.demo.js;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsManager {

  private static volatile JsManager instance;

  private ScriptEngineManager scriptEngineManager;
  private ScriptEngine engine;

  public static JsManager getInstance() {
    if (instance == null){
      synchronized (JsManager.class){
        if (instance == null){
          instance = new JsManager();
        }
      }
    }
    return instance;
  }

  private JsManager(){
    scriptEngineManager = new ScriptEngineManager();
    engine = scriptEngineManager.getEngineByName("javascript");
  }

  public Object invoke(InputStream in, String function, Object...args) {
    Reader scriptReader = null;
    try {
      scriptReader = new InputStreamReader(in, "utf-8");
      engine.eval(scriptReader);
      if (engine instanceof Invocable){
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(function, args);
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ScriptException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } finally {
      if (scriptReader != null){
        try {
          scriptReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return engine;
  }
}
