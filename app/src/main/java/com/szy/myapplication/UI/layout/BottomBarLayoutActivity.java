package com.szy.myapplication.UI.layout;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.UI.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 轻量级的底部导航栏
 */
public class BottomBarLayoutActivity extends BaseActivity {
    private ViewPager mVpContent;
    private BottomBarLayout mBottomBarLayout;

    private List<TabFragment> mFragmentList = new ArrayList<>();
    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bottom_bar_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("导航栏");
        setBackOnclickListner(mContext);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        TabFragment homeFragment = new TabFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(TabFragment.CONTENT, "首页");
        homeFragment.setArguments(bundle1);
        mFragmentList.add(homeFragment);

        TabFragment videoFragment = new TabFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(TabFragment.CONTENT, "视频");
        videoFragment.setArguments(bundle2);
        mFragmentList.add(videoFragment);

        TabFragment microFragment = new TabFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString(TabFragment.CONTENT, "微头条");
        microFragment.setArguments(bundle3);
        mFragmentList.add(microFragment);

        TabFragment meFragment = new TabFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString(TabFragment.CONTENT, "我的");
        meFragment.setArguments(bundle4);
        mFragmentList.add(meFragment);
        initListener();
    }

    private void initListener() {
        mVpContent.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mBottomBarLayout.setViewPager(mVpContent);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int position) {
                Log.i("MainActivity","position: " + position);
                if (position == 0){
                    //如果是第一个，即首页
                    if (mBottomBarLayout.getCurrentItem() == position){
                        //如果是在原来位置上点击,更换首页图标并播放旋转动画
                        bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_loading);//更换成加载图标
                        bottomBarItem.setStatus(true);

                        //播放旋转动画
                        if (mRotateAnimation == null) {
                            mRotateAnimation = new RotateAnimation(0, 360,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                                    0.5f);
                            mRotateAnimation.setDuration(800);
                            mRotateAnimation.setRepeatCount(-1);
                        }
                        ImageView bottomImageView = bottomBarItem.getImageView();
                        bottomImageView.setAnimation(mRotateAnimation);
                        bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

                        //模拟数据刷新完毕
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换成首页原来图标
                                bottomBarItem.setStatus(true);//刷新图标
                                cancelTabLoading(bottomBarItem);
                            }
                        },3000);
                        return;
                    }
                }

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换为原来的图标

                cancelTabLoading(bottomItem);//停止旋转动画
            }
        });

        mBottomBarLayout.setUnread(0,20);//设置第一个页签的未读数为20
        mBottomBarLayout.setUnread(1,1001);//设置第二个页签的未读数
        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
        mBottomBarLayout.setMsg(3,"NEW");//设置第四个页签显示NEW提示文字
    }
    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
