package com.szy.myapplication.UI.Util;

import android.media.MediaCodec;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import java.io.File;

/**
 * 播放视频
 */
public class PlayVideoActivity extends BaseActivity {
    private VideoView mVideoView;
    private String path = "";
    private MediaController mediaco;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mVideoView = $(R.id.videoView);
        path = getIntent().getStringExtra("path") != null ? getIntent().getStringExtra("path") : "";
        mVideoView.setVideoPath(path);
        mediaco=new MediaController(mContext);
        mVideoView.setMediaController(mediaco);
        mVideoView.requestFocus();//获取焦点
        mediaco.show();
        mVideoView.start();
    }

    @Override
    protected void initEvents() {
        super.initEvents();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!mVideoView.isPlaying()) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlabackVideo();
    }

    private void stopPlabackVideo() {
        try {
            if (mVideoView != null) {
                mVideoView.stopPlayback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
