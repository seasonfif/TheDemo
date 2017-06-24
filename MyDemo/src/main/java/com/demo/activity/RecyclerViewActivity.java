package com.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.demo.R;
import com.demo.recycler.DividerGridItemDecoration;
import com.demo.recycler.DividerItemDecration;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017年06月24日10:51 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class RecyclerViewActivity extends BaseActivity{

  private RecyclerView recyclerView;
  private List<String> datas;

  @Override public void setView() {
    setContentView(R.layout.activity_recyclerview);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    recyclerView = (RecyclerView) findViewById(R.id.recycler);

    initData();
    //recyclerView.setLayoutManager(new LinearLayoutManager(this));
    //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
    recyclerView.setAdapter(new SAdapter());
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    //recyclerView.addItemDecoration(new DividerItemDecration(this, DividerItemDecration.VERTICAL_LIST));
    recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
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
