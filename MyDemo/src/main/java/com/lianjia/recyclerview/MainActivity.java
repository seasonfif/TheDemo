package com.lianjia.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.R;


/**
 * Created by seasonfif on 2017/7/9.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lj);

        intent = new Intent();
        findViewById(R.id.recyclerview1).setOnClickListener(this);
        findViewById(R.id.recyclerview2).setOnClickListener(this);
        findViewById(R.id.recyclerview3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recyclerview1:
                intent.setClass(MainActivity.this, SimpleRecyclerViewActivity.class);
                break;
            case R.id.recyclerview2:
                intent.setClass(MainActivity.this, HeaderRecyclerViewActivity.class);
                break;
            case R.id.recyclerview3:
                intent.setClass(MainActivity.this, PaginationRecyclerViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
