package com.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.widget.TextView;

import com.demo.R;
import com.demo.designpattern.chainofresponsibility.nonpure.Interceptor;
import com.demo.designpattern.chainofresponsibility.nonpure.RealChain;

import org.apache.lucene.util.RamUsageEstimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangqiang on 2018/3/26.
 */

public class TestRamUsageActivity extends BaseActivity{

  private TextView tv;

  @Override public void setView() {
    setContentView(R.layout.activity_ram_usage);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tv = findViewById(R.id.tv);

    long start = System.currentTimeMillis();
    Class cls = null;
    Activity activity = null;
    try {
      cls = Class.forName("com.demo.activity.TestRamUsageActivity");
      activity = (Activity) cls.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    long cost = System.currentTimeMillis() - start;

    Bundle b = new Bundle();
    b.putString("a", big);
    b.putString("b", big);

    Map<String, String> map = new HashMap<>();
    map.put("a", big);
    map.put("b", big);

    Pair<String, String> p = new Pair(big, big);

    String s = String.format("接口对象 类：%s-实例：%s", RamUsageEstimator.humanSizeOf(Interceptor.Chain.class),
            RamUsageEstimator.humanSizeOf(new RealChain(new ArrayList<Interceptor>(), 0, "")))
            + "\n"
            +   String.format("Activity对象 类：%s-实例：%s", RamUsageEstimator.humanSizeOf(TestRamUsageActivity.class),
            RamUsageEstimator.humanSizeOf(new TestRamUsageActivity()))
            + "\n"
            +   String.format("String对象 类：%s-实例：%s", RamUsageEstimator.humanSizeOf(String.class),
            RamUsageEstimator.humanSizeOf("com.sogou.login.LoginActivity"))
            + "\n"
            +   String.format("Bundle实例：%s-Map实例：%s-Pair实例：%s", RamUsageEstimator.humanSizeOf(b),
            RamUsageEstimator.humanSizeOf(map), RamUsageEstimator.humanSizeOf(p))
            + "\n"
            +   String.format("Class.forName耗时：%s-%d", activity.toString(),cost);

    tv.setText(s);
  }

  String big = "Hardcoder is a solution which allows Android APP and Android System to communicate with each other directly, solving the problem that Android APP could only use system standard API rather than the hardware resource of system. Through Hardcoder, Android APP could make good use of hardware resource of mobile phone such as CPU frequency, Large Core, GPU to improve APP performance while Android system could get more information from APP in order to provide system resource to Android APP more properly. At the same time, for lack of implementation by the standard interface, the APP and the system can also realize the model adaptation and function expansion through the framework.\n" +
          "\n" +
          "Hardcoder framework could averagely optimize the performance of Wechat by 10%-30% in terms of Wechat startup, video delivery, mini program startup, and other highly-loaded scenes. Furthermore, it could also averagely optimize the performance of Mobile QQ by 10%-50% in terms of mobile QQ startup, chatting Initialization, picture delivery, and other highly-loaded scenes. The framework now has been applied to mobile brands such as OPPO, vivo, Huawei, XIAOMI, Sumsang, Meizu, etc and covers more than 460 millions devices.";
}
