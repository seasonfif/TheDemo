package com.homelink.ljrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * 创建时间：2017年06月26日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：LJRecyclerView的带分页功能adapter基类
 */

public class BaseListRecyclerAdapter<D> extends BaseRecyclerAdapter{

  private PaginationManager mPaginationManager;

  public BaseListRecyclerAdapter(){
    this(null);
  }

  public BaseListRecyclerAdapter(List<D> data){
    super(data);
    mPaginationManager = new PaginationManager();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return super.getItemCount();
  }

  @Override public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }

  public void setTotal(int total, int perSize) {
    mPaginationManager.setTotal(total, perSize);
  }

  public void setHasMore(boolean hasMore) {
    mPaginationManager.setHasMore(hasMore);
  }
}
