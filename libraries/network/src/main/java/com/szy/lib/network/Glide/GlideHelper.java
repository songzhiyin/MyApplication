package com.szy.lib.network.Glide;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.szy.lib.network.R;

/**
 * Created by bingju on 2017/1/11.
 */
public class GlideHelper {
    /**
     * 初始化Glide三方库
     * @param application
     */
    public static void initGlide(Application application) {
        //因为Glide默认不支持https图片的加载，所以对源码进行了修改。glide的初始化放到了清单文件中
//        Glide.get(application).register(GlideUrl.class, InputStream.class,
//                new OkHttpUrlLoader.Factory(new OkHttpClient()));
    }


    public static void showAvatar(Context context, String url, ImageView img) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(img);
    }
    /**
     * 加载网络图片并传递给imageview
     * @param context 上下文对象
     * @param imageUrl 图片的网络地址
     * @param img imageview对象
     */
    public static void showImage(Context context, String imageUrl, ImageView img) {
        if(imageUrl!=null&&imageUrl.length()>0){
            Glide.with(context)
                    .load(imageUrl)
                    .asBitmap()
                    .animate(android.R.anim.fade_in)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
        }

    }
    /**
     * 加载网络图片并传递给imageview
     * @param context 上下文对象
     * @param imageUrl 图片的网络地址
     * @param img imageview对象
     * @param placeholder_icon 默认加载图片、加载失败展示的图片
     */
    public static void showImage(Context context, String imageUrl, ImageView img,int placeholder_icon) {
        if(context!=null){
            if(imageUrl!=null&&imageUrl.length()>0){
                Glide.with(context)
                        .load(imageUrl)
                        .asBitmap()
                        .animate(android.R.anim.fade_in)
                        .placeholder(placeholder_icon)
                        .error(placeholder_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img);
            }
        }

    }

    /**
     * 加载图片并进行裁剪为圆形
     * @param context
     * @param url
     * @param img
     * @param placeholder_icon
     */
    public static void showAvatar(Context context, String url, ImageView img,int placeholder_icon) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder_icon)
                .error(placeholder_icon)
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(img);
    }
    /**
     * 加载网络图片并传递给imageview
     * @param context 上下文对象
     * @param imageUrl 图片的网络地址
     * @param img imageview对象
     * @param placeholder_icon 默认加载图片
     * @param error_icon 加载失败展示的图片
     */
    public static void showImage(Context context, String imageUrl, ImageView img,int placeholder_icon,int error_icon) {
        if(context!=null){
            if(imageUrl!=null&&imageUrl.length()>0){
                Glide.with(context)
                        .load(imageUrl)
                        .asBitmap()
                        .animate(android.R.anim.fade_in)
                        .placeholder(placeholder_icon)
                        .error(error_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img);
            }
        }


    }
    public static void showImage(Context context, byte[] imageUrl, ImageView iv) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .animate(android.R.anim.fade_in)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }
    /**
     * Glide
     * */
    public static void getBitmap(Context context, String imageUrl, final CallBackBitmap callBackBitmap) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(){

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (callBackBitmap == null) {
                            throw new IllegalArgumentException("resource can't null");
                        }
                        callBackBitmap.call(resource);

                    }
                });
    }

}
