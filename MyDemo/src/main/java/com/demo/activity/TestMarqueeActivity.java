package com.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.demo.R;
import com.demo.customview.view.MarqueeView;
import java.util.Random;

/**
 * Created by zhangqiang on 2016/7/19.
 */
public class TestMarqueeActivity extends BaseActivity{

    final static String[] randomStrings = new String[]{
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed.", "do eiusmod tempor incididunt",
            "fugiat nulla pariatur. Excepteur sint occaecat cupidatat", "sunt in culpa qui officia", "nisi ut aliquid",
            "aliquid ex ea commodi consequatur", "inventore veritatis et quasi architecto",
            "beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Marquee #1: Configuration using code.
        final MarqueeView mv = (MarqueeView) findViewById(R.id.marqueeView100);
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
                textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
                mv.startMarquee();
            }
        }, 1000);

        // Marquee #2: Configured via XML.
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        findViewById(R.id.btnChangeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
            }
        });

        // Marquee #3: Manual Start/Stop
        final MarqueeView marqueeView3 = (MarqueeView) findViewById(R.id.marqueeView3);
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marqueeView3.startMarquee();
            }
        });
        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marqueeView3.reset();
            }
        });

    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_marquee);
    }
}
