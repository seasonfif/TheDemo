package com.lianjia.recyclerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.demo.R;
import com.homelink.ljrecyclerview.ItemDivider;
import com.homelink.ljrecyclerview.LJSimpleRecyclerView;
import com.homelink.ljrecyclerview.RecyclerType;
import com.homelink.ljrecyclerview.SimpleRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017年06月24日10:51 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class SimpleRecyclerViewActivity extends Activity implements LJSimpleRecyclerView.OnItemClickListener,
        LJSimpleRecyclerView.OnItemLongClickListener, LJSimpleRecyclerView.OnPullRefreshListener {

    private LJSimpleRecyclerView ljSimpleRecyclerView;
    private SimpleRecyclerAdapter simpleRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lj_baserv);
        ljSimpleRecyclerView = (LJSimpleRecyclerView) findViewById(R.id.recycler);
        //TODO 通过setRecyclerType设置初始化的RecyclerView的布局，共有6种：纵向ListView、横向ListView、纵向GrideView等。详见RecyclerType。
        //eg：初始化纵向ListView
        ljSimpleRecyclerView.setRecyclerType(RecyclerType.LINEARLAYOUT_VERTICAL);

        //eg：初始化纵向GridView
        //ljSimpleRecyclerView.setRecyclerType(RecyclerType.GRIDLAYOUT_VERTICAL);
        //设置GridView的列数
        //ljSimpleRecyclerView.setSpanCount(4);

        ljSimpleRecyclerView.addItemDecoration(new ItemDivider(20, Color.GRAY));
        ljSimpleRecyclerView.addItemDecoration(new ItemDivider(getResources().getDrawable(R.drawable.div)));
        ljSimpleRecyclerView.setOnPullRefreshListener(this);
        ljSimpleRecyclerView.setOnItemClickListener(this);
        ljSimpleRecyclerView.setOnItemLongClickListener(this);

        //TODO 禁止下拉刷新
        //ljSimpleRecyclerView.setDisablePullRefresh(true);

        simpleRecyclerAdapter = new MyAdapter();
        simpleRecyclerAdapter.setDatas(initData());
        ljSimpleRecyclerView.setAdapter(simpleRecyclerAdapter);
    }

    private List initData() {
        List data = new ArrayList();
        for (int i = 0; i <= 20; i++){
            data.add(i+"");
        }
        return data;
    }

    @Override
    public void onItemClick(View view, int position) {
        simpleRecyclerAdapter.updateItem(position, "update " + simpleRecyclerAdapter.getItem(position));
        Toast.makeText(SimpleRecyclerViewActivity.this, simpleRecyclerAdapter.getItem(position) + " has been updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(SimpleRecyclerViewActivity.this, simpleRecyclerAdapter.getItem(position) + " has been removed!", Toast.LENGTH_SHORT).show();
        simpleRecyclerAdapter.removeItem(position);
    }

    @Override
    public void onPullRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ljSimpleRecyclerView.setEnabled(true);
                ljSimpleRecyclerView.setRefreshing(false);
                simpleRecyclerAdapter.insertItem(2, "insert data");
                Toast.makeText(SimpleRecyclerViewActivity.this, " insert successed!", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    private class MyAdapter extends SimpleRecyclerAdapter<String> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recycler_item, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            String s = getItem(position);
            MyHolder myHolder = (MyHolder) holder;
            myHolder.tv.setText(s);
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
