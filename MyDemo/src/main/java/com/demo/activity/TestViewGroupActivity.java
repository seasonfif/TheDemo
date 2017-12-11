package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.demo.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TestViewGroupActivity extends BaseActivity{

    //String[] titleArray = new String[]{"商圈排名","大区排名","城市排名"};
    String[] titleArray = new String[]{"文档","图片"};
    private CommonTabLayout mTabLayout, mTabLayout2;
    private final ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private Button scroll_ll;
    private Button cover_flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_viewgroup);
        testScroll();
        testTabLayout();
    }

    private void testScroll() {
        scroll_ll = (Button) findViewById(R.id.scroller_ll);
        cover_flow = (Button) findViewById(R.id.cover_flow);

        scroll_ll.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                goToOthers(ScrollerLayoutActivity.class);
            }
        });

        cover_flow.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                goToOthers(CoverFlowActivity.class);
            }
        });
    }

    private void testTabLayout() {
        mTabLayout = (CommonTabLayout)findViewById(R.id.tab_layout);
        mTabLayout2 = (CommonTabLayout)findViewById(R.id.tab_layout2);
        for(int i=0; i<2; i++){
            TextTabEntity entity = new TextTabEntity(titleArray[i]);
            mTabEntities.add(entity);
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout2.setTabData(mTabEntities);
        mTabLayout2.showDot(0);
        mTabLayout2.showDot(1);
        // NOTE: 2017/10/17  setMsgMargin必须在showDot之后
        mTabLayout2.setMsgMargin(0, 5, -9);
        mTabLayout2.setMsgMargin(1, 5, -9);

        mTabLayout2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override public void onTabSelect(int position) {

            }

            @Override public void onTabReselect(int position) {
                mTabLayout2.hideMsg(position);
            }
        });
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
