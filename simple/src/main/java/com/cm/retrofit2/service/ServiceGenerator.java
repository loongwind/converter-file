package com.cm.retrofit2.service;

import com.cm.retrofit2.converter.file.FileConverterFactory;
import com.cm.retrofit2.converter.file.body.HttpClientHelper;
import com.cm.retrofit2.converter.file.body.ProgressRequestListener;
import com.cm.retrofit2.converter.file.body.ProgressResponseListener;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Cmad on 2016/4/27.
 */
public class ServiceGenerator {
    private static final String HOST = "http://g.hiphotos.baidu.com/image/pic/item/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(FileConverterFactory.create());


    public static <T> T createService(Class<T> tClass){
        return builder.build().create(tClass);
    }


    /**
     * 创建带响应进度(下载进度)回调的service
     */
    public static <T> T createResponseService(Class<T> tClass, ProgressResponseListener listener){
        OkHttpClient client = HttpClientHelper.addProgressResponseListener(new OkHttpClient.Builder(),listener).build();
        return builder
                .client(client)
                .build()
                .create(tClass);
    }


    /**
     * 创建带请求体进度(上传进度)回调的service
     */
    public static <T> T createReqeustService(Class<T> tClass, ProgressRequestListener listener){
        OkHttpClient client = HttpClientHelper.addProgressRequestListener(new OkHttpClient.Builder(),listener).build();
        return builder
                .client(client)
                .build()
                .create(tClass);
    }

}
