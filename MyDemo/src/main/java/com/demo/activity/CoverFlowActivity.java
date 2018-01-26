package com.demo.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.demo.R;
import com.demo.customview.viewgroup.CoverflowView;

public class CoverFlowActivity extends BaseActivity {

    @Override public void setView() {
        setContentView(R.layout.activity_cover_flow);
        CoverflowView coverflowView = (CoverflowView) findViewById(R.id.coverflowview);
        coverflowView.setAdapter(new MyAdapter());
        coverflowView.setSelection(2);
    }

    private class MyAdapter extends BaseAdapter {

         @Override
         public int getCount() {
             return 5;
         }

         @Override
         public Object getItem(int position) {
             return position;
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             View view = null;
             if (convertView == null) {
                 view = LayoutInflater.from(CoverFlowActivity.this).inflate(R.layout.item_layout_cover_flow, null);
             } else {
                 view = convertView;
             }
             return view;
         }
     }
}
