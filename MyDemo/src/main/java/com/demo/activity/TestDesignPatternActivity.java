package com.demo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.demo.R;
import com.demo.designpattern.chainofresponsibility.nonpure.CEOIntercept;
import com.demo.designpattern.chainofresponsibility.nonpure.Interceptor;
import com.demo.designpattern.chainofresponsibility.nonpure.MemberIntercept;
import com.demo.designpattern.chainofresponsibility.nonpure.RealChain;
import com.demo.designpattern.chainofresponsibility.nonpure.VPIntercept;
import com.demo.designpattern.chainofresponsibility.pure.CEOChain;
import com.demo.designpattern.chainofresponsibility.pure.ManagerChain;
import com.demo.designpattern.chainofresponsibility.pure.MemberChain;
import com.demo.designpattern.chainofresponsibility.pure.VPChain;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public class TestDesignPatternActivity extends BaseActivity{

  private EditText editText;
  private Button btn, btnNon;
  private MemberChain memberChain;
  private RealChain realChain;

  @Override public void setView() {
    setContentView(R.layout.activity_design_pattern);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    editText = (EditText) findViewById(R.id.edit);
    btn = (Button) findViewById(R.id.button);
    btn.setOnClickListener(this);

    btnNon = (Button) findViewById(R.id.btn_non);
    btnNon.setOnClickListener(this);

    initChainHandler();
    initNonPureChain();

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

  private void initNonPureChain() {
    List<Interceptor> interceptors = new ArrayList<>();
    interceptors.add(new MemberIntercept());
    interceptors.add(new VPIntercept());
    interceptors.add(new CEOIntercept());
    realChain = new RealChain(interceptors, 0, "seasonfif");
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
      case R.id.btn_non:
        realChain.procced(6000, "seasonfif");
        break;
    }
  }
}
