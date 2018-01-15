package com.szy.myapplication.UI;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.szy.myapplication.Adapter.ItemBezierAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BezierDemoActivity extends BaseActivity {
    private ItemBezierAdapter adapter;
    private ImageView img_shopp;
    private RelativeLayout relativeLayout;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bezier_demo;
    }

    @Override
    protected void initViews() {
        super.initViews();
        recyclerView = $(R.id.recylist_bezier_list);
        adapter = new ItemBezierAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyDeviderDecoration(mContext, R.color.main_line_color, 1));
        recyclerView.setAdapter(adapter);
        setTextTitleName("贝塞尔曲线");
        img_shopp = $(R.id.img_bezier_demo_shopp);
        relativeLayout = $(R.id.rela_bezier_demo);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        adapter.addBottonDatas(datas);
        recyclerView.notifyDataSetChanged();
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        adapter.setOnItemImgClickListner(onItemImgClickListner);
    }

    /**
     * item中添加商品的点击事件
     */
    private ItemBezierAdapter.OnItemImgClickListner onItemImgClickListner = new ItemBezierAdapter.OnItemImgClickListner() {
        @Override
        public void onClick(ImageView imgview) {
            //1、获取底层布局的坐标
            int parent[] = new int[2];
            relativeLayout.getLocationInWindow(parent);
            //2、获取列表中点击view的坐标
            int startLoc[] = new int[2];
            imgview.getLocationInWindow(startLoc);
            //3、获取购物车的坐标
            int endLoc[] = new int[2];
            img_shopp.getLocationInWindow(endLoc);
            //4、创建一个新的view，将点击的view赋值到新的view上，再将该view添加到底层布局中
            final ImageView goods = new ImageView(getApplicationContext());
            goods.setImageDrawable(imgview.getDrawable());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            relativeLayout.addView(goods, params);
            //5、获取起始点的坐标和终点的坐标，计划坐标时根据布局的层数做响应的变化
            float startX = startLoc[0];
            float startY = startLoc[1] - parent[1];
            float toX = endLoc[0];
            float toY = endLoc[1] - parent[1];
            //6、设置view的移动轨迹
            Path path = new Path();
            path.moveTo(startX, startY);
            path.quadTo((startX + toX) / 2, startY, toX, toY);
            mPathMeasure = new PathMeasure(path, false);
            //7、属性动画实现
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
            valueAnimator.setDuration(1000);
            // 匀速插值器
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    //8、获取view移动过程中view的坐标，并将view移动到当前动画轨迹的位置上
                    mPathMeasure.getPosTan(value, mCurrentPosition, null);
                    goods.setTranslationX(mCurrentPosition[0]);
                    goods.setTranslationY(mCurrentPosition[1]);
                }
            });
            valueAnimator.start();

            //9、设置动画的监听，当动画结束时去掉添加的那个view
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    relativeLayout.removeView(goods);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    };
}
