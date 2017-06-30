package com.homelink.ljrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 创建时间：2017年06月26日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：LJRecyclerView的带分页功能adapter基类
 */

public class RecyclerPaginationAdapter<D> extends BaseRecyclerAdapter{

  /**
   * “加载更多”的viewtype
   */
  private static final int VIEW_TYPE_LOADMORE = 0x1f000000;

  /**
   * 上拉加载更多
   */
  private static final int PULLUP_LOAD_MORE = 1;

  /**
   * 正在加载中
   */
  private static final int LOADING = 2;

  private int mStatus = PULLUP_LOAD_MORE;

  private LoadMoreView mLoadMoreView;

  private boolean mIsPagination;
  private PaginationManager mPaginationManager;

  public RecyclerPaginationAdapter(){
    this(null);
  }

  public RecyclerPaginationAdapter(PaginationManager manager){
    this.mPaginationManager = manager;
    if (mPaginationManager != null){
      mIsPagination = true;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_LOADMORE){
      mLoadMoreView = new LoadMoreView(parent.getContext());
      LoadMoreViewHolder vh = new LoadMoreViewHolder(mLoadMoreView);
      return vh;
    }
    return onLJCreateViewHolder(parent, viewType);
  }

  private RecyclerView.ViewHolder onLJCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position) == VIEW_TYPE_LOADMORE){
      //LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
      switch (mStatus){
        case PULLUP_LOAD_MORE:
          mLoadMoreView.setEndText("load more");
          mLoadMoreView.setType(LoadMoreView.TYPE_REACH_END);
          break;
        case LOADING:
          mLoadMoreView.setType(LoadMoreView.TYPE_LOADING);
          break;
      }
    }else{
      onLJCustomerBindViewHolder(holder, position);
    }
  }

  private void onLJCustomerBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    if (mIsPagination && mPaginationManager.shouldLoadMore()){
      return mDatas.size() + 1;
    }
    return mDatas.size();
  }

  @Override public int getItemViewType(int position) {
    if (isLoadMorePos(position)){
      return VIEW_TYPE_LOADMORE;
    }else{
      return super.getItemViewType(position);
    }
  }

  private boolean isLoadMorePos(int position){
    return mIsPagination && mPaginationManager.shouldLoadMore() && position + 1 == getItemCount();
  }

  public void setTotal(int total, int perSize) {
    mPaginationManager.setTotal(total, perSize);
  }

  public void setHasMore(boolean hasMore) {
    mPaginationManager.setHasMore(hasMore);
  }

  public int getPageIndex(){
    return mPaginationManager.getPageIndex();
  }

  private class LoadMoreViewHolder extends RecyclerView.ViewHolder{
    public LoadMoreViewHolder(LoadMoreView loadMoreView) {
      super(loadMoreView);
    }
  }
}
