package com.szy.myapplication.UI.Util;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 视频的录制和播放
 */
public class VideoRecorderActivity extends BaseActivity {
    private TextView tvStartRecoreVideo, tvStopRecoreVideo, tvRecordNumber;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    File avfile; //存储位置
    //标志
    private boolean isRecording = false;
    private Disposable disposable;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvStartRecoreVideo = $(R.id.tvStartRecoreVideo);
        tvStopRecoreVideo = $(R.id.tvStopRecoreVideo);
        tvRecordNumber = $(R.id.tvRecordNumber);
        surfaceView = $(R.id.surfaceview);
        holder = surfaceView.getHolder();
        camera = Camera.open(0); //开启相机
        camera.setDisplayOrientation(90);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tvStartRecoreVideo.setOnClickListener(onClickListener);
        tvStopRecoreVideo.setOnClickListener(onClickListener);
        holder.addCallback(callback);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvStartRecoreVideo://开始录制
                    startRecorderVideo();
                    break;
                case R.id.tvStopRecoreVideo://停止录制
                    stopRecoreVideo();
                    break;
            }
        }
    };

    private void startRecorderVideo() {
        //文件保存位置
        avfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.mp4");
        if (avfile.exists()) {
            avfile.delete();
        }
        try {
            avfile.createNewFile();
        } catch (IOException e) {
        }
        mediaRecorder = new MediaRecorder();
        //*******************************************
        if (camera == null) {
            camera = Camera.open(0); //开启相机
            camera.setDisplayOrientation(90);
        }
        //预览时旋转90度正常
        Camera.Parameters params = camera.getParameters();
        //自动聚焦,不然的话可嫩不清晰
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        camera.setParameters(params);
        //解锁相机允许另一个进程访问它
        camera.unlock();
        mediaRecorder.setCamera(camera);
//********************************************
        mediaRecorder.reset();
        //设置声音来源 和 视频来源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置输出文件的格式,必须在设置编码格式之前设置
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //设置编码格式 音频 和 视频 格式要设置正确，不然有时候录制黑屏
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置分辨率,可以改变清晰度 这个值没设置对的话会崩溃  在编码格式之后设置 可以默认
//        mediaRecorder.setVideoSize(1920, 1080);
        //设置帧率,每秒25帧 这个值没设置对的话会崩溃  在编码格式之后设置
//        mediaRecorder.setVideoFrameRate(30);
        setVideoProfile();
        // 输出旋转90度播放时，但是预览时没有旋转90度,预览由Camera决定
        mediaRecorder.setOrientationHint(90);
        //设置输出文件位置 注意这里不是传入一个文件，而是路径
        mediaRecorder.setOutputFile(avfile.getAbsolutePath());
        //设置预览
        mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
        //准备录制
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始录制
        mediaRecorder.start();
        sendCheckCode();
        tvStartRecoreVideo.setEnabled(false);
        tvStopRecoreVideo.setEnabled(true);
        isRecording = true;
    }

    /**
     * 设置视频的像素质量
     */
    private void setVideoProfile() {
        CamcorderProfile profile = null;
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P))
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
        else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P))
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P))
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH))
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        if (profile != null)
            mediaRecorder.setProfile(profile);
        else
            mediaRecorder.setVideoEncodingBitRate(1920 * 1080 * 5);
    }

    private void stopRecoreVideo() {
        if (isRecording) {
            //停止录制
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            //
            tvStartRecoreVideo.setEnabled(true);
            tvStopRecoreVideo.setEnabled(false);
        }
        if (camera != null) {
            camera.stopPreview();
        }
        if (avfile != null && avfile.exists() && avfile.length() > 0) {
            startActivity(new Intent(mContext, PlayVideoActivity.class).putExtra("path", avfile.getPath()));
        }
        if (disposable != null && disposable.isDisposed() == false) {
            disposable.dispose();
        }

    }

    /**
     * 基本的rxjava的用法
     */
    private void sendCheckCode() {
        final int a = 15;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(a + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return aLong + 1;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {//监听刚开始的时候
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                tvRecordNumber.setText("录制时间" + aLong + "秒");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                tvRecordNumber.setText("录制时间");
                stopRecoreVideo();
            }
        });


    }


    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                if (camera != null) {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder mHolder, int i, int i1, int i2) {
            if (camera == null || mHolder.getSurface() == null) {
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                camera.stopPreview();
            } catch (Exception e) {
                // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                camera.setPreviewDisplay(mHolder);
                camera.setDisplayOrientation(90);
                Camera.Parameters parameters = camera.getParameters();
                parameters.set("orientation", "portrait");
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                camera.setParameters(parameters);
                camera.startPreview();

            } catch (Exception e) {
                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.stopPreview();
            camera = null;
        }
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
