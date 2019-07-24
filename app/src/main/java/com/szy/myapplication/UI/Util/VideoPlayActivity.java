package com.szy.myapplication.UI.Util;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.H264Encoder;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * 视频的录制和播放
 */
public class VideoPlayActivity extends BaseActivity {
    private TextView tvStartRecoreVideo, tvStopRecoreVideo;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Camera camera;
    private H264Encoder encoder;
    private int width = 1280;
    private int height = 720;
    private int framerate = 30; //一秒30帧
    private boolean isRecordVideo = false;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvStartRecoreVideo = $(R.id.tvStartRecoreVideo);
        tvStopRecoreVideo = $(R.id.tvStopRecoreVideo);
        surfaceView = $(R.id.surfaceview);
        holder = surfaceView.getHolder();
        camera = Camera.open(); //开启相机
        camera.setDisplayOrientation(90);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tvStartRecoreVideo.setOnClickListener(onClickListener);
        tvStopRecoreVideo.setOnClickListener(onClickListener);
        camera.setPreviewCallback(previewCallback);
        holder.addCallback(callback);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvStartRecoreVideo://开始录制
                    if (isRecordVideo == false && encoder != null) {
                        isRecordVideo = true;
                        encoder.startEncoder(); //开始编码
                    }
                    break;
                case R.id.tvStopRecoreVideo://停止录制
                    if (camera != null && isRecordVideo) {
                        camera.setPreviewCallback(null);
                        camera.stopPreview();
                        camera = null;
                        isRecordVideo = false;
                    }
                    if (encoder != null) {
                        encoder.stopEncoder();
                    }
                    break;
            }
        }
    };

    /**
     * 判断该设备是否支持H264编码
     */
    private boolean supportH264Codec() {
        // 遍历支持的编码格式信息,并查询有没有支持H.264(avc)的编码
        if (Build.VERSION.SDK_INT >= 18) {
            //计算可用的编解码器数量
            int number = MediaCodecList.getCodecCount();
            for (int i = number - 1; i > 0; i--) {
                //获得指定的编解码器信息
                MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
                //得到支持的类型
                String[] types = codecInfo.getSupportedTypes();
                //查询有没有支持H.264(avc)的编码
                for (int j = 0; j < types.length; j++) {
                    if (types[j].equalsIgnoreCase("video/avc")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private byte[] rotateYUV420Degree270(byte[] data, int imageWidth, int imageHeight){
        byte[] yuv =new byte[imageWidth*imageHeight*3/2];
        // Rotate the Y luma
        int i =0;
        for(int x = imageWidth-1;x >=0;x--){
            for(int y =0;y < imageHeight;y++){
                yuv[i]= data[y*imageWidth+x]; 		            i++;
            }   }// Rotate the U and V color components  	i = imageWidth*imageHeight;
        for(int x = imageWidth-1;x >0;x=x-2){
            for(int y =0;y < imageHeight/2;y++){ 		       yuv[i]= data[(imageWidth*imageHeight)+(y*imageWidth)+(x-1)]; 		         i++; 		       yuv[i]= data[(imageWidth*imageHeight)+(y*imageWidth)+x]; 		            i++;
            }
        }
        return yuv;
    }
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {
//返回相机预览的视频数据,并给H264Encoder编码压缩为H.264(avc)的文件test.mp4
            //这里面的Bytes的数据就是NV21格式的数据
            if (encoder != null && isRecordVideo) {
                encoder.putDate(rotateYUV420Degree270(bytes,1280,720)); //将一帧的数据传过去处理
            }
        }
    };

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewFormat(ImageFormat.NV21); //设置数据格式
                parameters.setPreviewSize(1280, 720);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //编码初始化
            encoder = new H264Encoder(width, height, framerate);

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (camera != null) {
                camera.release();
            }
        }
    };

}
