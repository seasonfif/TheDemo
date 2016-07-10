package com.demo.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/25.
 */
public class LikeQQHealthView extends View {

    private static final String TAG = LikeQQHealthView.class.getClass().getSimpleName();
    //view的宽高比
    private float mRadio;
    //view宽度
    private int w;
    //view高度
    private int h;
    //弧形半径
    private float radius;
    //弧形外接矩形
    private RectF arcRectF;
    private float arcCenterX, arcCenterY;
    //圆弧画笔宽度
    private float arcWidth;
    //竖条画笔宽度
    private float barWidth;

    private int themeColor;
    private Paint arcPaint;
    private Paint barPaint;
    private Paint textPaint;
    private Paint dashPaint;
    private Paint bottomBgPaint;

    private int[] steps = new int[]{10050, 8900, 15280, 9200, 6500, 5660, 9450};
    private float percent = 0.3f;
    private int step;

    public LikeQQHealthView(Context context) {
        this(context, null);
    }

    public LikeQQHealthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeQQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRadio = 450.f/600.f;
        themeColor = Color.parseColor("#2ec3fd");

        //圆弧画笔
        arcPaint = new Paint();
        arcPaint.setColor(themeColor);
        arcPaint.setAntiAlias(true);
        arcPaint.setDither(true);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        //虚线画笔
        dashPaint = new Paint();
        dashPaint.setAntiAlias(true);
        dashPaint.setColor(Color.parseColor("#C1C1C1"));
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setPathEffect(new DashPathEffect(new float[]{8, 4}, 0));//画虚线

        //竖条画笔
        barPaint = new Paint();
        barPaint.setColor(themeColor);
        barPaint.setAntiAlias(true);
        barPaint.setStrokeCap(Paint.Cap.ROUND);

        //圆环动画
        ValueAnimator percentAnimator = ValueAnimator.ofFloat(0, 1);
        percentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        percentAnimator.setDuration(1000);
        percentAnimator.start();

        //步数的动画
        ValueAnimator stepAnimator = ValueAnimator.ofInt(0, 10000);
        stepAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                step = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        stepAnimator.setDuration(1000);
        stepAnimator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = Integer.MAX_VALUE >> 1;
        int width, height;
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if(widthMod == MeasureSpec.EXACTLY || widthMod == MeasureSpec.AT_MOST){
            width = widthSize;
        }else {
            width = defWidth;
        }

        height = (int) (width * 1.f/ mRadio);
        setMeasuredDimension(width, height);
        Log.e(TAG, "width:" + width + " | height:" + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        Log.e("onSizeChanged", "h:" + h + " | oldh:" + oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        int length = steps.length;
        int sum = 0;
        for (int i=0; i<length; i++){
            sum += steps[i];
        }
        float arvage = sum / length;
        bottomBgPaint = new Paint();
        bottomBgPaint.setColor(themeColor);
        float bottomH = h * 85/585;
        canvas.drawRect(new RectF(0, h - bottomH, w, h), bottomBgPaint);

        arcCenterX = w / 2;
        arcCenterY = 170.f / 585.f * h;
        arcRectF = new RectF();
        radius = 0.3f * w;
        arcRectF.left = arcCenterX - radius;
        arcRectF.top = arcCenterY - radius;
        arcRectF.right = arcCenterX + radius;
        arcRectF.bottom = arcCenterY + radius;

        arcWidth = 20.f / 450.f * w;
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(arcRectF, 120, 300*percent, false, arcPaint);

        float xPos = arcCenterX;
        float yPos = (int) (arcCenterY - 40.f / 525.f * h);
        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(15.f / 450.f * w);
        textPaint.setColor(Color.parseColor("#C1C1C1"));
        canvas.drawText("截至22:50分已走", xPos, yPos, textPaint);

        textPaint.setTextSize(42.f / 450.f * w);
        textPaint.setColor(themeColor);
        canvas.drawText(step + "", arcCenterX, arcCenterY, textPaint);

        barWidth = 16.f / 450.f * w;
        float maxBar = h * 100/585;
        float startY = (float) (h - bottomH - bottomH);
        barPaint.setStrokeWidth(barWidth);
        barPaint.setStrokeJoin(Paint.Join.ROUND);
        barPaint.setStrokeCap(Paint.Cap.ROUND);

        float startX = w / (length + 1);
        float space = startX;
        float stopY;

        int[] aaa = steps.clone();
        Arrays.sort(aaa);
        int max = aaa[length-1];
        int min = aaa[0];

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(15.f / 450.f * w);
        textPaint.setColor(Color.parseColor("#C1C1C1"));
        float avgh = maxBar * (float) (arvage / (double)max);
        //绘制虚线
        canvas.drawLine(space/2, startY-avgh, w-space/2, startY-avgh, dashPaint);
        //绘制贝塞尔曲线
//        drawQuad(space/2, startY-avgh,100.f, 100.f, arcCenterX-space/2, 0, canvas);

        for (int i=0; i<length; i++){
            stopY = maxBar * (float) (steps[i] / (double)max);
            if (steps[i] >= arvage){
                barPaint.setColor(themeColor);
            }else{
                barPaint.setColor(Color.parseColor("#e5e5e5"));
            }
            //绘制竖条
            canvas.drawLine(startX, startY, startX, startY - stopY, barPaint);
            //绘制竖条下方的日期
            canvas.drawText("0"+(i+1)+"日", startX, startY + (15.f / 450.f * w)*2.2f, textPaint);
            startX += space;
        }
        //最大值
        float maxQuadH = bottomH *3/4;
        drawQuad(0, h - bottomH, w/4, (float)(maxQuadH*max/(double)sum)+maxQuadH/2, w*3/4, 0, canvas);
        //最小值
        drawQuad(w/4, h - bottomH, w/2, (float)(maxQuadH*min/(double)sum)+maxQuadH/2, w*3/4, 0, canvas);
    }

    private void drawQuad(float startX, float startY, float controlX, float controlY, float endX, float endY, Canvas canvas){
        Path path = new Path();
        path.moveTo(startX, startY);
        for (int i = 0; i < 1; i++) {
//            path.rQuadTo(controlX, controlY, endX, endY);
            path.rQuadTo(controlX, -controlY, endX, endY);
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        path.reset();
    }

    public void setSteps(int[] steps){
        this.steps = steps;
    }
}
