package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.demo.R;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ScrollerLayoutActivity extends BaseActivity{

    private LinearLayout scroll_ll;
    private Button scroll_to;
    private Button scroll_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_scroller);
        testScroll();
    }

    private void testScroll() {
        scroll_ll = (LinearLayout) findViewById(R.id.scroll_ll);
        scroll_to = (Button) findViewById(R.id.scroll_to);
        scroll_by = (Button) findViewById(R.id.scroll_by);

        scroll_to.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //第一个参数x表示相对于当前位置横向移动的距离，正值向左移动，负值向右移动，单位是像素。
                //第二个参数y表示相对于当前位置纵向移动的距离，正值向上移动，负值向下移动，单位是像素
                scroll_ll.scrollTo(-100, -100);
            }
        });

        scroll_by.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                scroll_ll.scrollBy(-100, -100);
            }
        });
    }
}
