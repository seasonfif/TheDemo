package com.demo.leakcanary;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.R;
import com.demo.activity.BaseActivity;

import java.lang.reflect.Field;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
/**
 * Created by season on 2016/7/16.
 */
public class LeakImmActivity extends BaseActivity{

    private MaqueeTextView immView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        updateImmView();
    }

    @Override
    public void setView() {
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        immView = new MaqueeTextView(this);

        final EditText leaking = new EditText(this);
//        leaking.setFocusable(false);
//        leaking.setFocusableInTouchMode(false);
//        leaking.clearFocus();
        Button button = new Button(this);
        button.setText("Remove EditText");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.removeView(leaking);
                ll.removeView(v);
//                Button fixLeak = new Button(LeakImmActivity.this);
//                fixLeak.setText("Fix IMM leak");
//                fixLeak.setOnClickListener(new View.OnClickListener() {
//                    @Override public void onClick(View v) {
//                        View rootView = v.getRootView();
//                        // This will give focus to the button.
//                        immView.requestFocusFromTouch();
//                        updateImmView();
//                    }
//                });
//                ll.addView(fixLeak, new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            }
        });

        ll.addView(immView, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        ll.addView(button, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        ll.addView(leaking, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT);
        lp.leftMargin = getResources().getDimensionPixelSize(R.dimen.dimen_15);
        lp.rightMargin = getResources().getDimensionPixelSize(R.dimen.dimen_15);
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.dimen_5);
        setContentView(ll, lp);
    }


    private void updateImmView() {
        logServedView();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
//                updateImmView();
            }
        }, 100);
    }

    private void logServedView() {
        try {
            Field sInstanceField = InputMethodManager.class.getDeclaredField("sInstance");
            sInstanceField.setAccessible(true);
            Object imm = sInstanceField.get(null);
            if (imm == null) {
                // Not set yet.
                return;
            }
            Field mServedViewField = InputMethodManager.class.getDeclaredField("mServedView");
            mServedViewField.setAccessible(true);
            View servedView = (View) mServedViewField.get(imm);
            immView.setText("InputMethodManager.mServedView: "
                    + servedView.getClass().getName()
                    + " Attached:"
                    + servedView.isAttachedToWindow());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
