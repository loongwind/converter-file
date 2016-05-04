package com.cm.retrofit2.converter.file;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Cmad on 2016/5/4.
 */
public class FileConverterFactory extends Converter.Factory{

    public static FileConverterFactory create(){
        return new FileConverterFactory();
    }

    @Override
    public Converter<ResponseBody, File> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return FileConverter.INSTANCE;
    }
}
