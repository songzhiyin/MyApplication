package com.lib.szy.pullrefresh.PullreFresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lib.szy.pullrefresh.R;


/**
 * Created by 宋志银 2017/09/26
 * 描述:recyclerview的头部view
 */

public class RecyclerViewHeader extends LinearLayout {
    /**
     * 动画执行时间
     */
    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_SUCCEED = 3;
    public final static int STATE_ERROR = 4;
    /**
     * 当前状态
     */
    private int mState = STATE_NORMAL;

    //获取到头布局
    private LinearLayout mContainer;
    //获取到控件
    private ImageView mArrowImageView;
    private ImageView mLoadImageView;
    private TextView mHintTextView;
    //初始化动画
    private RotateAnimation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private RelativeLayout mRealityContent;


    public RecyclerViewHeader(Context context) {
        this(context, null);
    }

    public RecyclerViewHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        //获取下拉布局
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pullrefrefh_recyclerview_header, (ViewGroup) getParent(), true);
        //添加到改容器
        addView(mContainer, lp);
        //显示位置下面
        setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        //初始化控件
        mRealityContent = (RelativeLayout) mContainer.findViewById(R.id.pullRefresh_reality_content);
        mArrowImageView = (ImageView) mContainer.findViewById(R.id.pullRefresh_arrow);
        mLoadImageView = (ImageView) mContainer.findViewById(R.id.pullRefresh_loading);
        start_loading_animation(mLoadImageView);
        mHintTextView = (TextView) mContainer.findViewById(R.id.pullRefresh_text);

        //初始化动画
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        //如果状态相同  直接返回
        if (mState == state) return;

        //如果传进来的是刷新状态
        if (state == STATE_REFRESHING) {    // 正在加载显示圆圈进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mLoadImageView.setVisibility(VISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mArrowImageView.setImageResource(R.mipmap.hhyj_pull_down);
            mLoadImageView.setVisibility(INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL://正常状态
                if (mState == STATE_READY) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mHintTextView.setText("下拉刷新");
                break;
            case STATE_READY://可以刷新状态
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText("松开刷新数据");
                }
                break;
            case STATE_REFRESHING://刷新状态
                mHintTextView.setText("正在加载...");
                break;
            case STATE_SUCCEED://加载成功
                if (mState == STATE_REFRESHING) {
                    mHintTextView.setText("加载成功");
                    mArrowImageView.setVisibility(View.VISIBLE);
                    mArrowImageView.setImageResource(R.mipmap.hhyj_footer_succeed);
                    mLoadImageView.setVisibility(INVISIBLE);
                }
                break;
            case STATE_ERROR://数据加载失败
                if (mState == STATE_REFRESHING) {
                    mHintTextView.setText("数据加载失败");
                    mArrowImageView.setVisibility(View.VISIBLE);
                    mArrowImageView.setImageResource(R.mipmap.hhyj_footer_error);
                    mLoadImageView.setVisibility(INVISIBLE);
                }
                break;
            default:
        }
        mState = state;

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
     * 设置显示的图片
     *
     * @param imagePath
     */
    public void setPullImage(String imagePath) {
        Drawable fromPath = Drawable.createFromPath(imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        mArrowImageView.setBackground(fromPath);
        mArrowImageView.setImageBitmap(bitmap);
    }

    /**
     * 设置显示的文字
     *
     * @param text
     */
    public void setPullContent(String text) {
//        mTitleTextView.setText(text);

    }

    /**
     * 获取本身实际的高度
     */
    public int getRealityHeight() {
        return mRealityContent.getHeight();
    }


    /**
     * 设置隐藏高度
     *
     * @param height
     */
    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * 获取隐藏的高度
     *
     * @return
     */
    public int getVisibleHeight() {
        return mContainer.getLayoutParams().height;
    }
}
