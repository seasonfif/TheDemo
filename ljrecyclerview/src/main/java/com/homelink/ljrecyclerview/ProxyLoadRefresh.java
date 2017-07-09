package com.homelink.ljrecyclerview;

import android.util.Log;

/**
 * 创建时间：2017年07月09日16:45 <br>
 * 作者：zhangqiang <br>
 * 描述：下拉刷新代理
 *      下拉刷新发生时对业务的逻辑应该在此处统一进行处理，以免污染view中的刷新
 *      可统计刷新时长、埋点等
 */
public class ProxyLoadRefresh implements LJSimpleRecyclerView.OnLoadRefreshListener{

    private LJSimpleRecyclerView.OnLoadRefreshListener mListener;

    public ProxyLoadRefresh(LJSimpleRecyclerView.OnLoadRefreshListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onLoadRefresh() {
        Log.e("onLoadRefresh", "start");
        mListener.onLoadRefresh();
        Log.e("onLoadRefresh", "finished");
    }
}
