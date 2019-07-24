package com.szy.myapplication.UI.Util;

import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import java.io.File;
import java.util.Calendar;

/**
 * 视频的录制和播放
 */
public class VideoPlayActivity extends BaseActivity implements SurfaceHolder.Callback {
    private static final String TAG = "MainActivity";
    private SurfaceView mSurfaceview;
    private Button mBtnStartStop;
    private Button mBtnPlay;
    private boolean mStartedFlg = false;//是否正在录像
    private boolean mIsPlay = false;//是否正在播放录像
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera camera;
    private MediaPlayer mediaPlayer;
    private String path;
    private TextView textView;
    private int text = 0;
    private android.os.Handler handler = new android.os.Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            text++;
            textView.setText(text + "");
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        mBtnStartStop = (Button) findViewById(R.id.btnStartStop);
        mBtnPlay = (Button) findViewById(R.id.btnPlayVideo);
        textView = (TextView) findViewById(R.id.text);
        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mBtnPlay.setOnClickListener(onClickListener);
        mBtnStartStop.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnStartStop://录制视频
                    recordVideo();
                    break;
                case R.id.btnPlayVideo://播放视频
                    playVideo();
                    break;
            }
        }
    };

    /**
     * 录制视频
     */
    private void recordVideo() {
        if (mIsPlay) {//是否正在播放视频
            if (mediaPlayer != null) {
                mIsPlay = false;
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        if (!mStartedFlg) {//是否正在播放视频
            handler.postDelayed(runnable, 1000);
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
            }
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            if (camera != null) {
                camera.setDisplayOrientation(90);//设置视频预览角度
                camera.unlock();
                mRecorder.setCamera(camera);
            }

            try {
                // 这两项需要放在setOutputFormat之前
                mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);//设置音频来源
                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置视频来源

                // Set output file format
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                // 这两项需要放在setOutputFormat之后
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                mRecorder.setVideoSize(640, 480);//设置视频分辨率
                mRecorder.setVideoFrameRate(30);//设置录制视频的帧率即1秒30帧
                mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);//设置视频的大小
                mRecorder.setOrientationHint(90);//设置视频的角度
                //设置记录会话的最大持续时间（毫秒）
                mRecorder.setMaxDuration(30 * 1000);
                mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

                path = getSDPath();
                if (path != null) {
                    File dir = new File(path + "/recordtest");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    path = dir + "/" + getDate() + ".mp4";
                    mRecorder.setOutputFile(path);
                    mRecorder.prepare();
                    mRecorder.start();
                    mStartedFlg = true;
                    mBtnStartStop.setText("Stop");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //stop
            if (mStartedFlg) {
                try {
                    handler.removeCallbacks(runnable);
                    mRecorder.stop();
                    mRecorder.reset();
                    mRecorder.release();
                    mRecorder = null;
                    mBtnStartStop.setText("Start");
                    if (camera != null) {
                        camera.release();
                        camera = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mStartedFlg = false;
        }
    }


    /**
     * 播放视频
     */
    private void playVideo() {
        mIsPlay = true;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        Uri uri = Uri.parse(path);
        mediaPlayer = MediaPlayer.create(mContext, uri);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒

        String date = "" + year + (month + 1) + day + hour + minute + second;
        Log.d(TAG, "date:" + date);

        return date;
    }

    /**
     * 获取SD path
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }

        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceview = null;
        mSurfaceHolder = null;
        handler.removeCallbacks(runnable);
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
            Log.d(TAG, "surfaceDestroyed release mRecorder");
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
