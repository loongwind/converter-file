package com.cm.retrofit2.converter.file.body;

/**
 * Created by Cmad on 2016/4/28.
 * 响应体进度回调接口，比如用于文件下载中
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
