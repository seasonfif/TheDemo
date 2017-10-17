package com.lianjia.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.demo.R;
import com.demo.activity.BaseActivity;

/**
 * 创建时间：2017年06月24日10:51 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class ChoiceActivity extends BaseActivity{

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent();
        findViewById(R.id.recyclerview1).setOnClickListener(this);
        findViewById(R.id.recyclerview2).setOnClickListener(this);
        findViewById(R.id.recyclerview3).setOnClickListener(this);
    }

    @Override public void setView() {
        setContentView(R.layout.activity_lj);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.recyclerview1:
                intent.setClass(ChoiceActivity.this, SimpleRecyclerViewActivity.class);
                break;
            case R.id.recyclerview2:
                intent.setClass(ChoiceActivity.this, HeaderRecyclerViewActivity.class);
                break;
            case R.id.recyclerview3:
                intent.setClass(ChoiceActivity.this, PaginationRecyclerViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
