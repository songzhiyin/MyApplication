package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.szy.myapplication.R;


/**
 * 自定义状态view
 */
public class StatusView extends View {
    private Context mContext;
    private Paint painText, painLine;
    private int mWidth, mHeight;
    private String message = "待取货";//文本
    private int textSize = 40;
    private int linew;
    private int textW = 0;
    private int textH = 0;

    public StatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }


    public void setLine_color(String color) {
        try {
            painLine.setColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        invalidate();
    }

    private void initview(Context context) {
        mContext = context;
        painText = new Paint();//文本的画笔
        painText.setColor(Color.WHITE);
        painText.setStyle(Paint.Style.FILL_AND_STROKE);
        painText.setAntiAlias(true);
        painText.setStrokeWidth(0.6f);

        painLine = new Paint();
        painLine.setStyle(Paint.Style.FILL);
        painLine.setAntiAlias(true);
        painLine.setStrokeWidth(1);
        painLine.setColor(mContext.getResources().getColor(R.color.blue_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制直角扇形
        Path path = new Path(); //定义一条路径
        linew = mHeight / 2;
        path.moveTo(0, linew); //移动到 坐标10,10
        path.lineTo(0, mHeight);
        path.lineTo(mWidth, 0);
        path.lineTo(linew, 0);
        canvas.drawPath(path, painLine);
        adjustTvTextSize((int) Math.sqrt(linew * linew * 2), canvas);


    }

    public synchronized void adjustTvTextSize(int maxWidth, Canvas canvas) {
        textSize = 38;
        painText.setTextSize(textSize);
        if (maxWidth <= 0 || message == null) {
            return;
        }
        while (painText.measureText(message) > maxWidth) {
            textSize--;
            painText.setTextSize(textSize);
        }
        Rect rect = new Rect();
        painText.getTextBounds(message, 0, message.length(), rect);
        textW = rect.width();//文字宽
        textH = rect.height();//文字高
        Path path_txt = new Path(); //定义一条路径
        double sin45 = Math.sin(Math.PI / 180 * 45);
        canvas.translate(mHeight/2,mWidth/2);//坐标轴平移
        canvas.rotate(-45);//画布旋转
        float textw = (float) (linew*sin45-textH)/2+4;
        path_txt.moveTo(-(textW/2), -textw);
        path_txt.lineTo(textW/2, -textw);
        canvas.drawTextOnPath(message, path_txt, 2f, 2f, painText);
        canvas.save();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取测量的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {//如果布局里面设置的wrap_content,这里取布局的文本的宽度和内边距
            mWidth = 120;
        }
        //如果布局里面设置的模式是固定值或者是设置match_parent，则取测量的大小
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {//如果布局设置的是wrap_content，这里取布局的文本高度和内边距
            mHeight = 120;
        }
        if (mWidth > mHeight) {
            mHeight = mWidth;
        } else {
            mWidth = mHeight;
        }
        setMeasuredDimension(mWidth, mHeight);

    }
}
