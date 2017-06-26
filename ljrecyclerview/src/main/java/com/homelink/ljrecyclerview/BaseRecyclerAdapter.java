package com.homelink.ljrecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 创建时间：2017年06月26日14:57 <br>
 * 作者：zhangqiang <br>
 * 描述：LJRecyclerView的adapter基类
 *      处理了点击事件的绑定
 */

public abstract class BaseRecyclerAdapter<D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

  private List<D> mDatas = new ArrayList<>();

  private LJRecyclerView.OnItemClickListener mOnItemClickListener;

  private LJRecyclerView.OnItemLongClickListener mOnItemLongClickListener;

  public BaseRecyclerAdapter() {}

  public BaseRecyclerAdapter(List<D> data) {
    this.mDatas = data;
  }

  public void setDatas(@Nullable List<D> data){
    this.mDatas = data;
    notifyDataSetChanged();
  }

  @Override public void onBindViewHolder(final VH holder, final int position) {
    if (mOnItemClickListener != null){
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mOnItemClickListener.onItemClick(holder.itemView, position);
        }
      });
    }
    if (mOnItemLongClickListener != null){
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
          mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
          return false;
        }
      });
    }
    onLJBindViewHolder(holder, position);
  }

  public abstract void onLJBindViewHolder(VH holder, int position);

  @Override public int getItemCount() {
    return isEmpty() ? 0 : mDatas.size();
  }

  public boolean isEmpty(){
    return mDatas == null || mDatas.size() == 0;
  }

  public D getItem(int position){
    if (!isEmpty()){
      return mDatas.get(position);
    }
    return null;
  }

  public void updateItem(int position, D d){
    mDatas.set(position, d);
    notifyItemChanged(position);
  }

  public void setOnItemClickListener(LJRecyclerView.OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  public void setOnItemLongClickListener(LJRecyclerView.OnItemLongClickListener onItemLongClickListener) {
    this.mOnItemLongClickListener = onItemLongClickListener;
  }
}
