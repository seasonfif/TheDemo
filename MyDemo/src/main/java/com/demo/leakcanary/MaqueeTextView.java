package com.demo.leakcanary;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Fcy on 2015/12/16.<br/>
 * 跑马灯TextView extends MyTextView
 */
public class MaqueeTextView extends TextView {

    public MaqueeTextView(Context context) {
        this(context, null);
    }

    public MaqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);

    }

    public MaqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFocusable(true);
        setFocusableInTouchMode(true);

        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
