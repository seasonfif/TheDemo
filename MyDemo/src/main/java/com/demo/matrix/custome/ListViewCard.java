package com.demo.matrix.custome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.card.ICard;

import java.util.List;

/**
 * Created by seasonfif on 2017/5/20.
 */

@CardModel(ItemCardBean.class)
public class ListViewCard<T> extends ListView implements ICard<List<T>>{

    public static final int TYPE_LISTVIEW_CARD = 0x0003;

    private TextAdapter adapter;

    public ListViewCard(Context context) {
        this(context, null);
    }

    public ListViewCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setDivider(new ColorDrawable(Color.WHITE));
        setDividerHeight(9);
        adapter = new TextAdapter(getContext());
        setAdapter(adapter);
    }

    @Override
    public int getNestMode() {
        return NestMode.NONE;
    }

    @Override
    public void update(List<T> data) {
        adapter.setDatas(data);
    }

    @Override
    public void addCard(int index, ICard card) {

    }
}
