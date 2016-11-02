package com.demo.leakcanary;

/**
 * Created by season on 2016/7/16.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.reflect.Field;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class Imm extends Activity {

    private TextView immView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(VERTICAL);
        immView = new TextView(this);

        final EditText leaking = new EditText(this);

        Button button = new Button(this);
        button.setText("Remove EditText");
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                layout.removeView(leaking);
                layout.removeView(v);
            }
        });

        layout.addView(immView, new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        layout.addView(button, new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        layout.addView(leaking, new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

        setContentView(layout);
        updateImmView();
    }

    private void updateImmView() {
        logServedView();
        immView.postDelayed(runnable, 100);
    }

    private Runnable runnable = new Runnable() {
        @Override public void run() {
            updateImmView();
        }
    };

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
                    + "\nAttached:"
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
        immView.removeCallbacks(runnable);
    }
}