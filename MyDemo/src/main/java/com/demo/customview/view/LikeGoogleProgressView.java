package com.demo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/6/25.
 */
public class LikeGoogleProgressView extends View {

    private int defBarColor = Color.parseColor("#1e88e5");
    private int defWidth = 80*3;
    private int defHeight = 10*3;
    private int defVerSpace = 10*3;
    private float start = 0.f;
    private Paint paint;
    private int[] colors = new int[]{Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};

    public LikeGoogleProgressView(Context context) {
        this(context, null);
    }

    public LikeGoogleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeGoogleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setColor(defBarColor);
        paint.setStrokeWidth(defHeight);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        if(start > width + defWidth + defVerSpace){
            start = 0;
        }else{
            start += 10;
        }

        int colori = 0;
        while(start < width){
//            paint.setColor(colors[colori]);
            colori = colori >= 3 ? 0: colori+1;
            canvas.drawLine(start, 0, start + defWidth, 0, paint);
            start += defWidth + defVerSpace;
        }

        int j = 1;
        while(start >= -defVerSpace){
//            paint.setColor(colors[j]);
            j = j >= 3 ? 0: j+1;
            canvas.drawLine(start, 0, start + defWidth, 0, paint);
            start -= (defWidth + defVerSpace);
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
    }
}
