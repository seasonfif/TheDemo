package com.demo.template;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.demo.R;
import com.demo.activity.BaseActivity;
import com.demo.util.DataUtil;
import com.homelink.ljrecyclerview.BaseRecyclerAdapter;
import com.homelink.ljrecyclerview.DividerItemDecoration;
import com.homelink.ljrecyclerview.LJRecyclerView;
import com.homelink.ljrecyclerview.RecyclerType;
import java.io.IOException;
import java.util.List;

/**
 * 创建时间：2017年06月29日11:10 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class HouseTemplateActivity extends BaseActivity{

  private LJRecyclerView lJRecyclerView;
  private Object result;
  private List<UiModel> datas;
  private HouseAdapter adapter;

  @Override public void setView() {
    setContentView(R.layout.activity_housetemplate);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    lJRecyclerView = (LJRecyclerView) findViewById(R.id.recycler);
    //lJRecyclerView.setRecyclerType(RecyclerType.LINEARLAYOUT_HORIZONTAL);
    //lJRecyclerView.addItemDecoration(new DividerItemDecoration(60, Color.RED));
    lJRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.div)));
    datas = getData();
    adapter = new HouseAdapter(datas);
    lJRecyclerView.setAdapter(adapter);
  }

  private List<UiModel> getData() {
    List<UiModel> lists = null;
    String json;
    try {
      json = DataUtil.getStrFromAssets(getAssets().open("house.json"));
    } catch (IOException e) {
      json = "";
      e.printStackTrace();
    }

    if (!TextUtils.isEmpty(json)){
      long s = System.currentTimeMillis();
      result = execJs("house.js", "transfer", new Object[]{json});
      long e = System.currentTimeMillis();
      Log.e("HouseTemplateActivity", (e-s) + " ms ");
      lists = GsonHelper.formatList((String) result, UiModel.class);
    }
    return lists;
  }

  private Object execJs(String jsName, String fName, Object...args) {
    Object result = null;
    try {
      String js = DataUtil.getStrFromAssets(getAssets().open(jsName));
      result = JsEngine.getEngine().exec(js, jsName, fName, args);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  class HouseAdapter extends BaseRecyclerAdapter<UiModel>{

    public HouseAdapter(List<UiModel> datas) {
      setDatas(datas);
    }

    @Override public void onLJBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      UiModel model = getItem(position);
      HouseHolder houseHolder = (HouseHolder) holder;
      houseHolder.bindData(model);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = getLayoutInflater().inflate(R.layout.item, parent, false);
      return new HouseHolder(view);
    }
  }

  private class HouseHolder extends RecyclerView.ViewHolder {
    ImageView image1;
    TextView text1, text2, text3, text4;
    public HouseHolder(View view) {
      super(view);
      image1 = (ImageView) view.findViewById(R.id.house_img);
      text1 = (TextView) view.findViewById(R.id.house_title);
      text2 = (TextView) view.findViewById(R.id.house_info);
      text3 = (TextView) view.findViewById(R.id.house_price);
      text4 = (TextView) view.findViewById(R.id.house_unit_price);
    }

    public void bindData(UiModel model) {
      //image1
      text1.setText(model.text1);
      text2.setText(model.text2);
      text3.setText(model.text3);
      text4.setText(model.text4);
    }
  }
}
