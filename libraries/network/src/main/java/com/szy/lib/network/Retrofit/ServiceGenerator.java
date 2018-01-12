package com.szy.lib.network.Retrofit;


import com.szy.lib.network.Retrofit.Util.LogUtil;
import com.szy.lib.network.Retrofit.Util.NetWorkUtils;
import com.szy.lib.network.Retrofit.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author binju
 *         Created by bingju on 2017/1/4.
 */

public class ServiceGenerator {
    /**
     * 获取域名之后，访问的域名地址——获取域名接口的域名地址写在了接口类里面
     */
    public static String BASE_URL = "http://175.25.49.199:8001";//测试环境
    private static String base_url = null;
    private static OkHttpClient.Builder httpClient = NetWorkUtils.getUnsafeOkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(base_url != null ? base_url : BASE_URL);
    private static Converter.Factory converterFactory = null;
    private static CallAdapter.Factory callFactory;
    private static Interceptor interceptor;

    /**
     * 修改域名
     *
     * @param base_url
     */
    public static void setBaseUrl(String base_url) {
        builder.baseUrl((base_url != null && base_url.length() > 0) ? base_url : BASE_URL);
        BASE_URL = base_url;//当获取到新的域名时，将就域名进行修改
    }


    public static OkHttpClient.Builder getHttpClient() {
        return httpClient;
    }

    /**
     * @param base_url
     */
    public static void setBase_url(String base_url) {
        ServiceGenerator.base_url = base_url;
    }

    /**
     * @param converterFactory
     */
    public static void setConverterFactory(Converter.Factory converterFactory) {
        ServiceGenerator.converterFactory = converterFactory;
    }

    /**
     * @param interceptor
     */
    public static void setInterceptor(Interceptor interceptor) {
        ServiceGenerator.interceptor = interceptor;
    }

    /**
     * @param callFactory
     */
    public static void setCallFactory(CallAdapter.Factory callFactory) {
        ServiceGenerator.callFactory = callFactory;
    }

    /**
     * @param serviceClass
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    private static String sessionId = "";

    public static void setAuthToken(String sessionId) {
        ServiceGenerator.sessionId = (sessionId == null ? "" : sessionId);
    }
    /**
     * @param serviceClass
     * @param authToken
     * @param authToken
     * @return
     */
    private static InterceptorAd interceptorAd = null;

    public static InterceptorAd getInterceptorAd(String sessionId, String version) {
        interceptorAd = new InterceptorAd(version);
        return interceptorAd;
    }


    public static <S> S createService(Class<S> serviceClass, String sessionId, String version) {

        if (sessionId != null) {
            LogUtil.i("sessionId--", sessionId);
            httpClient.addNetworkInterceptor(getInterceptorAd(sessionId, version));
            httpClient.connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS);//设置超时时间
        }
        /**打印OKHTTP的运行日志，APP打包时一定要关闭日志功能*/
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(httpLoggingInterceptor);
        /**打印OKHTTP的运行日志，APP打包时一定要关闭日志功能*/
        builder.addConverterFactory(converterFactory != null ? converterFactory : GsonConverterFactory.create());
        builder.addCallAdapterFactory(callFactory != null ? callFactory : RxJavaCallAdapterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    static class InterceptorAd implements Interceptor {
        private String version;

        public InterceptorAd(String version) {
            this.version = version;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            //获取header并添加token
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
