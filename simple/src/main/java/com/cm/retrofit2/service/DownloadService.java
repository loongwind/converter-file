package com.cm.retrofit2.service;

import com.cm.retrofit2.converter.file.FileConverter;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * Created by Cmad on 2016/4/28.
 */
public interface DownloadService {

    @GET
    Call<File> download(@Url String fileUrl, @Header(FileConverter.SAVE_PATH) String path);
}
