package com.homelink.ljrecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建时间：2017年06月26日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：LJRecyclerView的带分页功能adapter基类
 */

public class RecyclerPaginationAdapter<D> extends BaseRecyclerAdapter<D>{

  /**
   * “加载更多”的viewtype
   */
  private static final int VIEW_TYPE_LOADMORE = 0x1f000000;

  /**
   * 下拉刷新
   */
  private static final int PULLREFRESH = 1;


  /**
   * 上拉加载
   */
  private static final int LOADMORE = 2;

  private int mLoadType = PULLREFRESH;

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

  protected RecyclerView.ViewHolder onLJCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!isLoadMorePos(position)){
      super.onBindViewHolder(holder, position);
    }
    if (getItemViewType(position) == VIEW_TYPE_LOADMORE){
      //LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
      if (mLoadMoreView != null){
        mLoadMoreView.setEndText("load more");
        mLoadMoreView.setType(LoadMoreView.TYPE_REACH_END);
      }
    }else{
      onLJBindViewHolder(holder, position);
    }
  }

  protected void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position){};

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

  public boolean shouldLoadMore(){
    return mIsPagination && mPaginationManager.shouldLoadMore();
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

  public void refresh() {
    mLoadType = PULLREFRESH;

  }

  public void loadMore() {
    mLoadType = LOADMORE;
    mLoadMoreView.setType(LoadMoreView.TYPE_LOADING);
  }

  @Override
  public void setDatas(@Nullable List data) {
    if (mIsPagination){
      if (mLoadType == PULLREFRESH){
        if (data == null || data.size() == 0){
          mPaginationManager.refreshFinished(false);
          //TODO 空白页面
        }else{
          mPaginationManager.refreshFinished(true);
          if (mDatas != null && mDatas.size() > 0){
            mDatas.clear();
          }
          super.setDatas(data);
        }
      }else if(mLoadType == LOADMORE){
        if (data == null || data.size() == 0){
          mPaginationManager.loadMoreFinished(false);
          mLoadMoreView.setType(LoadMoreView.TYPE_ERROR);
        }else{
          mPaginationManager.loadMoreFinished(true);
          mDatas.addAll(data);
          notifyDataSetChanged();
        }
      }
    }else{
      if (data == null || data.size() == 0){
        //TODO 空白页面
      }else{
        super.setDatas(data);
      }
    }
  }

  private class LoadMoreViewHolder extends RecyclerView.ViewHolder{
    public LoadMoreViewHolder(LoadMoreView loadMoreView) {
      super(loadMoreView);
    }
  }
}
