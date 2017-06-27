package com.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import com.demo.R;
import com.homelink.ljrecyclerview.BaseRecyclerAdapter;
import com.homelink.ljrecyclerview.DividerGridItemDecoration;
import com.homelink.ljrecyclerview.DividerItemDecoration;
import com.homelink.ljrecyclerview.LJRecyclerView;
import com.homelink.ljrecyclerview.RecyclerType;

import com.homelink.ljrecyclerview.WrapedAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017年06月24日10:51 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class RecyclerViewActivity extends BaseActivity{

//  private RecyclerView recyclerView;
  private LJRecyclerView lJRecyclerView;
  private List<String> datas;
  private int count;

  @Override public void setView() {
    setContentView(R.layout.activity_recyclerview);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /*recyclerView = (RecyclerView) findViewById(R.id.recycler);

    initData();
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    recyclerView.setAdapter(new SAdapter());*/
//    lJRecyclerView = new LJRecyclerView(this);
    lJRecyclerView = (LJRecyclerView) findViewById(R.id.lJRecyclerView);
    lJRecyclerView.setRecyclerType(RecyclerType.LINEARLAYOUT_VERTICAL);
    lJRecyclerView.setReverseLayout(true);
    lJRecyclerView.setSpanCount(4);
    initData();
    lJRecyclerView.setItemAnimator(new DefaultItemAnimator());
    lJRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//    lJRecyclerView.addItemDecoration(new DividerItemDecoration(this));
    final SAdapter adapter = new SAdapter();
    adapter.setDatas(datas);
    adapter.setOnItemClickListener(new LJRecyclerView.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        Toast.makeText(RecyclerViewActivity.this, "itemClick" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        ((WrapedAdapter)lJRecyclerView.getAdapter()).updateItem(position, "str" + (position+1));
      }
    });

    adapter.setOnItemLongClickListener(new LJRecyclerView.OnItemLongClickListener() {
      @Override public void onItemLongClick(View view, int position) {
        Toast.makeText(RecyclerViewActivity.this, "itemLongClick" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        ((WrapedAdapter)lJRecyclerView.getAdapter()).removeItem(position);
      }
    });
    initHeader();
    lJRecyclerView.setAdapter(adapter);

    lJRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            /*count++;
            tv1.setText("refresh : "+ count);
            f2.setText("refresh : "+ count);*/
            ((WrapedAdapter)lJRecyclerView.getAdapter()).insertItem(3, "inserted");
            lJRecyclerView.setRefreshing(false);
          }
        }, 3000);
      }
    });
  }

  private TextView tv1;
  private TextView f2;

  private void initHeader() {
    tv1 = new TextView(this);
    tv1.setBackgroundColor(Color.BLUE);
    tv1.setTextColor(Color.WHITE);
    tv1.setText("header1");
    tv1.setGravity(Gravity.CENTER);
    tv1.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_50)));
    lJRecyclerView.addHeaderView(tv1);
    tv1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((WrapedAdapter)lJRecyclerView.getAdapter()).insertItem(2, "inserted");
      }
    });

    /*TextView tv2 = new TextView(this);
    tv2.setBackgroundColor(Color.BLUE);
    tv2.setTextColor(Color.WHITE);
    tv2.setText("header2");
    lJRecyclerView.addHeaderView(tv2);

    TextView tv3 = new TextView(this);
    tv3.setBackgroundColor(Color.BLUE);
    tv3.setTextColor(Color.WHITE);
    tv3.setText("header3");
    lJRecyclerView.addHeaderView(tv3);

    TextView f1 = new TextView(this);
    f1.setBackgroundColor(Color.BLUE);
    f1.setTextColor(Color.WHITE);
    f1.setText("footer1");
    lJRecyclerView.addFooterView(f1);*/

    f2 = new TextView(this);
    f2.setBackgroundColor(Color.BLUE);
    f2.setTextColor(Color.WHITE);
    f2.setText("footer2");
    f2.setGravity(Gravity.CENTER);
    f2.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_50)));
    lJRecyclerView.addFooterView(f2);
  }

  private void initData() {
    datas = new ArrayList<>();
    /*for (int i = 'A'; i < 'z'; i++){
      datas.add("" + (char)i);
    }*/
    for (int i = 1; i < 10; i++){
      datas.add("" + i);
    }
  }

  private class SAdapter extends BaseRecyclerAdapter<String> {

    @Override public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = getLayoutInflater().inflate(R.layout.recycler_item, parent, false);
      MyHolder holder = new MyHolder(view);
      return holder;
    }

    @Override public void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
