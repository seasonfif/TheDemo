package com.homelink.ljrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * 创建时间：2017年06月27日12:20 <br>
 * 作者：zhangqiang <br>
 * 描述：LJRecyclerView内部处理header与footer
 */
public class WrapHeaderAdapter extends RecyclerView.Adapter implements WrapedAdapter{

  private BaseRecyclerAdapter mAdapter;

  private ArrayList<View> mHeaderViews;

  private ArrayList<View> mFootViews;

  public WrapHeaderAdapter(ArrayList<View> headerViews, ArrayList<View> footerViews,
      BaseRecyclerAdapter adapter) {
    this.mAdapter = adapter;
    this.mHeaderViews = headerViews;
    this.mFootViews = footerViews;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (getHeaderFooterByViewType(viewType) == VIEW_TYPE_HEADER){
      int headerIndex = getIndexByViewType(viewType);
      return new HeaderFooterHolder(mHeaderViews.get(headerIndex));
    }else if(getHeaderFooterByViewType(viewType) == VIEW_TYPE_FOOTER){
      int footerIndex = getIndexByViewType(viewType);
      return new HeaderFooterHolder(mFootViews.get(footerIndex));
    }
    return mAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    int numHeaders = getHeadersCount();
    if (mAdapter != null && position >= numHeaders){
      int adjPosition = position - numHeaders;
      int adapterCount = mAdapter.getItemCount();
      if (adjPosition < adapterCount){
        mAdapter.onBindViewHolder(holder, adjPosition);
        return;
      }
    }
  }

  public int getHeadersCount(){
    return mHeaderViews.size();
  }

  public int getFootersCount(){
    return mFootViews.size();
  }

  @Override public int getItemCount() {
    if (mAdapter != null){
      return getHeadersCount() + mAdapter.getItemCount() + getFootersCount();
    }else{
      return getHeadersCount() + getFootersCount();
    }
  }

  @Override public int getItemViewType(int position) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders){
      return getHeaderViewTypeByIndex(position);
    }

    int adjPosition = position - numHeaders;
    int adapterCount = 0;
    if (mAdapter != null && position >= numHeaders){
      adapterCount = mAdapter.getItemCount();
      if (adjPosition < adapterCount){
        return mAdapter.getItemViewType(adjPosition);
      }
    }
    return getFooterViewTypeByIndex(adjPosition - adapterCount);
  }

  @Override public void updateItem(int position, Object obj) {
    mAdapter.getDatas().set(position, obj);
    notifyItemChanged(getHeadersCount() + position);
  }

  @Override public void insertItem(int position, Object obj) {
    int count = mAdapter.getItemCount();
    if (position > count){
      position = count;
    }
    mAdapter.getDatas().add(position, obj);
    //fixme crash: Called attach on a child which is not detached: ViewHolder{b87577 position=7
    //notifyItemInserted(getHeadersCount() + position);
    //notifyItemRangeChanged(getHeadersCount() + position, getItemCount());
    notifyDataSetChanged();
  }

  public Object removeItem(int position){
    Object obj = mAdapter.getDatas().remove(position);
    //fixme crash: Called attach on a child which is not detached: ViewHolder{b87577 position=7
    //notifyItemRemoved(getHeadersCount() + position);
    //notifyItemRangeChanged(getHeadersCount() + position, getItemCount());
    notifyDataSetChanged();
    return obj;
  }

  /**
   * header
   */
  private static final int VIEW_TYPE_HEADER = 0x10000000;

  /**
   * footer
   */
  private static final int VIEW_TYPE_FOOTER = 0x20000000;

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
}
