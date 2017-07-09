package com.homelink.ljrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * 创建时间：2017年06月24日17:08 <br>
 * 作者：zhangqiang <br>
 * 描述：封装LJRecyclerView
 */
public class LJPaginationWrappedRecyclerView extends LJHeaderWrappedRecyclerView{

  private static final String TAG = "LJHeaderWrappedRecyclerView";

  private PaginationWrappedAdapter mOriginalAdapter;

  public LJPaginationWrappedRecyclerView(Context context) {
    this(context, null);
  }

  public LJPaginationWrappedRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 设置adapter
   * @param adapter
   */
  public void setAdapter(PaginationWrappedAdapter adapter){
    this.mOriginalAdapter = adapter;
    if (mDisablePullRefresh){
      this.setEnabled(false);
    }else{
      this.setOnRefreshListener(new OnRefreshListener() {
        @Override
        public void onRefresh() {
          if (mOnLoadRefreshListener != null){
            mOriginalAdapter.refresh();
            mOnLoadRefreshListener.onLoadRefresh();
          }
        }
      });
    }
    initLayoutManager();
    initListener();
    mRecyclerView.addOnScrollListener(new RecyclerScrollListener());
    mOriginalAdapter.setHeaderViews(mHeaderViews);
    mOriginalAdapter.setFooterViews(mFooterViews);
    mOriginalAdapter.setEmpty(mEmpty);
    mRecyclerView.setAdapter(mOriginalAdapter);
  }

  /**
   * 获得adapter
   * @return
   */
  public PaginationWrappedAdapter getAdapter(){
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

  private class RecyclerScrollListener extends RecyclerView.OnScrollListener {

    private int lastVisibleItemPosition;
    private RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

    @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);

      if (newState != RecyclerView.SCROLL_STATE_IDLE || !mOriginalAdapter.shouldLoadMore()) return;

      if (layoutManager instanceof GridLayoutManager) {
        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
      } else if (layoutManager instanceof StaggeredGridLayoutManager) {
        int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
        lastVisibleItemPosition = findMax(into);
      } else {
        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
      }

      if (lastVisibleItemPosition + 1 == mOriginalAdapter.getItemCount()) {
        mOriginalAdapter.loadMore();
        if (mOnLoadMoreListener != null){
          mOnLoadMoreListener.onLoadMore();
        }
      }
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
    }

    private int findMax(int[] lastPositions) {
      int max = lastPositions[0];
      for (int value : lastPositions) {
        if (value > max) {
          max = value;
        }
      }
      return max;
    }
  }
}
