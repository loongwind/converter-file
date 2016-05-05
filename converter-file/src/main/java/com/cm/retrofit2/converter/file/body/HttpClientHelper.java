package com.cm.retrofit2.converter.file.body;

import com.cm.retrofit2.converter.file.FileConverter;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Cmad on 2016/4/28.
 */
public class HttpClientHelper {


    /**
     * 获取添加了自定义Response的okhttp builder
     * @return okhttp.builder
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        return addProgressResponseListener(client,null);
    }


    /**
     * 为okhttp.builder添加自定义response
     * @param builder okhttp.builder
     * @return 添加自定义response后的okhttp.builder
     */
    public static OkHttpClient.Builder addCustomResponse(OkHttpClient.Builder builder){
        return addProgressResponseListener(builder,null);
    }


    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient builder，使用clone方法返回
     */
    public static OkHttpClient.Builder addProgressResponseListener(OkHttpClient.Builder builder,final ProgressResponseListener progressListener){
        //增加拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //拦截
                Response originalResponse = chain.proceed(request);

                List<String> segments = request.url().pathSegments();
                String filename = segments.get(segments.size()-1);

                ProgressResponseBody body = new ProgressResponseBody(originalResponse.body(), progressListener);
                //从request中取出对应的header即我们设置的文件保存地址,然后保存到我们自定义的response中
                body.setSavePath(request.header(FileConverter.SAVE_PATH));
                body.setFileName(filename);
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(body)
                        .build();
            }
        });
        return builder;
    }


    /**
     * 包装OkHttpClient，用于上传文件进度的回调
     * @param progressListener 请求进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient.Builder addProgressRequestListener(OkHttpClient.Builder builder, final ProgressRequestListener progressListener){
        //增加拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), new ProgressRequestBody(original.body(),progressListener))
                        .build();
                return chain.proceed(request);
            }
        });
        return builder;
    }



}
