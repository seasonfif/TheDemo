package com.homelink.ljrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 创建时间：2017年06月24日17:08 <br>
 * 作者：zhangqiang <br>
 * 描述：封装LJRecyclerView
 */
public class LJHeaderWrappedRecyclerView extends LJRecyclerView{

  private static final String TAG = "LJHeaderWrappedRecyclerView";

  private HeaderWrappedAdapter mOriginalAdapter;

  protected ArrayList<View> mHeaderViews = new ArrayList<>();

  protected ArrayList<View> mFooterViews = new ArrayList<>();

  protected View mEmpty;

  public LJHeaderWrappedRecyclerView(Context context) {
    this(context, null);
  }

  public LJHeaderWrappedRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 设置adapter
   * @param adapter
   */
  public void setAdapter(HeaderWrappedAdapter adapter){
    this.mOriginalAdapter = adapter;
    if (mDisablePullRefresh){
      this.setEnabled(false);
    }else{
      this.setOnRefreshListener(new OnRefreshListener() {
        @Override
        public void onRefresh() {
          if (mOnLoadRefreshListener != null){
            mOnLoadRefreshListener.onLoadRefresh();
          }
        }
      });
    }
    initLayoutManager();
    initListener();
    mOriginalAdapter.setHeaderViews(mHeaderViews);
    mOriginalAdapter.setFooterViews(mFooterViews);
    mOriginalAdapter.setEmpty(mEmpty);
    mRecyclerView.setAdapter(mOriginalAdapter);
  }

  /**
   * 添加header
   * @param header
   */
  public void addHeaderView(View header){
    mHeaderViews.add(header);
  }

  /**
   * 添加footer
   * @param footer
   */
  public void addFooterView(View footer){
    mFooterViews.add(footer);
  }

  public void setEmptyView(View empty){
    this.mEmpty = empty;
  }


  /**
   * 获得adapter
   * @return
   */
  public HeaderWrappedAdapter getAdapter(){
    return mOriginalAdapter;
  }

  private void initListener() {
    if (mOnItemClickListener != null){
      mOriginalAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    if (mOnItemLongClickListener != null){
      mOriginalAdapter.setOnItemLongClickListener(mOnItemLongClickListener);
    }
  }
}
