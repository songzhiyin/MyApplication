package com.lib.szy.pullrefresh.PullreFresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.szy.pullrefresh.R;


/**
 * Created by 宋志银 2017/09/26
 * 描述:recyclerview的尾部view
 */

public class RecyclerViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    /**
     * 开始自动自动加载
     */
    public final static int STATE_AUTOLOADSTART = 0;
    /**
     * 自动加载成功
     */
    public final static int STATE_AUTOLOADSUCCED = 1;
    /**
     * 自动加载失败
     */
    public final static int STATE_AUTOLOADERROR = 2;
    /**
     * 数据加载完毕暂时没有数据了
     */
    public final static int STATE_AUTOLOADFINISH = 3;
    /**
     * 底部view布局
     */
    private LinearLayout moreView;

    private Context context;
    private View contentView;
    private TextView hintView;
    private ImageView mLoadImageView;


    public RecyclerViewFooter(Context context) {
        this(context, null);
    }

    public RecyclerViewFooter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();

    }

    private void initView() {
        moreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pullrefrefh_recyclerview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentView = moreView.findViewById(R.id.pullrefrefh_footer_content);
        mLoadImageView = (ImageView) moreView.findViewById(R.id.img_pullRefresh_loading);
        start_loading_animation(mLoadImageView);
        hintView = (TextView) moreView.findViewById(R.id.pullrefrefh_footer_hint_TextView);
        hide_layout();
    }

    /**
     * 根据自动加载状态值的变化，刷新尾部view的布局
     *
     * @param status
     */
    public void setStateAutoLoading(int status) {
//        if (STATE_AUTOLOADSUCCED == status) {
//            moreView.setVisibility(GONE);
//        } else {
//            moreView.setVisibility(VISIBLE);
//        }
        moreView.setVisibility(VISIBLE);
        switch (status) {
            case STATE_AUTOLOADSTART://开启加载动画
                mLoadImageView.setVisibility(VISIBLE);
                hintView.setVisibility(INVISIBLE);
                break;
            case STATE_AUTOLOADSUCCED://加载成功
                mLoadImageView.setVisibility(INVISIBLE);
                hintView.setVisibility(VISIBLE);
                hintView.setText("数据加载成功");
                break;
            case STATE_AUTOLOADERROR://加载失败
                mLoadImageView.setVisibility(INVISIBLE);
                hintView.setVisibility(VISIBLE);
                hintView.setText("加载失败");
            case STATE_AUTOLOADFINISH://加载完毕无法在进行加载
                mLoadImageView.setVisibility(INVISIBLE);
                hintView.setVisibility(VISIBLE);
                hintView.setText("没有更多数据了");
                break;
        }
    }

    /**
     * 隐藏显示自动加载的布局
     */
    public void setVisibleLoadingData(boolean show) {
        if (show)
            moreView.setVisibility(VISIBLE);
        else
            moreView.setVisibility(GONE);
    }

    /**
     * 开启加载动画
     */
    private void start_loading_animation(ImageView loading_image) {
        loading_image.setImageResource(R.drawable.anim_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) loading_image.getDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }

    /**
     * 设置状态
     *
     * @param state
     */
    public void setState(int state) {
//        hintView.setVisibility(View.INVISIBLE);
//        mLoadImageView.setVisibility(View.INVISIBLE);
//        hintView.setVisibility(View.INVISIBLE);
//        if (state == STATE_READY) {
//            hintView.setVisibility(View.VISIBLE);
//            hintView.setText("松开载入更多");
//        } else if (state == STATE_LOADING) {
//            mLoadImageView.setVisibility(View.VISIBLE);
//        } else {
//            hintView.setVisibility(View.VISIBLE);
//            hintView.setText("查看更多");
//        }
    }

    /**
     * 设置距离下边的BottomMargin
     *
     * @param height
     */
    public void setBottomMargin(int height) {
        if (height < 0) return;
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.bottomMargin = height;
        contentView.setLayoutParams(lp);

    }

    /**
     * 获取BottomMargin
     *
     * @return
     */
    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.height = 0;
        contentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        contentView.setLayoutParams(lp);
    }

    /**
     * 将尾部布局进行隐藏
     */
    public void hide_layout() {
        LayoutParams lp = (LayoutParams) moreView.getLayoutParams();
        lp.height = 0;
        moreView.setLayoutParams(lp);
    }

    /**
     * 将尾部布局进行显示
     */
    public void show_layout() {
        LayoutParams lp = (LayoutParams) moreView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        moreView.setLayoutParams(lp);
    }

}
