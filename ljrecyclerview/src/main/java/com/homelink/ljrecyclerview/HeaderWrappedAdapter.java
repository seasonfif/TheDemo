package com.homelink.ljrecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

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

  protected ArrayList<View> mEmptyViews = new ArrayList<>();

  private ArrayList<View> headerCache = new ArrayList<>();

  private ArrayList<View> footerCache = new ArrayList<>();

  protected View empty;

  private int emptyFlag;

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (getHeaderFooterByViewType(viewType) == VIEW_TYPE_HEADER){
      int headerIndex = getIndexByViewType(viewType);
      return new HeaderFooterHolder(mHeaderViews.get(headerIndex));
    }else if(getHeaderFooterByViewType(viewType) == VIEW_TYPE_FOOTER){
      int footerIndex = getIndexByViewType(viewType);
      return new HeaderFooterHolder(mFooterViews.get(footerIndex));
    }else if(viewType == VIEW_TYPE_EMPTY){
      return new EmptyViewHolder(empty);
    }
    return onLJCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    int numHeaders = getHeadersCount() + getEmptyCount();
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

    if (getEmptyCount() > 0 && position == numHeaders){
      return VIEW_TYPE_EMPTY;
    }

    int adjPosition = position - numHeaders - getEmptyCount();
    int adapterCount = 0;
    if (position >= numHeaders+getEmptyCount()){
      adapterCount = mDatas.size();
      if (adjPosition < adapterCount){
        return super.getItemViewType(adjPosition);
      }
    }
    return getFooterViewTypeByIndex(adjPosition - adapterCount);
  }

  @Override
  public int getItemCount() {
    return mDatas.size() + getHeadersCount() + getFootersCount() + getEmptyCount();
  }

  @Override
  public int getHeadersCount(){
    return mHeaderViews.size();
  }

  @Override
  public int getFootersCount(){
    return mFooterViews.size();
  }

  public int getEmptyCount(){
    return mEmptyViews.size();
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
  @Override public void setEmpty(View empty) {
    this.empty = empty;
  }

  /**
   * 设置空白页区域
   * @param flag
   */
  public void setEmpty(@Empty int flag) {
    this.emptyFlag |= flag;
  }

  /**
   * 显示空白页面
   */
  protected void enableEmpty() {
    //设置过empty并且当前没有显示时才会显示空白页面
    if (empty != null && getEmptyCount() == 0){
      //清除已有数据
      mDatas.clear();
      if ((emptyFlag & Empty.HEADER_COVER) > 0){
        headerCache.addAll(mHeaderViews);
        mHeaderViews.clear();
      }

      if ((emptyFlag & Empty.FOOTER_COVER) > 0){
        footerCache.addAll(mFooterViews);
        mFooterViews.clear();
      }
      mEmptyViews.add(empty);
      notifyDataSetChanged();
    }
  }

  /**
   * 隐藏空白页面
   */
  protected void disableEmpty(){

    if (empty == null) return;
    if (headerCache.size() > 0){
      mHeaderViews.addAll(headerCache);
      headerCache.clear();
    }
    if (footerCache.size() > 0){
      mFooterViews.addAll(footerCache);
      footerCache.clear();
    }
    mEmptyViews.remove(empty);
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

  private class EmptyViewHolder extends RecyclerView.ViewHolder {
    public EmptyViewHolder(View view) {
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
