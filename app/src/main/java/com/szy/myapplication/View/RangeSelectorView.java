package com.szy.myapplication.View;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.szy.myapplication.R;

public class RangeSelectorView extends View {
    private int xLift, xRight, yCentre;//左圆点的X坐标、右圆点的X坐标、直线的Y轴坐标
    private int mXLift, mXRight;//左边界限、右边界限
    private int mWidth, mHeight;
    private int radius = 6;
    private int allNumber = 1, liftNumer = 1, rightNumber = 1;
    private Context mContext;
    private Paint paint_line;
    private Paint paint_circle;
    private boolean isFirst = true;//是否是首次加载布局
    private OnXyTouchListener onXyTouchListener;//滑动触摸回调接口
    private int code = 0;//触摸事件的类型
    private int difference = 0;//点击点和圆点的差值


    public RangeSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RangeSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void initView(Context context, @Nullable AttributeSet attrs) {
        this.mContext = context;
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.RangeSelectorView);
        paint_line = new Paint();
        paint_line.setColor(array.getColor(R.styleable.RangeSelectorView_line_color, R.color.gray_666));
        paint_line.setAntiAlias(true);
        paint_line.setStyle(Paint.Style.FILL);
        paint_line.setStrokeWidth(array.getFloat(R.styleable.RangeSelectorView_line_width, 6f));
        paint_circle = new Paint();
        paint_circle.setStyle(Paint.Style.FILL);
        paint_circle.setAntiAlias(true);
        paint_circle.setColor(array.getColor(R.styleable.RangeSelectorView_circle_color, R.color.red));
        paint_circle.setStrokeWidth(array.getFloat(R.styleable.RangeSelectorView_line_width, 6f));
        allNumber = array.getInteger(R.styleable.RangeSelectorView_allnumber, 100);
        rightNumber = allNumber;
        radius = array.getInteger(R.styleable.RangeSelectorView_radius, 20);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mXLift, yCentre, xLift, yCentre, paint_line);
        canvas.drawLine(xLift, yCentre, xRight, yCentre, paint_circle);
        canvas.drawLine(xRight, yCentre, mXRight, yCentre, paint_line);
        canvas.drawCircle(xLift, yCentre, radius, paint_circle);
        canvas.drawCircle(xRight, yCentre, radius, paint_circle);

    }

    public void setOnXyTouchListener(OnXyTouchListener onXyTouchListener) {
        this.onXyTouchListener = onXyTouchListener;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }

    /**
     * 设置左边刻度
     *
     * @param liftNumer
     */
    public void setLiftNumer(int liftNumer) {
        if (liftNumer >= 0 && liftNumer < rightNumber) {
            this.liftNumer = liftNumer;
            setXLiftUI();
        } else {
            Toast.makeText(mContext, "设置的范围不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 设置右边刻度
     *
     * @param rightNumber
     */
    public void setRightNumber(int rightNumber) {
        if (rightNumber > liftNumer && rightNumber <= allNumber) {
            this.rightNumber = rightNumber;
            setXrightUI();
        } else {
            Toast.makeText(mContext, "设置的范围不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 计算左边圆点按钮的UI
     */
    private void setXLiftUI() {
        int xLiftNew = radius;
        if (liftNumer == 0) {
            xLiftNew = mXLift + radius;
        } else {
            //先计算圆点在X轴上的滑动范围距离 再加上圆点的X轴距离
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
        //先计算圆点在X轴上的滑动范围距离 再加上圆点的X轴距离
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
            if (heightSize < radius + 6)
                mHeight = radius + 6;
            else
                mHeight = heightSize;
        } else {
            mHeight = radius + 6;
        }
        setMeasuredDimension(mWidth, mHeight);
        if (isFirst) {//当界面布局发生变化进行重新绘制的时候，会再次调用该方法，所以需要判断是否是首次调用，只有首次调用时才能设置两个圆点的X轴坐标
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
            case MotionEvent.ACTION_DOWN://按下屏幕的时候，判断触摸点是否在左右圆点上,并且记录触摸点距离圆点的距离
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x > xLift - radius && x < xLift + radius && y > yCentre - radius && y < yCentre + radius) {
                    difference = xLift - x;
                    code = 1;
                }
                if (x > xRight - radius && x < xRight + radius && y > yCentre - radius && y < yCentre + radius) {
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
            default://重置数据
                code = 0;
                difference = 0;
                break;
        }
        return true;
    }

    /**
     * 设置触摸左边圆点之后的UI
     *
     * @param xNew
     */
    private void setOnTouLiftUI(int xNew) {
        xNew = difference + xNew;//因为触摸点和圆点有误差，所以需要进行修正
        //当滑动到距离左边界限点不足20像素时，直接滑动到左边界限点防止出现无法滑动到边界的情况
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
            //获取最后离开屏幕点X坐标到初始左边圆点坐标和初始最右边圆点到初始左边圆点坐标的比例，再计算对应的数值
            liftNumer = allNumber * (xNew - mXLift - radius) / (mXRight - mXLift - radius * 2);
            xLift = xNew;
            invalidate();
            if (onXyTouchListener != null) {
                onXyTouchListener.onLiftNumber(liftNumer);
            }
        }
    }

    private void setOnTouRightUI(int xNew) {
        xNew = difference + xNew;//因为触摸点和圆点有误差，所以需要进行修正
        //当滑动到距离右边界限点不足20像素时，直接滑动到右边界限点防止出现无法滑动到边界的情况
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
            //获取最后离开屏幕点X坐标到初始左边圆点坐标和初始最右边圆点到初始左边圆点坐标的比例，再计算对应的数值
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
