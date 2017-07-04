package com.homelink.ljrecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017年06月26日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：继承自HeaderWrappedAdapter
 *        扩展分页功能
 */

public abstract class PaginationWrappedAdapter<D> extends HeaderWrappedAdapter<D>{

  /**
   * 下拉刷新
   */
  private static final int PULLREFRESH = 0x000f;


  /**
   * 上拉加载
   */
  private static final int LOADMORE = 0x00f0;

  /**
   * 标识当前刷新类型
   * 初始为下拉刷新
   */
  private int mLoadType = PULLREFRESH;

  private LoadMoreView mLoadMoreView;

  private Paginable mPaginationManager;

  public PaginationWrappedAdapter(){
    this(null);
  }

  public PaginationWrappedAdapter(Paginable manager){
    this.mPaginationManager = manager;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_LOADMORE){
      mLoadMoreView = new LoadMoreView(parent.getContext());
      LoadMoreViewHolder vh = new LoadMoreViewHolder(mLoadMoreView);
      return vh;
    }else{
      return super.onCreateViewHolder(parent, viewType);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position) == VIEW_TYPE_LOADMORE){
      //LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
      if (mLoadMoreView != null){
        mLoadMoreView.setEndText("加载更多");
        mLoadMoreView.setType(LoadMoreView.TYPE_REACH_END);
      }
    }else{
      super.onBindViewHolder(holder, position);
    }
  }

  @Override public int getItemCount() {
    if (isPaginable() && mPaginationManager.shouldLoadMore()){
      return super.getItemCount() + 1;
    }
    return super.getItemCount();
  }

  @Override public int getItemViewType(int position) {
    if (isLoadMorePos(position)){
      return VIEW_TYPE_LOADMORE;
    }else{
      return super.getItemViewType(position);
    }
  }

  protected boolean isPaginable(){
    return mPaginationManager != null;
  }

  private boolean isLoadMorePos(int position){
    return isPaginable() && mPaginationManager.shouldLoadMore() && position + 1 == getItemCount();
  }

  public boolean shouldLoadMore(){
    return isPaginable() && mPaginationManager.shouldLoadMore();
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
    if (isPaginable()){
      if (mLoadType == PULLREFRESH){
        if (data == null || data.size() == 0){
          mPaginationManager.onLoadFinished(false, false);
          //TODO 空白页面
        }else{
          mPaginationManager.onLoadFinished(false, true);
          if (mDatas != null && mDatas.size() > 0){
            mDatas.clear();
          }
          super.setDatas(data);
        }
      }else if(mLoadType == LOADMORE){
        if (data == null || data.size() == 0){
          mPaginationManager.onLoadFinished(true, false);
          mLoadMoreView.setType(LoadMoreView.TYPE_ERROR);
        }else{
          mPaginationManager.onLoadFinished(true, true);
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
