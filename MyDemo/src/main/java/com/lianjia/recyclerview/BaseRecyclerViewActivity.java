package com.lianjia.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.demo.R;

/**
 * Created by seasonfif on 2017/7/9.
 */
public class BaseRecyclerViewActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lj_baserv);
    }
}
