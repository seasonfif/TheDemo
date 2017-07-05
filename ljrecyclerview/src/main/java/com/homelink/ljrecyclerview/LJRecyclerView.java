package com.homelink.ljrecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

/**
 * 创建时间：2017年06月24日17:08 <br>
 * 作者：zhangqiang <br>
 * 描述：封装LJRecyclerView
 */
public class LJRecyclerView extends SwipeRefreshLayout{

  private static final String TAG = "LJRecyclerView";

  private RecyclerView mRecyclerView;

  /**
   * 标识RecyclerView的布局种类
   * 见{@link RecyclerType}
   * 默认为纵向的listview
   */
  private int mRecyclerType = RecyclerType.LINEARLAYOUT_VERTICAL;

  /**
   * RecyclerView的LayoutManager
   */
  private RecyclerView.LayoutManager mLayoutManager;

  /**
   * 标识gridlayout与staggeredGridLayout显示的行数或列数
   * 纵向时为列数，横向时为行数
   * 默认为-1
   */
  private int mSpanCount = GridLayoutManager.DEFAULT_SPAN_COUNT;

  /**
   * 标识横向RecyclerView内容显示的方向
   * 为true表示右→左
   * 默认为false即表示从左至右显示
   */
  private boolean mReverseLayout = false;

  /**
   * 标识禁止下拉刷新
   */
  private boolean mDisablePullRefresh = false;

  private BaseRecyclerAdapter mOriginalAdapter;


  public LJRecyclerView(Context context) {
    this(context, null);
  }

  public LJRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mRecyclerView = new RecyclerView(context, attrs);
    addView(mRecyclerView);
  }

  /**
   * 设置RecyclerView的布局种类
   * 默认为纵向的listview
   * @param recyclerType
   */
  public void setRecyclerType(@RecyclerType int recyclerType){
    this.mRecyclerType = recyclerType;
  }

  /**
   * 标识gridlayout与staggeredGridLayout显示的行数或列数
   * 纵向时为列数，横向时为行数
   * 默认为-1
   */
  public void setSpanCount(int spanCount) {
    this.mSpanCount = spanCount;
  }

  /**
   * RecyclerView内容显示的方向
   * 只有横向布局才需要设置
   * 默认从左到右显示
   * @param reverseLayout
   */
  public void setReverseLayout(boolean reverseLayout){
    this.mReverseLayout = reverseLayout;
  }

  /**
   * 设置adapter
   * @param adapter
   */
  public void setAdapter(BaseRecyclerAdapter adapter){
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
    mRecyclerView.setAdapter(adapter);
  }

  private ArrayList<View> mHeaderViews = new ArrayList<>();

  private ArrayList<View> mFooterViews = new ArrayList<>();

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

  /**
   * 添加分割线
   * @param decoration
   */
  public void addItemDecoration(ItemDecoration decoration){
    mRecyclerView.addItemDecoration(decoration);
  }

  /**
   * 添加item动画
   * @param animator
   */
  public void setItemAnimator(ItemAnimator animator){
    mRecyclerView.setItemAnimator(animator);
  }

  public RecyclerView getRecyclerView() {
    return mRecyclerView;
  }

  /**
   * 获得adapter
   * @return
   */
  public BaseRecyclerAdapter getAdapter(){
    return mOriginalAdapter;
  }

  private void initLayoutManager() {

    switch (mRecyclerType){
      case RecyclerType.LINEARLAYOUT_VERTICAL:
        mLayoutManager = new LinearLayoutManager(getContext());
        break;
      case RecyclerType.LINEARLAYOUT_HORIZONTAL:
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, mReverseLayout);
        setEnabled(false);
        break;
      case RecyclerType.GRIDLAYOUT_VERTICAL:
        mLayoutManager = new GridLayoutManager(getContext(), mSpanCount);
        break;
      case RecyclerType.GRIDLAYOUT_HORIZONTAL:
        mLayoutManager = new GridLayoutManager(getContext(), mSpanCount, GridLayoutManager.HORIZONTAL, mReverseLayout);
        setEnabled(false);
        break;
      case RecyclerType.STAGGEREDGRIDLAYOUT_VERTICAL:
        mLayoutManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL);
        break;
      case RecyclerType.STAGGEREDGRIDLAYOUT_HORIZONTAL:
        mLayoutManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.HORIZONTAL);
        setEnabled(false);
        break;
      default:
        mLayoutManager = new LinearLayoutManager(getContext());
        break;
    }
    mRecyclerView.setLayoutManager(mLayoutManager);
  }

  /**
   * 设置是否禁止下拉刷新
   * 默认不禁止
   * @param disablePullRefresh
   */
  public void setDisablePullRefresh(boolean disablePullRefresh) {
    this.mDisablePullRefresh = disablePullRefresh;
  }


  /**
   * 监听“下拉刷新”
   */
  public interface OnLoadRefreshListener{
    void onLoadRefresh();
  }

  private OnLoadRefreshListener mOnLoadRefreshListener;

  public void setOnLoadRefreshListener(OnLoadRefreshListener onLoadRefreshListener) {
    this.mOnLoadRefreshListener = onLoadRefreshListener;
  }

  /**
   * 监听“加载更多”
   */
  public interface OnLoadMoreListener{
    void onLoadMore();
  }

  private OnLoadMoreListener mOnLoadMoreListener;

  public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
    this.mOnLoadMoreListener = onLoadMoreListener;
  }

  /**
   * item短按事件监听
   */
  public interface OnItemClickListener {

    void onItemClick(View view, int position);
  }

  /**
   * item长按事件监听
   */
  public interface OnItemLongClickListener {

    void onItemLongClick(View view, int position);
  }

  private OnItemClickListener mOnItemClickListener;

  private OnItemLongClickListener mOnItemLongClickListener;

  public void setOnItemClickListener(LJRecyclerView.OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  public void setOnItemLongClickListener(LJRecyclerView.OnItemLongClickListener onItemLongClickListener) {
    this.mOnItemLongClickListener = onItemLongClickListener;
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
