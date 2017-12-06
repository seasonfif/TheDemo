package com.demo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;
import com.demo.R;
import com.demo.customview.view.CustomerArcView;
import com.demo.customview.view.LikeQQHealthView;

import com.demo.customview.view.MarqueeView;
import com.demo.customview.view.RecordStepView;
import java.util.Random;

public class TestViewActivity extends BaseActivity {

    private LikeQQHealthView qqHealthView;
    private boolean changed = false;

    private CustomerArcView arcView;
    private boolean arcViewChanged = false;

    private RecordStepView recordStepView;

    private Button previous, next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        Toast.makeText(this, getIntent().getStringExtra(Intent.EXTRA_TEXT), Toast.LENGTH_SHORT).show();

        /*final MarqueeView mv = (MarqueeView) findViewById(R.id.marqueeView100);
        mv.setPauseBetweenAnimations(500);
        mv.setSpeed(10);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mv.startMarquee();
            }
        });
        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do.");
                mv.startMarquee();
            }
        }, 1000);*/


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

        recordStepView = (RecordStepView) findViewById(R.id.record_step_view);
        recordStepView.setLables(new String[]{"选项目", "带看信息", "反馈", "带看信息", "反馈", "带看信息"});
        previous.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                recordStepView.previous();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                recordStepView.next();
            }
        });
    }


    @Override
    public void setView() {
        setContentView(R.layout.activity_views);
    }
}
