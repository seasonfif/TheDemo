package com.demo.activity;

import android.os.Bundle;

import com.demo.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TestViewGroupActivity extends BaseActivity{

    String[] titleArray = new String[]{"商圈排名","大区排名","城市排名"};
    private CommonTabLayout mTabLayout;
    private final ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
