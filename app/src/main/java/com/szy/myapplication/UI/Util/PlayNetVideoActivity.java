package com.szy.myapplication.UI.Util;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.ToastUtils;

/**
 * 在线播放网络视频
 */
public class PlayNetVideoActivity extends BaseActivity {
    private VideoView videoView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_play_net_video;
    }

    @Override
    protected void initViews() {
        super.initViews();
        videoView=$(R.id.videoView);
        setTextTitleName("播放视频");
        setBackOnclickListner(mContext);
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        Uri uri=Uri.parse("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4");
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(mContext));
        videoView.start();
        videoView.requestFocus();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initEvents() {
        super.initEvents();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int i1) {
                if(what==MediaPlayer.MEDIA_INFO_BUFFERING_START){//开始缓存
                    Log.i(TAG, "onInfo: 开始预加载");
                }
                if(what==MediaPlayer.MEDIA_INFO_BUFFERING_END){//缓存结束
                    Log.i(TAG, "onInfo: 预加载结束");
                }
                return true;
            }
        });
    }
}
