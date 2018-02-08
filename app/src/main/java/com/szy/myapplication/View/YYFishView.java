package com.szy.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/2/5 0005.
 * 自定义阴阳鱼view
 */

public class YYFishView extends View {
    private Paint paintBlack, paintWhite;
    private Context mContext;
    private Canvas canvas;
    private int xCentre, yCentre;
    private int mWidth, mHeight;
    private int radiusBlack, radiusWhite;
    private float rota = 0;

    public YYFishView(Context context) {
        super(context);
        initview(context);
    }

    public YYFishView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public YYFishView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    private void initview(Context context) {
        mContext = context;
        paintBlack = new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setAntiAlias(true);
        paintBlack.setStyle(Paint.Style.FILL);
        paintWhite = new Paint();
        paintWhite.setColor(Color.WHITE);
        paintWhite.setAntiAlias(true);
        paintWhite.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(rectF, paint);
        canvas.translate(xCentre, yCentre);
        canvas.rotate(rota);
        canvas.save();
        drawCirBackgroud();
        draMinCircle();
        rota = rota + 10;
        postInvalidateDelayed(50);
    }

    private void drawCirBackgroud() {
        canvas.save();

        canvas.drawCircle(0, 0, radiusBlack, paintBlack);
        radiusWhite = radiusBlack - 0;
        RectF rectF = new RectF(0 - radiusWhite, 0 - radiusWhite, radiusWhite, radiusWhite);
        canvas.drawArc(rectF, -90, 180, true, paintWhite);
        canvas.restore();
    }

    private void draMinCircle() {
        canvas.save();
        canvas.translate(0, -radiusWhite / 2);
        RectF rectF = new RectF(0 - radiusWhite / 2, 0 - radiusWhite / 2, radiusWhite / 2, radiusWhite / 2);
        canvas.drawArc(rectF, -270, 180, true, paintWhite);
        canvas.drawCircle(0, 0, 20, paintBlack);
        canvas.restore();

        canvas.translate(0, radiusWhite / 2);
        RectF rectF1 = new RectF(0 - radiusWhite / 2, 0 - radiusWhite / 2, radiusWhite / 2, radiusWhite / 2);
        canvas.drawArc(rectF1, -90, 180, true, paintBlack);
        canvas.drawCircle(0, 0, 20, paintWhite);
        canvas.restore();

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
        } else {//如果布局里面设置的wrap_content
            mWidth = 300;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 300;
        }
        if (mWidth > mHeight) {
            mWidth = mHeight;
        } else {
            mHeight = mWidth;
        }
        setMeasuredDimension(mWidth, mHeight);
        xCentre = mWidth / 2;
        yCentre = mHeight / 2;
        radiusBlack = mHeight / 2-6;

    }
}
