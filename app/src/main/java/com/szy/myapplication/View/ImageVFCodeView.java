package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private String message = "";//文本
    private int pading = 20;
    private int text_width = 0;//文本的宽度
    private int text_height = 0;//文本的高度
    private int textpad = 6;

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
        int x = mWidth / 2 - text_width / 2;
        int y = mHeight / 2 + text_height / 2;
        int textItemW = text_width / message.length();
        for (int i = 0; i < message.length(); i++) {
            canvas.save();
            canvas.rotate(-10, x + (i * textItemW) + textpad, y);
            canvas.drawText(message.substring(i, i + 1), x + (i * textItemW) + textpad, y, painText);
            canvas.restore();
        }
        int xLine = x + text_width + message.length() * textpad;
        int yLine = y - text_height;
        canvas.drawLine(x, y, xLine, yLine, painLine);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Rect rect = new Rect();
        painText.getTextBounds(message, 0, message.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        text_width = rect.width();//字符串的宽度
        text_height = rect.height();//字符串的高度
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取测量的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {//如果布局里面设置的wrap_content,这里取布局的文本的宽度和内边距
            mWidth = text_width + pading * 2 + textpad * message.length();
        }
        //如果布局里面设置的模式是固定值或者是设置match_parent，则取测量的大小
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {//如果布局设置的是wrap_content，这里取布局的文本高度和内边距
            mHeight = text_height + pading * 2;
        }
        setMeasuredDimension(mWidth, mHeight);

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
