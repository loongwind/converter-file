package com.cm.retrofit2.converter.file.body;

/**
 * Created by Cmad on 2016/4/28.
 * 请求体进度回调接口，比如用于文件上传中
 */
public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}