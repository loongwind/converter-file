package com.cm.retrofit2.converter.file;

import com.cm.retrofit2.converter.file.body.ProgressResponseBody;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Cmad on 2016/5/4.
 */
public class FileConverter implements Converter<ResponseBody, File> {

    /**
     * 添加请求头的key,后面数字为了防止重复
     */
    public static final String SAVE_PATH = "savePath2016050433191";

    static final FileConverter INSTANCE = new FileConverter();

    @Override
    public File convert(ResponseBody value) throws IOException {
        String saveFilePath = null;
        try {

            //使用反射获得我们自定义的response
            Class aClass = value.getClass();
            Field field = aClass.getDeclaredField("delegate");
            field.setAccessible(true);
            ResponseBody body = (ResponseBody) field.get(value);
            if(body instanceof ProgressResponseBody){
                saveFilePath = ((ProgressResponseBody)body).getSavePath();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return FileUtils.writeResponseBodyToDisk(value, saveFilePath);
    }
}
