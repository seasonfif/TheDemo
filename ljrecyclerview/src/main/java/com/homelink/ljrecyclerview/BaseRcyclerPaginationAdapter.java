package com.homelink.ljrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * 创建时间：2017年06月26日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：LJRecyclerView的带分页功能adapter基类
 */

public abstract class BaseRcyclerPaginationAdapter<D> extends BaseRecyclerAdapter{

  private static final int VIEW_TYPE_LOADMORE = 0x1f000000;

  private PaginationManager mPaginationManager;

  public BaseRcyclerPaginationAdapter(){
    this(null);
  }

  public BaseRcyclerPaginationAdapter(List<D> data){
    mPaginationManager = new PaginationManager();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_LOADMORE){
      return null;
    }
    return onLJCreateViewHolder(parent, viewType);
  }

  public abstract RecyclerView.ViewHolder onLJCreateViewHolder(ViewGroup parent, int viewType);

  @Override public void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  private boolean isLoadMorePos(int position){
    return mPaginationManager.shouldLoadMore() && position == getItemCount();
  }

  @Override public int getItemCount() {
    if (mPaginationManager.shouldLoadMore()){
      return mDatas.size() + 1;
    }
    return mDatas.size();
  }

  @Override public int getItemViewType(int position) {
    if (position < mDatas.size()){
      return super.getItemViewType(position);
    }else{
      return VIEW_TYPE_LOADMORE;
    }
  }

  public void setTotal(int total, int perSize) {
    mPaginationManager.setTotal(total, perSize);
  }

  public void setHasMore(boolean hasMore) {
    mPaginationManager.setHasMore(hasMore);
  }
}
