package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.demo.LogicOperation.TestLogicActivity;
import com.demo.R;
import com.demo.leakcanary.LeakAsyncTaskAndHandlerActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/9.
 */
public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
        ((TextView)findViewById(R.id.vername)).setText(sdf.format(getLast()));
        findViewById(R.id.views).setOnClickListener(this);
        findViewById(R.id.viewgroup).setOnClickListener(this);
        findViewById(R.id.leakcanary).setOnClickListener(this);
        findViewById(R.id.logic).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
    }

    private long getLast(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.views:
                goToOthers(TestViewActivity.class);
                //goToOthers(TestMarqueeActivity.class);
                break;
            case R.id.viewgroup:
                goToOthers(TestViewGroupActivity.class);
                break;
            case R.id.leakcanary:
                goToOthers(LeakAsyncTaskAndHandlerActivity.class);
                break;
            case R.id.logic:
                goToOthers(TestLogicActivity.class);
                break;
            case R.id.share:
                goToOthers(ShareAcivity.class);
                break;

        }
    }
}
