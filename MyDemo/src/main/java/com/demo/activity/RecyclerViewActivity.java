package com.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.R;
import com.homelink.ljrecyclerview.DividerGridItemDecoration;
import com.homelink.ljrecyclerview.DividerItemDecoration;
import com.homelink.ljrecyclerview.LJRecyclerView;
import com.homelink.ljrecyclerview.RecyclerType;

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
    lJRecyclerView.setRecyclerType(RecyclerType.GRIDLAYOUT_VERTICAL);
    lJRecyclerView.setReverseLayout(true);
    lJRecyclerView.setSpanCount(4);
    initData();
    lJRecyclerView.setItemAnimator(new DefaultItemAnimator());
    lJRecyclerView.addItemDecoration(new DividerGridItemDecoration(getResources().getDrawable(R.drawable.divider)));
//    lJRecyclerView.addItemDecoration(new DividerItemDecoration(this));
    lJRecyclerView.setAdapter(new SAdapter());

    lJRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            lJRecyclerView.getRecyclerView().getAdapter().notifyDataSetChanged();
            lJRecyclerView.setRefreshing(false);
          }
        }, 3000);
      }
    });
  }

  private void initData() {
    datas = new ArrayList<>();
    for (int i = 'A'; i < 'z'; i++){
      datas.add("" + (char)i);
    }
  }

  private class SAdapter extends RecyclerView.Adapter<MyHolder> {
    @Override public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      View view = getLayoutInflater().inflate(R.layout.recycler_item, parent, false);
      MyHolder holder = new MyHolder(view);
      return holder;
    }

    @Override public void onBindViewHolder(MyHolder holder, int position) {
      String s = datas.get(position);
      holder.tv.setText(s);
    }

    @Override public int getItemCount() {
      return datas.size();
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
