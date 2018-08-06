package com.lib.szy.pullrefresh.PullreFresh;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Scroller;


/**
 * Created by 宋志银 2017/09/26
 * 描述:自定义下拉刷新和自动加载的recyclerview
 */

public class PullRecyclerView extends RecyclerView {

    private float mLastY = -1; // save event y
    /**
     * 滚动需要的时间
     */
    public final static int SCROLL_DURATION = 200;

    /**
     * 提示消息显示时间
     */
    public final static int MESSAGE_SHOW_DURATION = 2000;
    /**
     * 阻尼效果
     */
    private final static float OFFSET_RADIO = 1.5f;
    /**
     * 上拉加载的距离,默认50px
     */
    private static final int PULL_LOAD_MORE_DELTA = 50;
    /**
     * 下拉刷新加载的最大距离
     */
    private double PULL_Refresh_MORE_DELTA = 0;
    /**
     * 头部刷新时距离父控件的距离
     */
    private int PULL_VIEW_HEIGHT = 0;

    /**
     * 是否设置为自动加载更多,默认不可以
     */
    private boolean mEnableAutoLoading = false;
    /**
     * 当列表的item数量大于5时才会触发自动加载功能
     */
    private int visibleThreshold = 5;
    /**
     * 是否可以上拉  默认可以
     */
    private boolean mEnablePullLoad = true;
    /**
     * 是否可以下拉   默认不可以
     */
    private boolean mEnablePullRefresh = false;
    /**
     * 是否正在加载
     */
    private boolean mPullLoading = false;
    /**
     * 是否正在刷新
     */
    private boolean mPullRefreshing = false;
    /**
     * 区分上拉和下拉
     */
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    //滚动类
    private Scroller mScroller;

    //头布局控件
    private RecyclerViewHeader mHeaderView;
    //尾控件
    private RecyclerViewFooter mFooterView;
    //消息提示类
    private MessageRelativeLayout mParent;
    //adapter的装饰类
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;


    public PullRecyclerView(Context context) {
        this(context, null);
    }

    public PullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        //滚动类
        mScroller = new Scroller(context, new DecelerateInterpolator());
        //获取到头布局
        mHeaderView = new RecyclerViewHeader(context);
        mHeaderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //获取尾布局
        mFooterView = new RecyclerViewFooter(context);
        mFooterView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    /**
     * 获取头部刷新时距离父控件的距离
     *
     * @return
     */
    public int getPullViewHeight() {
        return PULL_VIEW_HEIGHT;
    }

    /**
     * 设置刷新时，头部距离父控件的距离
     *
     * @param height
     */
    public void settPullViewHeight(int height) {
        this.PULL_VIEW_HEIGHT = height;
    }


