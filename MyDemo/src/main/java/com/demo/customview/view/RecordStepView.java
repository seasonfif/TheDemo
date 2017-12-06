package com.demo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.demo.R;

/**
 * 创建时间：2017年03月15日10:59 <br>
 * 作者：zhangqiang <br>
 * 描述：录带看进度view
 */

public class RecordStepView extends View {

  private int doColor;
  private int undoColor;
  private float spaceH;
  private float spaceV;
  private float radius;
  private float lineHeight;
  private float textHeight;
  private float distance;
  private Paint circlePaint;
  private Paint linePaint;

  private float textSize;
  private Paint textPaint;

  private int index;

  public String[] lables;

  private int lableCount;

  public RecordStepView(Context context) {
    this(context, null);
  }

  public RecordStepView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RecordStepView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void setLables(String[] lables){
    this.lables = lables;
    lableCount = lables.length;
    invalidate();
  }

  public void next() {
    index ++;
    if (index >= lableCount) index = lableCount - 1;
    invalidate();
  }

  public void previous() {
    index --;
    if (index < 0) index = 0;
    invalidate();
  }

  private void init() {
    index = 0;
    doColor = Color.parseColor("#41bc6a");
    undoColor = Color.parseColor("#666666");
    radius = getResources().getDimension(R.dimen.dimen_4);
    spaceH = getResources().getDimension(R.dimen.dimen_4);
    spaceV = getResources().getDimension(R.dimen.dimen_5);
    lineHeight = getResources().getDimension(R.dimen.dimen_1);
    textSize = getResources().getDimension(R.dimen.dimen_12);

    circlePaint = new Paint();
    linePaint = new Paint();
    linePaint.setStrokeWidth(lineHeight);
    linePaint.setStrokeJoin(Paint.Join.ROUND);
    linePaint.setStrokeCap(Paint.Cap.ROUND);

    textPaint = new Paint();
    textPaint.setTextSize(textSize);
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

    Paint.FontMetrics fm = textPaint.getFontMetrics();
    textHeight = fm.descent - fm.ascent;
  }

  private float getLablesWidth() {
    StringBuilder sb = new StringBuilder();
    for (String s : lables){
      sb.append(s);
    }
    return textPaint.measureText(sb.toString());
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (lables != null) distance = (getMeasuredWidth() - getLablesWidth())/(lables.length-1);
    int height = (int) (2*radius + spaceV + textHeight);
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
  }

  @Override public void draw(Canvas canvas) {
    super.draw(canvas);
    if (lables == null) return;

    float centerX = 0;
    for (int i = 0; i < lables.length; i++){

      if (i <= index){
        textPaint.setColor(doColor);
        circlePaint.setColor(doColor);
        linePaint.setColor(doColor);
      }else{
        textPaint.setColor(undoColor);
        circlePaint.setColor(undoColor);
        linePaint.setColor(undoColor);
      }

      float shift = textPaint.measureText(lables[i])/2;
      float shift_next = 0;

      if (i == 0){
        centerX += shift;
      }else{
        shift_next = textPaint.measureText(lables[i-1])/2;
        centerX += textPaint.measureText(lables[i-1])/2 + distance + shift;
      }

      //绘制文字
      canvas.drawText(lables[i], centerX, 2*radius + spaceV + textSize, textPaint);

      //绘制圆点
      canvas.drawCircle(centerX, radius, radius, circlePaint);

      if (i > 0){
        //绘制间隔线
        canvas.drawLine(centerX - shift - shift_next -distance + radius + spaceH, radius, centerX - radius - spaceH, radius, linePaint);
      }
    }
  }
}
