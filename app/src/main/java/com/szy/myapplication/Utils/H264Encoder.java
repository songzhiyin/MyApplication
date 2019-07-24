package com.szy.myapplication.Utils;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

public class H264Encoder {
    private final static int TIMEOUT_USEC = 12000; //超时时间
    private MediaCodec mediaCodec; //核心
    public boolean isRunning = false; //flag
    private int width, height, framerate;
    public byte[] configbyte;
    private BufferedOutputStream outputStream;

    //存储camera返回的视频数据yuv(NV21)
    public ArrayBlockingQueue<byte[]> yuv420queue = new ArrayBlockingQueue<byte[]>(10);

    public H264Encoder(int width, int height, int framerate) {
        this.width = width;
        this.height = height;
        this.framerate = framerate;
        MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", width, height);
//设置编码器的数据格式
        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar); //NV12(YUV420SP)的数据格式
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, width * height * 5); //比特率 bite/s
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
        try {
            mediaCodec = MediaCodec.createEncoderByType("video/avc");
//创建编码器
            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();
            createfile();//准备存储视频录制数据的文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createfile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.mp4";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //核心
    public void startEncoder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                byte[] input = null;
                long pts = 0;
                long generateIndex = 0;

                while (isRunning) {
                    if (yuv420queue.size() > 0) {
//得到一帧数据
                        input = yuv420queue.poll();

                        //YUV420 数据在内存中的长度是 width * hight * 3 / 2 (Y占UV占0.5)
                        byte[] yuv420sp = new byte[width * height * 3 / 2];
                        //必须要转换格式，否则视频录得内容播放出来颜色有偏差
                        NV21TONV12(input, yuv420sp, width, height);
                        input = yuv420sp;
                    }
                    if (input != null) {
                        try {
                            ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
                            ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
                            int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
                            if (inputBufferIndex >= 0) {
//当前帧的时间戳
                                pts = computePresentationTime(generateIndex);
//得到编码的输入缓冲区
                                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                                inputBuffer.clear();
//向缓冲区添加数据
                                inputBuffer.put(input);
//缓冲区数据入编码器
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, pts, 0);
                                generateIndex += 1;
                            }
//定义一个BufferInfo保存outputBufferIndex的帧信息
                            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                            int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
                            while (outputBufferIndex >= 0) {
//得到输出缓冲区
                                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                                byte[] outData = new byte[bufferInfo.size];
//将数据写入outData
                                outputBuffer.get(outData);
                                if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_CODEC_CONFIG) {
//关键帧信息或初始化的信息
                                    configbyte = new byte[bufferInfo.size];
                                    configbyte = outData;
                                } else if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_SYNC_FRAME) {
//关键帧
                                    byte[] keyframe = new byte[bufferInfo.size + configbyte.length];
                                    System.arraycopy(configbyte, 0, keyframe, 0, configbyte.length);
                                    System.arraycopy(outData, 0, keyframe, configbyte.length, outData.length);
                                    outputStream.write(keyframe, 0, keyframe.length);
                                } else {
                                    outputStream.write(outData, 0, outData.length);
                                }
//释放输出缓冲区，进行下一次编码操作
                                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //编解码完成，停止编解码，并释放资源
                mediaCodec.stop();
                ;
                mediaCodec.release();
                //关闭数据流
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private long computePresentationTime(long frameIndex) {
        return frameIndex * 1000000 / framerate;
    }


    private void NV21TONV12(byte[] nv21, byte[] nv12, int width, int height) {
        if (nv21 == null || nv12 == null) return;
        int frameSize = width * height;
        int i = 0, j = 0;
        System.arraycopy(nv21, 0, nv12, 0, frameSize);
        for (i = 0; i < frameSize; i++) {
            nv12[i] = nv21[i];
        }
        for (j = 0; j < frameSize / 2; j += 2) {
            nv12[frameSize + j + 1] = nv21[j + frameSize];
        }
        for (j = 0; j < frameSize / 2; j += 2) {
            nv12[frameSize + j] = nv21[j + frameSize + 1];
        }
    }

    public void stopEncoder() {
        isRunning = false;
    }

    public void putDate(byte[] bytes) {
        if (yuv420queue.size() >= 10) {
            yuv420queue.poll();
        }
        yuv420queue.add(bytes);
    }

}
