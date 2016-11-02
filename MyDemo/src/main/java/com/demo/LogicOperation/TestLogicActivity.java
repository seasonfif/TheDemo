package com.demo.LogicOperation;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.demo.R;
import com.demo.activity.BaseActivity;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangqiang on 2016/7/13.
 */
public class TestLogicActivity extends BaseActivity{

    //排序条件
    public final static int LEFT_CONDITION = 0x0001;
    public final static int MIDDLE_CONDITION = 0x0002;
    public final static int RIGHT_CONDITION = 0x0003;

    //排序顺序，升序和降序
    public final static int ORDER_ASC = 0x1000;
    public final static int ORDER_DES = 0x2000;

    private TextView tvShow, tvResult;
    private EditText et;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvShow = (TextView) findViewById(R.id.tv);
        tvResult = (TextView) findViewById(R.id.result);
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String result = formatBigPrice(Double.parseDouble(et.getText().toString()));
                tvResult.setText(generateSpanString(result));*/
                tvResult.setText(percentFormat(1.f/4));
            }
        });
        int sortSpec = SortSpec.makeSort(LEFT_CONDITION, ORDER_ASC);
        tvShow.setText(sortSpec+
                "\n"+ SortSpec.getSortCondition(sortSpec)+
                "\n"+ SortSpec.getSortOrder(sortSpec)
        );
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_logic);
    }

    public static class SortSpec{

        public static final int SORT_CONDITION_MASK = 0x0fff;
        public static final int SORT_ORDER_MASK = 0xf000;

        public static int makeSort(int condition, int order){
            return condition | order;
        }

        //获取排序的类型
        public static int getSortCondition(int sort){
            //return sort & SORT_CONDITION_MASK;
            return sort & ~SORT_ORDER_MASK;
        }

        //获取排序的顺序
        public static int getSortOrder(int sort){
            return sort & SORT_ORDER_MASK;
        }
    }

    private String percentFormat(float f){
        f *= 100;
        DecimalFormat decimalFormat = new DecimalFormat(".#");
        String percentStr = getValid(Float.parseFloat(decimalFormat.format
                (f)));
        return percentStr+"%";
    }

    /**
     * @param value
     * @return 取有效数字
     */
    private String getValid(float value) {
        if (value - (int) value == 0) {
            return String.valueOf((int) value);
        }
        return String.valueOf(value);
    }

    private String formatPercent(float percent){
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(1);
        //最后格式化
        return nt.format(percent);
    }

    /**
     * 正则匹配字符串中的浮点数（如-123.45）
     *
     * @return 匹配之后的matcher
     */
    private Matcher getFloatMatcher(String str) {
        //?代表0次或者1次，+代表1次或者多次
        String regEx = "-?[0-9]+.?[0-9]+";
        Pattern p = Pattern.compile(regEx);
        return p.matcher(str);
    }

    public static String formatBigPrice(double totalPrice) {
        String priceData = null;
        if (totalPrice >= 10000) {
            totalPrice /= 10000;
            if (totalPrice > 10000) {
                totalPrice /= 10000;
                priceData = getPriceByUnit(totalPrice, "亿");
            } else {
                priceData = getPriceByUnit(totalPrice, "万");
            }
        } else {
            priceData = getPriceByUnit(totalPrice, "");
        }
        return priceData;
    }

    private static String getPriceByUnit(double price, String unit){
        DecimalFormat decimalFormat = new DecimalFormat(".#");
        return (Double.parseDouble(decimalFormat.format(price)) - (int) price) == 0 ?
               (int) price+unit : Double
                       .parseDouble(decimalFormat.format(price))+unit;
    }


    private SpannableString generateSpanString(String origin) {

        Matcher matcher = getFloatMatcher(origin);
        SpannableString ss = new SpannableString(origin);
        if  (matcher.find()){

            ss.setSpan(new AbsoluteSizeSpan(25, true), matcher.end(), origin.length(), Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), matcher.end(), origin.length(), Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
            /*if (matcher.end() != origin.length()-1){
                SpannableString ss = new SpannableString(origin);
                ss.setSpan(new AbsoluteSizeSpan(15, true), matcher.end()-1, origin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ss.toString();
            }
            return origin;*/
        }else{
            return ss;
        }
    }
}
