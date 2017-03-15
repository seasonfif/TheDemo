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

  private boolean firstStep = true;
  private int doColor;
  private int undoColor;
  private float spaceH;
  private float spaceV;
  private float radius;
  private float lineHeight;
  private float textHeight;
  private Paint circlePaint;
  private Paint linePaint;

  private String leftText;
  private String rightText;
  private float textSize;
  private Paint textPaint;

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

  public void setFirstStep(boolean firstStep) {
    this.firstStep = firstStep;
    invalidate();
  }

  private void init() {
    doColor = Color.parseColor("#41bc6a");
    undoColor = Color.parseColor("#666666");
    radius = getResources().getDimension(R.dimen.dimen_4);
    spaceH = getResources().getDimension(R.dimen.dimen_4);
    spaceV = getResources().getDimension(R.dimen.dimen_5);
    lineHeight = getResources().getDimension(R.dimen.dimen_1);

    leftText = "选择项目";
    rightText = "带看信息";
    textSize = getResources().getDimension(R.dimen.dimen_12);

    circlePaint = new Paint();
    linePaint = new Paint();
    linePaint.setStrokeWidth(lineHeight);
    linePaint.setStrokeJoin(Paint.Join.ROUND);
    linePaint.setStrokeCap(Paint.Cap.ROUND);

    textPaint = new Paint();
    textPaint.setTextSize(textSize);
    textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

    Paint.FontMetrics fm = textPaint.getFontMetrics();
    textHeight = fm.descent - fm.ascent;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int height = (int) (2*radius + spaceV + textHeight);
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
  }

  @Override public void draw(Canvas canvas) {
    super.draw(canvas);
    //绘制左边圆
    float leftTextWidth = textPaint.measureText(leftText);
    circlePaint.setColor(doColor);
    canvas.drawCircle(leftTextWidth/2, radius, radius, circlePaint);

    //绘制左边文字
    textPaint.setColor(doColor);
    canvas.drawText(leftText, 0, 2*radius + spaceV + textSize, textPaint);

    //绘制右边圆
    float rightTextWidth = textPaint.measureText(leftText);
    circlePaint.setColor(firstStep ? undoColor : doColor);
    canvas.drawCircle(getMeasuredWidth() - rightTextWidth/2, radius, radius, circlePaint);

    //绘制右边文字
    textPaint.setColor(firstStep ? undoColor : doColor);
    textPaint.measureText(rightText);
    canvas.drawText(rightText, getMeasuredWidth() - rightTextWidth, 2*radius + spaceV + textSize, textPaint);

    //绘制中间线条
    linePaint.setColor(firstStep ? undoColor : doColor);
    canvas.drawLine(leftTextWidth/2 + radius + spaceH, radius, getMeasuredWidth() - rightTextWidth/2 - radius - spaceH, radius, linePaint);
  }
}
