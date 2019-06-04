package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.R;
import com.demo.util.RecordManager;

/**
 *
 */
public class TestQueueActivity extends BaseActivity{

    private static final String TAG = "TestQueueActivity";
    private Button btn_record;
    private Button btn_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecordManager.init();
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_test_queue);

        btn_record = (Button) findViewById(R.id.btn_record);
        btn_records = (Button) findViewById(R.id.btn_records);

        btn_record.setOnClickListener(this);
        btn_records.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_record:
                RecordManager.record("btn_record");
                break;

            case R.id.btn_records:
                for (int i=0; i<=99; i++){
                    RecordManager.record("btn_records" + i);
                }
                break;
        }
    }
}
