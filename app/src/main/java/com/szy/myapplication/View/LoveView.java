package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.szy.myapplication.R;

public class LoveView extends View {
    private int mWidth, mHeight;

    public LoveView(Context context) {
        super(context);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.save();
//        Path path = new Path();
//        path.moveTo(-1, -41);
//        path.cubicTo(50, -89,113, -77,113, -27);
//        path.cubicTo(113, 21,50, 90,-1, 138);
//        path.cubicTo(-49, 90,-110, 27,-110, -22);
//        path.quadTo(-110, -72,-49, -89);
//        canvas.clipPath(path);
//        canvas.drawColor(Color.RED);
        Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        int width = getWidth();
        int height = getHeight();

        // 绘制心形
        Path path = new Path();
        path.moveTo(width / 2, height / 4);
        path.cubicTo((width * 6) / 7, height / 9, (width * 12) / 13, (height * 2) / 5, width / 2, (height * 7) / 12);
        canvas.drawPath(path, paint);

        Path path2 = new Path();
        path2.moveTo(width / 2, height / 4);
        path2.cubicTo(width / 7, height / 9, width / 13, (height * 2) / 5, width / 2, (height * 7) / 12);
        canvas.drawPath(path2, paint);

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
            mWidth = 200;
        }
        //如果布局里面设置的模式是固定值或者是设置match_parent，则取测量的大小
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {//如果布局设置的是wrap_content，这里取布局的文本高度和内边距
            mHeight = 200;
        }
        if (mWidth > mHeight) {
            mHeight = mWidth;
        } else {
            mWidth = mHeight;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
