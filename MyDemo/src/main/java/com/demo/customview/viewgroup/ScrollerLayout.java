package com.demo.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by zhangqiang on 2017/12/11.
 */

public class ScrollerLayout extends ViewGroup{

  private static final String TAG = "ScrollerLayout";

  private Scroller scroller;
  /**
   * 最小拖动距离
   */
  private int touchSlop;
  /**
   * 左边界
   */
  private int leftBorder;
  /**
   * 右边界
   */
  private int rightBorder;
  private float mXDown;
  private float mXMove;
  private float mXLastMove;

  public ScrollerLayout(Context context) {
    this(context, null);
  }

  public ScrollerLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    scroller = new Scroller(getContext());
    ViewConfiguration configuration = ViewConfiguration.get(getContext());
    touchSlop = configuration.getScaledPagingTouchSlop();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int childCount = getChildCount();
    for (int i=0; i<childCount; i++){
      View child = getChildAt(i);
      measureChild(child, widthMeasureSpec, heightMeasureSpec);
    }
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if (changed){
      int childCount = getChildCount();
      for (int i=0; i<childCount; i++){
        View child = getChildAt(i);
        child.layout(i * child.getMeasuredWidth(), 0, (i+1) * child.getMeasuredWidth(), child.getMeasuredHeight());
      }
      // 初始化左右边界值
      leftBorder = getChildAt(0).getLeft();
      rightBorder = getChildAt(childCount - 1).getRight();
      Log.e(TAG, leftBorder + " " + rightBorder);
    }
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    switch (ev.getAction()){
      case MotionEvent.ACTION_DOWN:
        mXDown = ev.getRawX();
        mXLastMove = mXDown;
        break;
      case MotionEvent.ACTION_MOVE:
        mXMove = ev.getRawX();
        float diff = Math.abs(mXMove - mXDown);
        mXLastMove = mXMove;
        if (diff > touchSlop) return true;
        break;
    }
    return super.onInterceptTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN:
        return true;
      case MotionEvent.ACTION_MOVE:
        mXMove = event.getRawX();
        int scrollX = (int) (mXLastMove  - mXMove);
        if (getScrollX() + scrollX < leftBorder){
          scrollTo(leftBorder, 0);
          return true;
        } else if(getScrollX() + getWidth() + scrollX > rightBorder){
          scrollTo(rightBorder - getWidth(), 0);
          return true;
        }
        scrollBy(scrollX, 0);
        mXLastMove = mXMove;
        break;
      case MotionEvent.ACTION_UP:
        int targetIndex = (getScrollX() + getWidth()/2) / getWidth();
        int dx = targetIndex * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), 0, dx, 0);
        invalidate();
        break;
    }
    return super.onTouchEvent(event);
  }

  @Override public void computeScroll() {
    if (scroller.computeScrollOffset()){
      scrollTo(scroller.getCurrX(), scroller.getCurrY());
      invalidate();
    }
  }
}
