package com.homelink.ljrecyclerview;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.homelink.ljrecyclerview.RecyclerType.LINEARLAYOUT_VERTICAL;
import static com.homelink.ljrecyclerview.RecyclerType.LINEARLAYOUT_HORIZONTAL;
import static com.homelink.ljrecyclerview.RecyclerType.GRIDLAYOUT_VERTICAL;
import static com.homelink.ljrecyclerview.RecyclerType.GRIDLAYOUT_HORIZONTAL;
import static com.homelink.ljrecyclerview.RecyclerType.STAGGEREDGRIDLAYOUT_VERTICAL;
import static com.homelink.ljrecyclerview.RecyclerType.STAGGEREDGRIDLAYOUT_HORIZONTAL;

/**
 * 创建时间：2017年06月24日17:08 <br>
 * 作者：zhangqiang <br>
 * 描述：RecyclerView的布局管理器种类
 */

@IntDef({LINEARLAYOUT_VERTICAL,
        LINEARLAYOUT_HORIZONTAL,
        GRIDLAYOUT_VERTICAL,
        GRIDLAYOUT_HORIZONTAL,
        STAGGEREDGRIDLAYOUT_VERTICAL,
        STAGGEREDGRIDLAYOUT_HORIZONTAL})
@Retention(RetentionPolicy.SOURCE)
public @interface RecyclerType {

    /**
     * 纵向listview
     */
    int LINEARLAYOUT_VERTICAL = 0x0001;

    /**
     * 横向listview
     */
    int LINEARLAYOUT_HORIZONTAL = 0x0002;

    /**
     * 纵向gridview
     */
    int GRIDLAYOUT_VERTICAL = 0x0003;

    /**
     * 横向gridview
     */
    int GRIDLAYOUT_HORIZONTAL = 0x0004;

    /**
     * 纵向瀑布流
     */
    int STAGGEREDGRIDLAYOUT_VERTICAL = 0x0005;

    /**
     * 横向瀑布流
     */
    int STAGGEREDGRIDLAYOUT_HORIZONTAL = 0x0006;

}
