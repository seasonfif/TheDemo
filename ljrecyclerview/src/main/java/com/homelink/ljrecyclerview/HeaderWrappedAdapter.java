package com.homelink.ljrecyclerview;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017年07月03日20:51 <br>
 * 作者：zhangqiang <br>
 * 描述：继承自BaseRecyclerAdapter
 *      扩展了Header与Footer
 */

public abstract class HeaderWrappedAdapter<D> extends BaseRecyclerAdapter<D> {

  protected ArrayList<View> mHeaderViews = new ArrayList<>();

  protected ArrayList<View> mFooterViews = new ArrayList<>();

  private ArrayList<View> headerCache = new ArrayList<>();

  private ArrayList<View> footerCache = new ArrayList<>();

  protected View empty;

  private int emptyFlag;
  public static int WITH_HEADER = 0x000f;
  public static int WITH_FOOTER = 0x00f0;

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (getHeaderFooterByViewType(viewType) == VIEW_TYPE_HEADER){
      int headerIndex = getIndexByViewType(viewType);
      return new HeaderFooterHolder(mHeaderViews.get(headerIndex));
    }else if(getHeaderFooterByViewType(viewType) == VIEW_TYPE_FOOTER){
      int footerIndex = getIndexByViewType(viewType);
      return new HeaderFooterHolder(mFooterViews.get(footerIndex));
    }
    return onLJCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    int numHeaders = getHeadersCount();
    if (position >= numHeaders){
      int adjPosition = position - numHeaders;
      int adapterCount = mDatas.size();
      if (adjPosition < adapterCount){
        super.onBindViewHolder(holder, adjPosition);
        onLJBindViewHolder(holder, adjPosition);
        return;
      }
    }
  }

  protected abstract RecyclerView.ViewHolder onLJCreateViewHolder(ViewGroup parent, int viewType);

  protected abstract void onLJBindViewHolder(RecyclerView.ViewHolder holder, int adjPosition);

  @Override public void setDatas(@Nullable List<D> data) {
    if (data == null || data.size() == 0){
      //TODO 空白页面
      enableEmpty();
    }else{
      if (mDatas != null && mDatas.size() > 0){
        mDatas.clear();
      }
      disableEmpty();
      super.setDatas(data);
    }
  }

  @Override
  public int getItemViewType(int position) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders){
      return getHeaderViewTypeByIndex(position);
    }

    int adjPosition = position - numHeaders;
    int adapterCount = 0;
    if (position >= numHeaders){
      adapterCount = mDatas.size();
      if (adjPosition < adapterCount){
        return super.getItemViewType(adjPosition);
      }
    }
    return getFooterViewTypeByIndex(adjPosition - adapterCount);
  }

  @Override
  public int getItemCount() {
    return mDatas.size() + getHeadersCount() + getFootersCount();
  }

  @Override
  public int getHeadersCount(){
    return mHeaderViews.size();
  }

  @Override
  public int getFootersCount(){
    return mFooterViews.size();
  }

  /**
   * 添加header
   * @param headers
   */
  @Override
  public void setHeaderViews(ArrayList<View> headers){
    this.mHeaderViews = headers;
  }

  /**
   * 添加footer
   * @param footers
   */
  @Override
  public void setFooterViews(ArrayList<View> footers){
    this.mFooterViews = footers;
  }

  /**
   * 添加空白页面
   * @param empty
   */
  @Override public void setEmptyView(View empty) {
    this.empty = empty;
  }

  /**
   * 设置空白页区域
   * @param flag
   */
  public void setEmptyArea(int flag) {
    this.emptyFlag |= flag;
  }

  /**
   * 显示空白页面
   */
  protected void enableEmpty() {
    //UI没有数据时显示时才显示空白页面
    if (mDatas.size() >= 0 && empty != null && !mHeaderViews.contains(empty)){
      //清除已有数据
      mDatas.clear();
      if ((emptyFlag & WITH_HEADER) > 0){
        headerCache.addAll(mHeaderViews);
        mHeaderViews.clear();
      }

      if ((emptyFlag & WITH_FOOTER) > 0){
        footerCache.addAll(mFooterViews);
        mFooterViews.clear();
      }
      mHeaderViews.add(empty);
      notifyDataSetChanged();
    }
  }

  /**
   * 隐藏空白页面
   */
  protected void disableEmpty(){
    if (headerCache.size() > 0){
      mHeaderViews.addAll(headerCache);
      headerCache.clear();
    }
    if (footerCache.size() > 0){
      mFooterViews.addAll(footerCache);
      footerCache.clear();
    }
    if (empty != null){
      mHeaderViews.remove(empty);
    }
  }

  /**
   * GridLayoutManager设置header与footer的布局
   * @param recyclerView
   */
  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager){
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
      final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          int viewType = getItemViewType(position);
          if (getHeaderFooterByViewType(viewType) == VIEW_TYPE_HEADER || getHeaderFooterByViewType(viewType) == VIEW_TYPE_FOOTER
              || viewType == VIEW_TYPE_LOADMORE){
            return gridLayoutManager.getSpanCount();
          }
          if (spanSizeLookup != null){
            return spanSizeLookup.getSpanSize(position);
          }
          return 0;
        }
      });
    }
  }

  /**
   * StaggeredGridLayoutManager设置header与footer的布局
   * @param holder
   */
  @Override public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    int position = holder.getLayoutPosition();
    if (isHeaderViewPos(position) || isFooterViewPos(position)) {
      setFullSpan(holder);
    }
  }

  private boolean isHeaderViewPos(int position) {
    return position < getHeadersCount();
  }

  private boolean isFooterViewPos(int position) {
    return position >= getHeadersCount() + getItemCount();
  }

  private void setFullSpan(RecyclerView.ViewHolder holder) {
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
    if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
      StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
      p.setFullSpan(true);
    }
  }

  /**
   * 取header、footer类型
   */
  private static final int MASK_HEADER_FOOTER = 0xf0000000;

  /**
   * 取header、footer的list索引
   */
  private static final int MASK_INDEX = 0x000000ff;

  private int getHeaderViewTypeByIndex(int index){
    return index | VIEW_TYPE_HEADER;
  }

  private int getFooterViewTypeByIndex(int index){
    return index | VIEW_TYPE_FOOTER;
  }

  /**
   * 取header、footer类型
   */
  private int getHeaderFooterByViewType(int viewType){
    return viewType & MASK_HEADER_FOOTER;
  }

  /**
   * 取header、footer的list索引
   */
  private int getIndexByViewType(int viewType){
    return viewType & MASK_INDEX;
  }

  private class HeaderFooterHolder extends RecyclerView.ViewHolder {
    public HeaderFooterHolder(View view) {
      super(view);
    }
  }

  @Override public void refresh() {
  }

  @Override public void loadMore() {
  }

  @Override
  public boolean shouldLoadMore() {
    return false;
  }
}
