package com.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.demo.R;
import com.demo.util.DataUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import java.io.IOException;
import java.util.ArrayList;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TestViewGroupActivity extends BaseActivity{

    String[] titleArray = new String[]{"商圈排名","大区排名","城市排名"};
    private TextView tv;
    private CommonTabLayout mTabLayout;
    private final ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private Object result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        String json = null;
        try {
            json = DataUtil.getStrFromAssets(getAssets().open("test.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long s = System.currentTimeMillis();
        result = execJs2("demo.js", "transfer", new Object[]{json});
        long e = System.currentTimeMillis();
        Log.e("TestViewGroupActivity", (e-s) + " ms " +String.valueOf(result));
        tv.setText(result + "");

    }

    private Object execJs2(String jsName, String fName, Object...args) {
        Object result = null;
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        Scriptable scriptable = rhino.initStandardObjects();
        try {
            String js = DataUtil.getStrFromAssets(getAssets().open(jsName));
            rhino.evaluateString(scriptable, js, jsName, 1, null);
            Function f = (Function) scriptable.get(fName, scriptable);
            result = f.call(rhino, scriptable, scriptable, args);
        } catch (IOException e) {
            e.printStackTrace();
            Context.exit();
        }
        return result;
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_viewgroup);
        mTabLayout = (CommonTabLayout)findViewById(R.id.tab_layout);
        for(int i=0; i<3; i++){
            TextTabEntity entity = new TextTabEntity(titleArray[i]);
            mTabEntities.add(entity);
        }
        mTabLayout.setTabData(mTabEntities);
    }

    private class TextTabEntity implements CustomTabEntity{

        private String title;

        public TextTabEntity(String title) {
            this.title = title;
        }

        @Override public String getTabTitle() {
            return title;
        }

        @Override public int getTabSelectedIcon() {
            return 0;
        }

        @Override public int getTabUnselectedIcon() {
            return 0;
        }
    }
}