    /**
     * 设置是否自动加载
     */
    public void setmEnableAutoLoading(boolean mEnableAutoLoading) {
        this.mEnableAutoLoading = mEnableAutoLoading;
        if (mEnableAutoLoading) {
            setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastItem = getLastItem();
                    RecyclerView.LayoutManager layoutManager = getLayoutManager();
                    int totalItemCount = layoutManager.getItemCount();//列表上所有的item数量
                    Log.i("recycler", "lastVisibleItem: " + lastItem + "  totalItemCount:" + totalItemCount + "  dy" + dy);
                    if (lastItem >= totalItemCount - 1 && dy > 0) {
                        if (mPullLoading == false) {
                            startAutoLoadingAnication();
                        }
                    } else {
                        if (dy > 0) {
                            mFooterView.show_layout();
                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        LinearLayoutManager lm = (LinearLayoutManager) getLayoutManager();
//                        int lastVisiblePosition = lm.findLastVisibleItemPosition();//最后一个item的position
//                        int totalItemCount = lm.getItemCount();//列表上所有的item数量
//                        if (lastVisiblePosition >= totalItemCount - 1) {
//
////                        System.out.println("====自动加载");
//                        }
//                    }
                }
            });
        }
    }

    //To find the maximum value in the array
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 设置自动加载数据的item数量限制，只有列表item数量大于5时才会自动加载
     *
     * @param newVisibleThreshold
     */
    public void setvisibleThreshold(int newVisibleThreshold) {
        this.visibleThreshold = newVisibleThreshold;
    }

    /**
     * 开启自动加载数据的动画
     */
    public void startAutoLoadingAnication() {
        if (mPullLoading == false && mEnableAutoLoading == true) {
            mFooterView.setStateAutoLoading(RecyclerViewFooter.STATE_AUTOLOADSTART);//开启自动加载的动画
            mPullLoading = true;
            mOnRefreshListener.onLoadMore();
        }
    }

    /**
     * 隐藏自动加载的布局
     */
    public void setVisibleLoadingData() {
        mFooterView.setVisibleLoadingData(false);
    }

    /**
     * 数据加载成功，还有数据可以进行加载
     */
    public void autoLoadingSucceed() {
        mFooterView.setStateAutoLoading(RecyclerViewFooter.STATE_AUTOLOADSUCCED);
        mPullLoading = false;
    }

    /**
     * 数据加载成功，暂无新数据
     */
    public void autoLoadingFinish() {
        mFooterView.setStateAutoLoading(RecyclerViewFooter.STATE_AUTOLOADFINISH);
        mPullLoading = false;
    }

    /**
     * 数据加载失败
     */
    public void autoLoadingError() {
        mFooterView.setStateAutoLoading(RecyclerViewFooter.STATE_AUTOLOADERROR);
        mPullLoading = false;
    }

    /**
     * 数据加载失败
     */
    public void autoLoadingNetwork() {
        mFooterView.setStateAutoLoading(RecyclerViewFooter.STATE_AUTOLOADERROR);
        mPullLoading = false;
    }


    /**
     * 设置是否启用下拉刷新
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) {
            mHeaderView.setVisibility(View.INVISIBLE);
        } else {
            mHeaderView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置是否启用上拉加载
     *
     * @param enable
     */
    public void setPullLoadingEnable(boolean enable) {
//        mEnablePullLoad = enable;
//        if (!mEnablePullLoad) {
//            mFooterView.hide();
//        } else {
//            mFooterView.show();
//        }
    }

    /**
     * 设置刷新头部的title
     *
     * @param text
     */
    public void setPullContent(String text) {
        mHeaderView.setPullContent(text);
    }

    /**
     * 设置头部刷新的图片
     *
     * @param imagePath
     */
    public void setPullImage(String imagePath) {
        mHeaderView.setPullImage(imagePath);
    }

    private Adapter adapter;

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);

        super.setAdapter(mHeaderAndFooterWrapper);

        //添加头,确保是第一个
        mHeaderAndFooterWrapper.addHeaderView(mHeaderView);
        //添加尾,确保是第最后一个
        mHeaderAndFooterWrapper.addFootView(mFooterView);
        //获取到它的父容器
        if (getParent() instanceof MessageRelativeLayout) {
            mParent = (MessageRelativeLayout) getParent();
        }
    }

    /**
     * 刷新数据
     */
    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (mHeaderAndFooterWrapper != null) {
            mHeaderAndFooterWrapper.notifyDataSetChanged();
        }
    }

    /**
     * 添加头部方法
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderAndFooterWrapper.addHeaderView(view);
    }

    /**
     * 移除头部方法
     *
     * @param view
     */
    public void removeHeaderView(View view) {
        mHeaderAndFooterWrapper.removeHeaderView(view);
    }


    /**
     * 获取添加的头部view的数量
     */
    public int getHeadersCount() {
        if (mHeaderAndFooterWrapper != null) {
            return mHeaderAndFooterWrapper.getHeadersCount();
        }
        return 0;
    }

    /**
     * 获取列表上的item数量
     *
     * @return
     */
    public int getItemCount() {
        if (mHeaderAndFooterWrapper != null) {
            return mHeaderAndFooterWrapper.getItemCount();
        }
        return getChildCount();
    }

    /**
     * 添加尾部方法
     *
     * @param view
     */
    public void addFootView(View view) {
        mHeaderAndFooterWrapper.addFootView(view);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mLastY == -1) {
            mLastY = e.getRawY();
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下的时候记录值
                mLastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:


                float moveY = e.getRawY();
                //手指滑动的差值
                float distanceY = moveY - mLastY;
                mLastY = moveY;

                //第一个条目完全显示   //头部高度大于0   deltaY大于0  向下移动
                if ((getFirstItem() == 0 || getFirstItem() == 1) && (mHeaderView.getVisibleHeight() > 0 || distanceY > 0)) {
                    // 更新头部高度
                    if (mEnablePullRefresh) {
                        updateHeaderHeight(distanceY / OFFSET_RADIO);
                    }
                } else if (isSlideToBottom() && (mFooterView.getBottomMargin() > 0 || distanceY < 0)) {
                    //已经到达底部,改变状态或者自动加载
                    updateFooterHeight(-distanceY / OFFSET_RADIO);
                }

                break;
            default:
                mLastY = -1; // 复位
                if ((getFirstItem() == 0 || getFirstItem() == 1)) {
                    // 松手的时候  高度大于  一定值  调用刷新
                    if (mEnablePullRefresh && mHeaderView.getVisibleHeight() > mHeaderView.getRealityHeight()) {
                        //变为刷新状态
                        mPullRefreshing = true;
                        mHeaderView.setState(RecyclerViewHeader.STATE_REFRESHING);
                        //回调事件
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                } else if (isSlideToBottom()) {
//                    // invoke load more.
//                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA && !mPullLoading) {
//                        mPullLoading = true;
//                        mFooterView.setState(RecyclerViewFooter.STATE_LOADING);
//                        if (mOnRefreshListener != null) {
//                            mOnRefreshListener.onLoadMore();
//                        }
//
//                    }
//                    resetFooterHeight();
                } else {
//                    resetFooterHeight();
                    resetHeaderHeight();
                }

                break;
        }


        return super.onTouchEvent(e);
    }

    /**
     * 获取第一个可见的item
     * @return
     */
    private int getFirstItem() {
        int firstItem = 0;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            //Position to find the final item of the current LayoutManager
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            firstItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
            // this array into an array of position and then take the maximum value that is the last show the position value
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            firstItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
        }
        return firstItem;
    }

    /**
     * 获取最后一个可见的item位置
     * @return
     */
    private int getLastItem() {
        int lastItem = 0;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            //Position to find the final item of the current LayoutManager
            lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1)
                lastItem = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1)
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
            // this array into an array of position and then take the maximum value that is the last show the position value
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastItem = findMax(lastPositions);
        }
        return lastItem;
    }

    /**
     * 更新尾部加载
     *
     * @param distance
     */
    private void updateFooterHeight(float distance) {
//        int height = mFooterView.getBottomMargin() + (int) distance;
//        if (mEnablePullLoad && !mPullLoading) {
//            if (height > PULL_LOAD_MORE_DELTA) {
//                //改变状态
//                mFooterView.setState(RecyclerViewFooter.STATE_READY);
//            } else {
//                mFooterView.setState(RecyclerViewFooter.STATE_NORMAL);
//            }
//        }
//        mFooterView.setBottomMargin(height);

    }

    /**
     * 更新头部刷新
     *
     * @param distance
     */
    private void updateHeaderHeight(float distance) {
        PULL_Refresh_MORE_DELTA = mHeaderView.getRealityHeight() * 2;
//        当活动的距离大于默认最大距离时不再刷新UI效果
        if (PULL_Refresh_MORE_DELTA < (int) distance + mHeaderView.getVisibleHeight()) {
            return;
        }
        // 设置头部高度,原先的高度加上
        mHeaderView.setVisibleHeight((int) distance + mHeaderView.getVisibleHeight());
        // 未处于刷新状态，更新箭头
        if (mEnablePullRefresh && !mPullRefreshing) {
            //下拉高度到达可以刷新的位置
            if (mHeaderView.getVisibleHeight() > mHeaderView.getRealityHeight()) {
                mHeaderView.setState(RecyclerViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(RecyclerViewHeader.STATE_NORMAL);
            }
        }
        //移动到顶部
        smoothScrollBy(0, 0);
    }

    /**
     * 重置头部高度
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisibleHeight();
        if (height == 0) // 如果=0  是不可见的 直接返回
            return;

        if (mPullRefreshing && height <= mHeaderView.getRealityHeight()) {
            return;
        }

        int finalHeight = 0;

        if (mPullRefreshing && height > mHeaderView.getRealityHeight()) {
            finalHeight = mHeaderView.getRealityHeight();
        }
        if (mParent != null) {
            if (mHeaderView.getVisibleHeight() == mParent.getHeaderMessageViewHeight()) {
                finalHeight = mParent.getHeaderMessageViewHeight();
            }
        }

        mScrollBack = SCROLLBACK_HEADER;//设置标识
        if (mPullRefreshing) {//只有在刷新时移动的距离才增加预留范围
            mScroller.startScroll(0, height, 0, PULL_VIEW_HEIGHT + finalHeight - height, SCROLL_DURATION);
        } else {
            mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        }

        // 触发计算滚动
        invalidate();
    }

    /**
     * 重置尾部高度
     */
    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;//设置标识
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    /**
     * 下拉刷新数据，数据加载成功
     */
    public void refreshDataSucced() {
        mHeaderView.setState(RecyclerViewHeader.STATE_SUCCEED);
        stopRefresh();
    }

    /**
     * 下拉刷新数据，数据加载失败
     */
    public void refreshDataError() {
        mHeaderView.setState(RecyclerViewHeader.STATE_ERROR);
        stopRefresh();
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        //开启延迟0.3秒在进行更新UI
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollBack = SCROLLBACK_HEADER;//设置标识
                int obligateHeight;
                if (mParent != null) {
                    obligateHeight = mParent.getHeaderMessageViewHeight();
                } else {
                    obligateHeight = 0;
                }
                int height = mHeaderView.getVisibleHeight();
                if (mPullRefreshing) {
                    //是否复位

                    mPullRefreshing = false;

                    //显示更新了多少条消息
                    if (mParent != null) {
                        mParent.showMessage();
                    }
                    mScroller.startScroll(0, height, 0, obligateHeight - height, SCROLL_DURATION);
                    // 触发计算滚动
                    invalidate();

                    //延时执行复位移动
                    if (mParent != null) {
                        handler.removeCallbacksAndMessages(null);
                        handler.sendEmptyMessageDelayed(1, MESSAGE_SHOW_DURATION);
                    }
                }
            }
        }, 300);


    }

    /**
     * 停止加载
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(RecyclerViewFooter.STATE_NORMAL);
        }
    }

    /**
     * 消息
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mHeaderView.getVisibleHeight() == mParent.getHeaderMessageViewHeight()) {
//                resetHeaderHeight();
                mScroller.startScroll(0, mHeaderView.getVisibleHeight(), 0, -mHeaderView.getVisibleHeight(), SCROLL_DURATION);
                postInvalidate();
            }

            mParent.hideMessage();
        }
    };

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisibleHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    private OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;

    }


    /**
     * 刷新接口,
     */
    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * 判断是否到底
     *
     * @return
     */
    private boolean isSlideToBottom() {
        return computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange();
    }
}





















