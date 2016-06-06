package com.cm.retrofit2.converter.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Cmad on 2016/5/3.
 */
public class FileUtils {

    /**
     * 将文件写入本地
     * @param body http响应体
     * @param path 保存路径
     * @return 保存file
     */
    public static File writeResponseBodyToDisk(ResponseBody body, String path) {

        File saveFile = null;
        try {

            saveFile = new File(path);

            createDirs(saveFile);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(saveFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                }

                outputStream.flush();

                return saveFile;
            } catch (IOException e) {
                return saveFile;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return saveFile;
        }
    }

    public static void createDirs(File file){
        if(file != null){
            String dir = file.getParent();
            createDirs(dir);
        }
    }

    public static void createDirs(String dirPath){
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
