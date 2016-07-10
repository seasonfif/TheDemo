package com.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.R;
import com.demo.customview.view.CustomerArcView;
import com.demo.customview.view.LikeQQHealthView;

import java.util.Random;

public class TestViewActivity extends BaseActivity {

    private LikeQQHealthView qqHealthView;
    private boolean changed = false;

    private CustomerArcView arcView;
    private boolean arcViewChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qqHealthView = (LikeQQHealthView) findViewById(R.id.qq_health);
        qqHealthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) qqHealthView.getLayoutParams();
                if(!changed){
                    params.width = 800;
                    changed = true;
                }else {
                    params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    changed = false;
                }
                qqHealthView.setLayoutParams(params);
//                qqHealthView.invalidate();
            }
        });

        arcView = (CustomerArcView) findViewById(R.id.arc);
        arcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) arcView.getLayoutParams();
                if(!arcViewChanged){
                    params.width = 800;
                    arcViewChanged = true;
                }else {
                    params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    arcViewChanged = false;
                }
                arcView.setLayoutParams(params);*/
                if(!arcViewChanged){
                    arcView.setText("HAHA");
                    arcViewChanged = true;
                }else {
                    arcView.setText("HAHAha");
                    arcViewChanged = false;
                }
                arcView.invalidate();
            }
        });

        final Random r = new Random();
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        int length = r.nextInt(7) % (7 - 4 + 1) + 4;
                        int[] steps = new int[length];
                        for (int i = 0; i < length; i++) {
                            steps[i] = r.nextInt(20000) % (20000 - 3000 + 1) + 3000;
                        }
                        qqHealthView.setSteps(steps);
                        qqHealthView.postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    public void setView() {
        setContentView(R.layout.activity_views);
    }
}
