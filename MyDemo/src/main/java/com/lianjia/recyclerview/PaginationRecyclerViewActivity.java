package com.lianjia.recyclerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.R;
import com.homelink.ljrecyclerview.DividerItemDecoration;
import com.homelink.ljrecyclerview.Empty;
import com.homelink.ljrecyclerview.LJPaginationWrappedRecyclerView;
import com.homelink.ljrecyclerview.LJSimpleRecyclerView;
import com.homelink.ljrecyclerview.PaginationTotalStyleManager;
import com.homelink.ljrecyclerview.PaginationWrappedAdapter;
import com.homelink.ljrecyclerview.RecyclerType;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017年06月24日10:51 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class PaginationRecyclerViewActivity extends Activity{

    //  private RecyclerView recyclerView;
    private LJPaginationWrappedRecyclerView lJRecyclerView;
    private List<String> datas;
    private SAdapter adapter;
    private PaginationTotalStyleManager paginationTotalStyleManager;
    private final static int PER_PAGE = 10;
    private final static int TOTAL = 56;
    private int count;

    /**
     * @param savedInstanceState
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lj_pagerv);

        lJRecyclerView = (LJPaginationWrappedRecyclerView) findViewById(R.id.lJRecyclerView);
        lJRecyclerView.setRecyclerType(RecyclerType.LINEARLAYOUT_VERTICAL);
        //反转布局
        //lJRecyclerView.setReverseLayout(true);
        lJRecyclerView.setSpanCount(4);
        lJRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //lJRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        lJRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.div)));
        paginationTotalStyleManager = new PaginationTotalStyleManager(PER_PAGE);
        adapter = new SAdapter(paginationTotalStyleManager);

        lJRecyclerView.setOnItemClickListener(new LJSimpleRecyclerView.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                Toast.makeText(PaginationRecyclerViewActivity.this, "itemClick" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
                lJRecyclerView.getAdapter().updateItem(position, "str" + (position+1));
            }
        });

        lJRecyclerView.setOnItemLongClickListener(new LJSimpleRecyclerView.OnItemLongClickListener() {
            @Override public void onItemLongClick(View view, int position) {
                Toast.makeText(PaginationRecyclerViewActivity.this, "itemLongClick" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
                lJRecyclerView.getAdapter().removeItem(position);
            }
        });
        initHeader();
        lJRecyclerView.setEmptyArea(Empty.FOOTER_COVER);

        lJRecyclerView.setOnLoadRefreshListener(new LJSimpleRecyclerView.OnLoadRefreshListener() {
            @Override
            public void onLoadRefresh() {
                lJRecyclerView.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lJRecyclerView.setRefreshing(false);
                        lJRecyclerView.setEnabled(true);
                        ArrayList list = initData(1);
                        if (list.size() > 0){
                            //设置分页参数
                            paginationTotalStyleManager.setTotal(TOTAL);
                        }else{
                            paginationTotalStyleManager.setTotal(0);
                        }
                        adapter.setDatas(list);
                    }
                }, 3000);
            }
        });

        lJRecyclerView.setOnLoadMoreListener(new LJPaginationWrappedRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                lJRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setDatas(initData(paginationTotalStyleManager.getCurrentPage() * PER_PAGE + 1));
                    }
                }, 3000);
            }
        });
        lJRecyclerView.setAdapter(adapter);

        datas = initData(1);
        if (datas.size() > 0){
            //设置分页参数
            paginationTotalStyleManager.setTotal(TOTAL);
        }
        adapter.setDatas(datas);
    }

    private TextView tv1;
    private TextView f2;

    private void initHeader() {
        tv1 = new TextView(this);
        tv1.setBackgroundColor(Color.RED);
        tv1.setTextColor(Color.WHITE);
        tv1.setText("header1");
        tv1.setGravity(Gravity.CENTER);
        tv1.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_50)));
        lJRecyclerView.addHeaderView(tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                lJRecyclerView.getAdapter().insertItem(2, "inserted");
            }
        });

        TextView tv2 = new TextView(this);
        tv2.setBackgroundColor(Color.GREEN);
        tv2.setTextColor(Color.WHITE);
        tv2.setText("header2");
        tv2.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_70)));
        lJRecyclerView.addHeaderView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setBackgroundColor(Color.BLUE);
        tv3.setTextColor(Color.WHITE);
        tv3.setText("header3");
        tv3.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_90)));
        lJRecyclerView.addHeaderView(tv3);

        TextView f1 = new TextView(this);
        f1.setBackgroundColor(Color.BLUE);
        f1.setTextColor(Color.WHITE);
        f1.setText("footer1");
        lJRecyclerView.addFooterView(f1);

        f2 = new TextView(this);
        f2.setBackgroundColor(Color.BLUE);
        f2.setTextColor(Color.WHITE);
        f2.setText("footer2");
        f2.setGravity(Gravity.CENTER);
        f2.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_50)));
        lJRecyclerView.addFooterView(f2);

        initEmptyView();
    }

    private void initEmptyView() {
        TextView empty = new TextView(this);
        empty.setBackgroundColor(Color.GRAY);
        empty.setTextColor(Color.WHITE);
        empty.setText("Empty");
        empty.setGravity(Gravity.CENTER);
        //mEmpty.setPadding(100,100,100,100);
        LinearLayoutCompat.LayoutParams lp =
                new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.dimen_250));
        /*lp.topMargin = 100;
        lp.bottomMargin = 100;
        lp.leftMargin = 100;
        lp.rightMargin = 100;*/
        empty.setLayoutParams(lp);
        lJRecyclerView.setEmptyView(empty);
    }

    private ArrayList initData(int start) {
        ArrayList list = new ArrayList<>();
        int end = start + PER_PAGE;
        if (end > TOTAL){
            end = TOTAL;
        }

        if (Utils.isConnected(PaginationRecyclerViewActivity.this)){
            for (int i = start; i < end; i++){
                list.add("" + i);
            }
        }
        return list;
    }

    private class SAdapter extends PaginationWrappedAdapter<String> {

        public SAdapter(){
            super();
        }

        public SAdapter(PaginationTotalStyleManager manager){
            super(manager);
        }

        @Override
        public MyHolder onLJCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recycler_item, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            String s = getItem(position);
            MyHolder myHolder = (MyHolder) holder;
            myHolder.tv.setText(s);
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
