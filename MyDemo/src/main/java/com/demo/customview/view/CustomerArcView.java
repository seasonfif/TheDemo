package com.demo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/6/19.
 */
public class CustomerArcView extends View{
    private Context context;
    private int start = 270;
    private int start1 = 270;
    private int sweep = 270;
    private String text;

    public CustomerArcView(Context context) {
        this(context, null);
    }

    public CustomerArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        sleep(1000);
                        perform();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        text = "Hello Android";
    }

    private void perform() {
        start = (start+30) % 360;
        start1 = (start1-30) % 360;
//        sweep = (sweep+30) % 360;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
//        setMeasuredDimension(800, 800);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        float cx = width/2;
        float cy = height/2;
        float radius = width/6;
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        canvas.drawCircle(cx, cy, radius, p);

        Paint textP = new Paint();
        textP.setColor(Color.BLACK);
        textP.setTextSize(50);

        int textW = (int)textP.measureText(text);
        int textH = (int)(textP.getFontMetrics().bottom - textP.getFontMetrics().ascent);
        canvas.drawText(text, (float)((width-textW)/2), (float)((height/2+textH/4)), textP);

        Paint arcP = new Paint();
        arcP.setColor(Color.GRAY);
        arcP.setStrokeWidth(width/16);
        arcP.setStyle(Paint.Style.STROKE);
        float arcRadius = radius*1.5f;
        RectF f = new RectF(cx-arcRadius, cy-arcRadius, cx+arcRadius, cy+arcRadius);
//        f = new RectF(50, 50, 200, 200);
        canvas.drawArc(f, start, sweep, false, arcP);

        Paint arcP1 = new Paint();
        arcP1.setColor(Color.GRAY);
        arcP1.setStrokeWidth(width/16);
        arcP1.setStyle(Paint.Style.STROKE);
        float arcRadius1 = radius*2.f;
        RectF f1 = new RectF(cx-arcRadius1, cy-arcRadius1, cx+arcRadius1, cy+arcRadius1);
//        f = new RectF(50, 50, 200, 200);
        canvas.drawArc(f1, start1, sweep, false, arcP1);
    }

    public void setText(String text){
        this.text = text;
    }
}
