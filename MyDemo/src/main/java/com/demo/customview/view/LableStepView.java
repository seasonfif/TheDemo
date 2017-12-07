package com.demo.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import com.demo.R;

/**
 * 创建时间：2017年03月15日10:59 <br>
 * 作者：zhangqiang <br>
 * 描述：进度view
 */

public class LableStepView extends View {

  /**
   * 已完成步骤的颜色
   */
  private int doColor;
  /**
   * 未完成步骤的颜色
   */
  private int undoColor;
  /**
   * 圆点与文字的垂直距离
   */
  private float spaceV;
  /**
   * 圆点与横线的水平距离
   */
  private float spaceH;
  /**
   * 原点半径
   */
  private float radius;
  /**
   * 横线高度
   */
  private float lineHeight;
  /**
   * 文字大小
   */
  private float textSize;
  /**
   * 文案集合
   */
  public CharSequence[] lables;

  private float textHeight;
  private float distance;
  private Paint circlePaint;
  private Paint linePaint;
  private Paint textPaint;
  private int index;
  private int lableCount;
  private SparseArray<LableArea> lableAreas = new SparseArray<>();

  private OnStepClickListener onStepClickListener;

  public interface OnStepClickListener{
    void onStepClick(int index);
  }

  public LableStepView(Context context) {
    this(context, null);
  }

  public LableStepView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LableStepView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LableStepView);
    doColor = ta.getColor(R.styleable.LableStepView_do_color, Color.parseColor("#41bc6a"));
    undoColor = ta.getColor(R.styleable.LableStepView_undo_color, Color.parseColor("#666666"));
    radius = ta.getDimension(R.styleable.LableStepView_radius, getResources().getDimension(R.dimen.dimen_4));
    spaceV = ta.getDimension(R.styleable.LableStepView_space_vertical, getResources().getDimension(R.dimen.dimen_5));
    spaceH = ta.getDimension(R.styleable.LableStepView_space_horizontal, getResources().getDimension(R.dimen.dimen_5));
    lineHeight = ta.getDimension(R.styleable.LableStepView_line_height, getResources().getDimension(R.dimen.dimen_1));
    textSize = ta.getDimension(R.styleable.LableStepView_text_size, getResources().getDimension(R.dimen.dimen_12));
    lables = ta.getTextArray(R.styleable.LableStepView_lables);
    lableCount = lables.length;
    ta.recycle();
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

  public int getIndex() {
    return index;
  }

  public void setOnStepClickListener(OnStepClickListener onStepClickListener) {
    this.onStepClickListener = onStepClickListener;
  }

  private void init() {
    index = 0;

    circlePaint = new Paint();
    linePaint = new Paint();
    linePaint.setStrokeWidth(lineHeight);
    linePaint.setStrokeJoin(Paint.Join.ROUND);
    linePaint.setStrokeCap(Paint.Cap.ROUND);

    textPaint = new Paint();
    textPaint.setTextSize(textSize);
    //文字居中绘制
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

    Paint.FontMetrics fm = textPaint.getFontMetrics();
    textHeight = fm.descent - fm.ascent;
  }

  private float getLablesWidth() {
    StringBuilder sb = new StringBuilder();
    for (CharSequence s : lables){
      sb.append(s);
    }
    return textPaint.measureText(sb.toString());
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (lables != null) distance = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - getLablesWidth())/(lables.length-1);
    int height = (int) (2*radius + spaceV + textHeight + getPaddingTop() + getPaddingBottom());
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {

    if (onStepClickListener != null){
      switch (event.getAction()){
        case MotionEvent.ACTION_MOVE:
        case MotionEvent.ACTION_DOWN:
          float x = event.getX();
          int step = pointStep(x);
          if (step > -1){
            index = step;
            return true;
          }
          break;
        case MotionEvent.ACTION_UP:
          invalidate();
          onStepClickListener.onStepClick(index);
          return true;
      }
    }
    return super.onTouchEvent(event);
  }

  private int pointStep(float x) {
    for (int i=0; i < lableCount; i++){
      LableArea lableArea = lableAreas.valueAt(i);
      if (x >= lableArea.minX && x <= lableArea.maxX){
        return lableArea.index;
      }
    }
    return -1;
  }

  @Override public void draw(Canvas canvas) {
    super.draw(canvas);
    if (lables == null) return;

    canvas.translate(getPaddingLeft(), getPaddingTop());
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

      float shift = textPaint.measureText(lables[i].toString())/2;
      float shift_pre = 0;

      if (i == 0){
        centerX += shift;
      }else{
        shift_pre = textPaint.measureText(lables[i-1].toString())/2;
        centerX += textPaint.measureText(lables[i-1].toString())/2 + distance + shift;
      }

      //绘制文字
      canvas.drawText(lables[i].toString(), centerX, 2*radius + spaceV + textSize, textPaint);

      //绘制圆点
      canvas.drawCircle(centerX, radius, radius, circlePaint);

      if (i > 0){
        //绘制间隔线
        canvas.drawLine(centerX - shift - shift_pre -distance + radius + spaceH, radius, centerX - radius - spaceH, radius, linePaint);
      }

      //缓存位置信息
      LableArea lableArea = lableAreas.get(i);
      if (lableArea == null){
        lableArea = new LableArea();
      }
      lableArea.index = i;
      lableArea.lable = lables[i].toString();
      lableArea.minX = getPaddingLeft() + centerX - shift;
      lableArea.maxX = getPaddingLeft() + centerX + shift;
      lableAreas.put(i, lableArea);
    }
  }

  private class LableArea {
    /**
     * 文案
     */
    public String lable;
    /**
     * 索引
     */
    public int index;
    /**
     * x轴左边界
     */
    public float minX = 0;
    /**
     * x轴右边界
     */
    public float maxX = 0;
  }
}
