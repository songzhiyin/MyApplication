package com.lib.szy.pullrefresh.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by songzhiyin on 2017/11/7.
 * 自定义首页的分割线
 */

public class HomeDeviderDecoration extends RecyclerView.ItemDecoration {
    private int mydevider;
    private Paint dividerPaint;
    private int startCount = 0;

    public HomeDeviderDecoration(Context context, int colorResources, int dividerHeight) {
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(context.getResources().getColor(colorResources));
        //设置分割线宽度
        mydevider = dividerHeight;
    }

    public int getStartCount() {
        return startCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mydevider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = startCount; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + mydevider;

            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}
