package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.szy.myapplication.R;

import java.util.Random;

/**
 * Created by Administrator on 2018/1/24 0024.
 * 图形验证码
 */

public class ImageVFCodeView extends View {
    private Context mContext;
    private Paint painText, painLine;
    private int mWidth, mHeight;
    private String message = "";


    public ImageVFCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    private void initview(Context context) {
        mContext = context;
        painText = new Paint();//文本的画笔
        painText.setColor(Color.BLUE);
        painText.setStyle(Paint.Style.FILL_AND_STROKE);
        painText.setAntiAlias(true);
        painText.setStrokeWidth(2);
        painText.setTextSize(40);
        painLine = new Paint();
        painLine.setColor(Color.RED);
        painLine.setStyle(Paint.Style.FILL);
        painLine.setAntiAlias(true);
        painLine.setStrokeWidth(1);
        message = getStringRandom(5);
    }

    /**
     * 重置验证码
     */
    public void setMessage() {
        message = getStringRandom(5);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 10;
        int y = mHeight / 2 + 15;
        int text_width = (int) painText.measureText(message);// 得到总体长度
        canvas.drawText(message, x, y, painText);
        canvas.drawLine(x - 10, y - 20, x + text_width + 10, y, painLine);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的文本的宽度
            int text_width = (int) painText.measureText(message);// 得到总体长度
            width = text_width + 30;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 80;
        }
        mWidth = width - (getPaddingBottom() + getPaddingTop());
        mHeight = height - (getPaddingRight() + getPaddingLeft());
        setMeasuredDimension(width, height);
    }

    /**
     * 生成随机数字和字母
     *
     * @param length
     * @return
     */
    private String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
