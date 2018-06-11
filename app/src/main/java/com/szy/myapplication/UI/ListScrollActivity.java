package com.szy.myapplication.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.MyOnScrollListener;
import com.szy.myapplication.Utils.ObservableHorizontalScrollView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListScrollActivity extends BaseActivity {
    private RecyclerView mRvLayer;
    private RecyclerView mRvRoom;
    private ObservableHorizontalScrollView mSvRoom;
    private List mData = new ArrayList();

    private final RecyclerView.OnScrollListener mLayerOSL = new MyOnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 当楼层列表滑动时，单元（房间）列表也滑动
            mRvRoom.scrollBy(dx, dy);
        }
    };
    private final RecyclerView.OnScrollListener mRoomOSL = new MyOnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 当单元（房间）列表滑动时，楼层列表也滑动

            mRvLayer.scrollBy(dx, dy);
        }
    };

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_list_scroll;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setBackOnclickListner(mContext);
        setTextTitleName("列表联动滑动");
        mRvLayer = $(R.id.rv_layer);
        mRvRoom = $(R.id.rv_room);
        mSvRoom = $(R.id.sv_room);
        mRvLayer.setLayoutManager(new LinearLayoutManager(this));
        mRvLayer.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#f5f5f5"))
                .size(1)
                .build()
        );
        mRvRoom.setLayoutManager(new LinearLayoutManager(this));
        mRvRoom.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#f5f5f5"))
                .size(1)
                .build()
        );
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        int layerCount = 30;
        for (int i = 0; i < layerCount; i++) {
            mData.add(new Object());
        }
        mRvLayer.setAdapter(
                new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.item_room, mData) {
                    @Override
                    protected void convert(BaseViewHolder helper, Object item) {

                    }
                });
        mRvRoom.setAdapter(
                new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.item_layer, mData) {
                    @Override
                    protected void convert(BaseViewHolder helper, Object item) {
                        LinearLayout llLayer = helper.getView(R.id.ll_layer);
                        llLayer.removeAllViews();
                        for (int i = 0; i < 5; i++) {
                            View room = View.inflate(mContext, R.layout.item_room, null);
                            View line = new View(mContext);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                            line.setLayoutParams(params);
                            line.setBackgroundColor(Color.parseColor("#f5f5f5"));
                            llLayer.addView(room);
                            llLayer.addView(line);
                        }
                    }
                });
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setSyncScrollListener();
    }

    /**
     * 设置两个列表的同步滚动
     */
    private void setSyncScrollListener() {

        mRvLayer.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            private int mLastY;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // 当列表是空闲状态时
                if (rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    onTouchEvent(rv, e);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // 若是手指按下的动作，且另一个列表处于空闲状态
                if (e.getAction() == MotionEvent.ACTION_DOWN && mRvRoom.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    // 记录当前另一个列表的y坐标并对当前列表设置滚动监听
                    mLastY = rv.getScrollY();
                    rv.addOnScrollListener(mLayerOSL);
                } else {
                    // 若当前列表原地抬起手指时，移除当前列表的滚动监听
                    if (e.getAction() == MotionEvent.ACTION_UP && rv.getScrollY() == mLastY) {
                        rv.removeOnScrollListener(mLayerOSL);
                    }
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mRvRoom.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            private int mLastY;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    onTouchEvent(rv, e);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN && mRvLayer.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastY = rv.getScrollY();
                    rv.addOnScrollListener(mRoomOSL);
                } else {
                    if (e.getAction() == MotionEvent.ACTION_UP && rv.getScrollY() == mLastY) {
                        rv.removeOnScrollListener(mRoomOSL);
                    }
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mSvRoom.setScrollViewListener(new ObservableHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
                mRvLayer.removeOnScrollListener(mLayerOSL);
                mRvRoom.removeOnScrollListener(mRoomOSL);
            }
        });
    }
}
