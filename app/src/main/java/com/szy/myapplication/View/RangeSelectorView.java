package com.szy.myapplication.View;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RangeSelectorView extends View {
    private int xLift, xRight, yCentre;
    private int mXLift, mXRight;
    private int mWidth, mHeight;
    private int radius = 60;
    private int allNumber = 300, liftNumer = 1, rightNumber = 300;
    private Context mContext;
    private Paint paint_line;
    private Paint paint_circle;
    private boolean isFirst = true;//是否是首次加载布局
    private OnXyTouchListener onXyTouchListener;
    //触摸事件的类型
    private int code = 0;
    private int difference = 0;//点击点和圆点的差值

    public RangeSelectorView(Context context) {
        super(context);
        initView(context);
    }

    public RangeSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RangeSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        paint_line = new Paint();
        paint_line.setColor(Color.GRAY);
        paint_line.setAntiAlias(true);
        paint_line.setStyle(Paint.Style.FILL);
        paint_line.setStrokeWidth(5f);
        paint_circle = new Paint();
        paint_circle.setStyle(Paint.Style.FILL);
        paint_circle.setAntiAlias(true);
        paint_circle.setColor(Color.BLUE);
        paint_line.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mXLift, yCentre, xLift, yCentre, paint_line);
        canvas.drawLine(xLift, yCentre, xRight, yCentre, paint_circle);
        canvas.drawLine(xRight, yCentre, mXRight - 2, yCentre, paint_line);
        canvas.drawCircle(xLift, yCentre, radius, paint_circle);
        canvas.drawCircle(xRight, yCentre, radius, paint_circle);

    }

    public void setOnXyTouchListener(OnXyTouchListener onXyTouchListener) {
        this.onXyTouchListener = onXyTouchListener;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }

    public void setLiftNumer(int liftNumer) {
        if (liftNumer >= 0 && liftNumer < rightNumber) {
            this.liftNumer = liftNumer;
            setXLiftUI();
        } else {
            Toast.makeText(mContext, "设置的范围不正确", Toast.LENGTH_LONG).show();
        }
    }

    public void setRightNumber(int rightNumber) {
        if (rightNumber > liftNumer && rightNumber <= allNumber) {
            this.rightNumber = rightNumber;
            setXrightUI();
        } else {
            Toast.makeText(mContext, "设置的范围不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 计算左边按钮的UI
     */
    private void setXLiftUI() {
        int xLiftNew = radius;
        if (liftNumer == 0) {
            xLiftNew = mXLift + radius;
        } else {
            xLiftNew = (mXRight - mXLift - radius * 2) * liftNumer / allNumber + radius + mXLift;
        }
        if (xLiftNew > xLift) {
            for (int i = xLift; i <= xLiftNew; i++) {
                if (xLift <= xRight - radius * 2) {
                    xLift = i;
                    invalidate();
                } else {
                    return;
                }
            }
        } else {
            for (int i = xLift; i >= xLiftNew; i--) {
                if (xLift - radius >= mXLift) {
                    xLift = i;
                    invalidate();
                } else {
                    return;
                }
            }
        }


    }

    /**
     * 计算右边按钮的UI
     */
    private void setXrightUI() {
        int xRightNew = (mXRight - mXLift - radius * 2) * rightNumber / allNumber + radius + mXLift;
        if (xRightNew > xRight) {
            for (int i = xRight; i <= xRightNew; i++) {
                if (xRight <= mXRight - radius) {
                    xRight = i;
                    invalidate();
                } else {
                    return;
                }
            }
        } else {
            for (int i = xRight; i >= xRightNew; i--) {
                if (xRight >= xLift + radius * 2) {
                    xRight = i;
                    invalidate();
                } else {
                    return;
                }
            }
        }
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
        } else {
            mWidth = 300;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = radius + 6;
        }
        setMeasuredDimension(mWidth, mHeight);
        if (isFirst) {//当界面布局发生不变，进行重新绘制的时候，会再次调用该方法，所以需要判断是否是首次调用
            mXLift = 0 + getPaddingLeft();
            xLift = mXLift + radius;
            mXRight = mWidth - getPaddingRight();
            xRight = mXRight - radius;
            isFirst = false;
            yCentre = mHeight / 2;
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                if (x > xLift - radius && x < xLift + radius) {
                    difference = xLift - x;
                    code = 1;
                }
                if (x > xRight - radius && x < xRight + radius) {
                    code = 2;
                    difference = xRight - x;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (code == 1) {
                    setOnTouLiftUI((int) event.getX());
                }
                if (code == 2) {
                    setOnTouRightUI((int) event.getX());
                }
                break;
            default:
                code = 0;
                difference = 0;
                break;
        }
        return true;
    }

    private void setOnTouLiftUI(int xNew) {
        xNew = difference + xNew;
        if (xNew < mXLift + radius + 20) {
            xLift = mXLift + radius;
            invalidate();
            liftNumer = 0;
            if (onXyTouchListener != null) {
                onXyTouchListener.onLiftNumber(liftNumer);
            }
            return;
        }
        if (xNew - radius >= mXLift && xNew <= xRight - radius * 2) {
            liftNumer = allNumber * (xNew - mXLift - radius) / (mXRight - mXLift - radius * 2);
            xLift = xNew;
            invalidate();
            if (onXyTouchListener != null) {
                onXyTouchListener.onLiftNumber(liftNumer);
            }
        }
    }

    private void setOnTouRightUI(int xNew) {
        xNew = difference + xNew;
        if (xNew > mXRight - radius - 20) {
            xRight = mXRight - radius;
            invalidate();
            rightNumber = allNumber;
            if (onXyTouchListener != null) {
                onXyTouchListener.onRightNumber(rightNumber);
            }
            return;
        }
        if (xNew >= xLift + radius * 2 && xNew <= mXRight - radius) {
            rightNumber = allNumber * (xNew - mXLift - radius) / (mXRight - mXLift - radius * 2);
            xRight = xNew;
            invalidate();
            if (onXyTouchListener != null) {
                onXyTouchListener.onRightNumber(rightNumber);
            }
        }
    }

    public interface OnXyTouchListener {
        void onLiftNumber(int number);

        void onRightNumber(int number);
    }
}
