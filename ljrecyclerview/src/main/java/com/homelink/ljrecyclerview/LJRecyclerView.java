package com.homelink.ljrecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

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
  public void setAdapter(Adapter adapter){
    initLayoutManager();
    mRecyclerView.setAdapter(adapter);
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

  private void initLayoutManager() {

    switch (mRecyclerType){
      case RecyclerType.LINEARLAYOUT_VERTICAL:
//        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, mReverseLayout);
        break;
      case RecyclerType.LINEARLAYOUT_HORIZONTAL:
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, mReverseLayout);
        setEnabled(false);
        break;
      case RecyclerType.GRIDLAYOUT_VERTICAL:
//        mLayoutManager = new GridLayoutManager(getContext(), mSpanCount);
        mLayoutManager = new GridLayoutManager(getContext(), mSpanCount, GridLayoutManager.VERTICAL, mReverseLayout);
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
}
