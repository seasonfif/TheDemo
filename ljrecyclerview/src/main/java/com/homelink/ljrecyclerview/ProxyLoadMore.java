package com.homelink.ljrecyclerview;

import android.util.Log;

/**
 * 创建时间：2017年07月09日16:45 <br>
 * 作者：zhangqiang <br>
 * 描述：上拉加载代理
 *      上拉加载发生时对业务的逻辑应该在此处统一进行处理，以免污染view中的刷新
 */
public class ProxyLoadMore implements LJPaginationWrappedRecyclerView.OnLoadMoreListener{

    private LJPaginationWrappedRecyclerView.OnLoadMoreListener mListener;

    public void setListener(LJPaginationWrappedRecyclerView.OnLoadMoreListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onLoadMore() {
        mListener.onLoadMore();
    }
}
