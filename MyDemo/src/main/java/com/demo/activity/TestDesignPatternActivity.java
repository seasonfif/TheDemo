package com.demo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.demo.R;
import com.demo.designpattern.chainofresponsibility.CEOChain;
import com.demo.designpattern.chainofresponsibility.ManagerChain;
import com.demo.designpattern.chainofresponsibility.MemberChain;
import com.demo.designpattern.chainofresponsibility.VPChain;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public class TestDesignPatternActivity extends BaseActivity{

  private EditText editText;
  private Button btn;
  private MemberChain memberChain;

  @Override public void setView() {
    setContentView(R.layout.activity_design_pattern);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    editText = (EditText) findViewById(R.id.edit);
    btn = (Button) findViewById(R.id.button);
    btn.setOnClickListener(this);

    initChainHandler();

  }

  private void initChainHandler() {
    memberChain = new MemberChain();
    ManagerChain managerChain = new ManagerChain();
    VPChain vpChain = new VPChain();
    CEOChain ceoChain = new CEOChain();

    memberChain.setChainHandler(managerChain);
    managerChain.setChainHandler(vpChain);
    vpChain.setChainHandler(ceoChain);
  }

  @Override public void onClick(View v) {
    super.onClick(v);
    switch (v.getId()){
      case R.id.button:
        String str = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(str)){
          memberChain.handle(Integer.valueOf(str));
        }
        break;
    }
  }
}
