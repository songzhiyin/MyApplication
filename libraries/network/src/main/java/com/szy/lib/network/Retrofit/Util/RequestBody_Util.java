package com.szy.lib.network.Retrofit.Util;

import android.webkit.MimeTypeMap;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/3/3.
 * RequestBody的工具类
 */

public class RequestBody_Util {
    /**
     * 将String数据转化为RequestBody形式
     * @param value 文本对象
     * @return
     */
    public static RequestBody parseRequestBody_String(String value) {
        if(value!=null){
            return RequestBody.create(MediaType. parse("text/plain"), value);
        }else {
            return RequestBody.create(MediaType. parse("text/plain"), "");
        }

    }

    /**
     * 将File数据转化为RequestBody形式
     * @param file 文件对象
     * @return
     */
    public static RequestBody parseRequestBody_File(File file) {
        return RequestBody.create(MediaType. parse("image/jpg"), file);
    }

    public static String parseImageMapKey(String key, String fileName) {
        return key + "\"; filename=\"" + fileName;
    }
    public static String get_file_mimetype(File file){
        String mimeType = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(
                        MimeTypeMap.getFileExtensionFromUrl(file.getPath()));
        return mimeType;
    }
}
