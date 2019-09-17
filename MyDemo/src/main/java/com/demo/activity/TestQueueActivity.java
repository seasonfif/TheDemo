package com.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.R;
import com.demo.util.RecordManager;
import com.demo.util.RecordManager2;

/**
 *
 */
public class TestQueueActivity extends BaseActivity{

    private static final String TAG = "TestQueueActivity";
    private Button btn_record, btn_records, btn_start, btn_pause;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RecordManager.init();
        RecordManager2.init();
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_test_queue);

        btn_record = (Button) findViewById(R.id.btn_record);
        btn_records = (Button) findViewById(R.id.btn_records);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);

        btn_record.setOnClickListener(this);
        btn_records.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_record:
                i++;
                RecordManager.record("btn_record"+i);
                break;

            case R.id.btn_records:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0; i<=9999; i++){
                            RecordManager2.record("btn_records" + i);
                            Log.e("TAG", "record成功");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.btn_start:
                RecordManager.flag = true;
                break;
            case R.id.btn_pause:
                RecordManager.flag = false;
                break;
        }
    }
}
