package com.homelink.ljrecyclerview;

/**
 * 创建时间：2017年06月26日16:57 <br>
 * 作者：zhangqiang <br>
 * 描述：分页管理类
 */

public class PaginationManager {

    private static final int DEFAULT_PER_SIZE = 20;
    private static final int DEFAULT_CUR_PAGE = 1;

    private boolean mSizeType = false;
    private int mTotal;
    private int mPerSize = DEFAULT_PER_SIZE;
    private int mCurPage = DEFAULT_CUR_PAGE;
    private boolean mHasMore;

    public void setTotal(int total, int perSize) {
        this.mTotal = total;
        if (perSize > 0){
            this.mPerSize = perSize;
        }
        mSizeType = true;
    }

    public int getPages(){
        return mTotal % mPerSize + 1;
    }

    public void setHasMore(boolean hasMore) {
        this.mHasMore = hasMore;
        mSizeType = false;
    }

    public boolean shouldLoadMore(){
        if (mSizeType){
            return mCurPage == getPages();
        }
        return mHasMore;
    }

    public void loadFinished(boolean successed){
        if (mSizeType && successed){
            mCurPage++;
        }
    }

    public void refreshFinished(boolean successed){
        if (mSizeType && successed){
            mCurPage = DEFAULT_CUR_PAGE;
        }
    }
}
