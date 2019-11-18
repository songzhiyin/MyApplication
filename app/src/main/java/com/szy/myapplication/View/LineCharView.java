package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.szy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LineCharView extends View {
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private Paint mPainLine;
    private Paint mPainText;
    private double maxSize;
    private List<Double> dataY = new ArrayList<>();
    private List<String> dataX = new ArrayList<>();
    private int mPadingH = 10;
    private int mXtxtRight = 10;

    public LineCharView(Context context) {
        super(context);
        initView(context);
    }

    public LineCharView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LineCharView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setFromDataToUI();
        mContext = context;
        mPainText = new Paint();
        mPainLine = new Paint();
        mPainText = new Paint();//文本的画笔
        mPainText.setColor(mContext.getResources().getColor(R.color.tv_black));
        mPainText.setStyle(Paint.Style.FILL_AND_STROKE);
        mPainText.setAntiAlias(true);
        mPainText.setStrokeWidth(0.6f);
        mPainText.setTextSize(dip2px(mContext, 14f));
        mPainLine = new Paint();
        mPainLine.setStyle(Paint.Style.FILL);
        mPainLine.setAntiAlias(true);
        mPainLine.setStrokeWidth(1);
        mPainLine.setColor(mContext.getResources().getColor(R.color.blue_color));
        mPadingH = dip2px(mContext, 10f);
        mXtxtRight = mPadingH + getTxtW(maxSize + "");
    }

    public void setFromDataToUI() {
        maxSize = 11.4;
        dataY.add(7.3);
        dataY.add(3.6);
        dataY.add(5.0);
        dataX.add("01");
        dataX.add("02");
        dataX.add("03");
        dataX.add("04");
        dataX.add("05");
        dataX.add("06");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(maxSize + "", mPadingH, mPadingH + getTxtH(maxSize + ""), mPainText);

        drawYtxtAndLine(canvas);
        drawXtxtAndLine(canvas);

    }

    private void drawYtxtAndLine(Canvas canvas) {
        for (int i = 0; i < dataY.size(); i++) {
            int y = (int) (mHeight * dataY.get(i) / maxSize);
            y = mHeight - y;
            Paint mPainLine = new Paint();
            mPainLine.setStyle(Paint.Style.FILL);
            mPainLine.setAntiAlias(true);
            mPainLine.setStrokeWidth(1);
            mPainLine.setColor(mContext.getResources().getColor(R.color.blue_color));
            mPainLine.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
            canvas.drawLine(mXtxtRight, y, mWidth - mPadingH, y, mPainLine);

            canvas.drawText(dataY.get(i) + "", mPadingH, y, mPainText);
        }

    }
    private void drawXtxtAndLine(Canvas canvas){
        int w=(mWidth-mPadingH-mXtxtRight)/dataX.size();
        for (int i = 0; i <dataX.size() ; i++) {
            int x=mXtxtRight+w*i+w/2-getTxtW(dataX.get(i));
            canvas.drawText(dataX.get(i),x,mHeight-mPadingH,mPainText);
        }
        int y=mHeight-mPadingH-getTxtH("12")-dip2px(mContext,6);
        canvas.drawLine(mXtxtRight, y, mWidth - mPadingH, y, mPainLine);

    }

    private int getTxtW(String message) {
        Rect rect = new Rect();
        mPainText.getTextBounds(message, 0, message.length(), rect);
        int textW = rect.width();//文字宽
        int textH = rect.height();//文字高
        return textW;
    }

    private int getTxtH(String message) {
        Rect rect = new Rect();
        mPainText.getTextBounds(message, 0, message.length(), rect);
        int textW = rect.width();//文字宽
        int textH = rect.height();//文字高
        return textH;
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
        setMeasuredDimension(mWidth, mHeight);

    }
}
