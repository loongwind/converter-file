# converter-file

自定义FileConverterFactory实现在使用Retrofit下载文件的时候将ResponseBody内容保存到指定文件路径,详细可以看这篇博客:
[FileConverterFactory实现Retrofit下载文件直接返回File](http://www.loongwind.com/archives/296.html)


###使用:

####1. 在Module的build.gradle中添加
```gradle
compile 'com.cm.retrofit2:converter-file:1.0.1'
```

####2. APIService中方法里添加`@Header(FileConverter.SAVE_PATH)`
```java
@GET
Call<File> download(@Url String fileUrl, @Header(FileConverter.SAVE_PATH) String path);
```

####3. 设置Retrofit
```java
private static Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(FileConverterFactory.create()); //添加FileConverterFactory



/**
 * 创建带响应进度(下载进度)回调的service
 */
public static <T> T createResponseService(Class<T> tClass, ProgressResponseListener listener){
    //通过HttpClientHelper获得已经添加了自定义ResponseBody的OkHttpClient
    OkHttpClient client = HttpClientHelper.addProgressResponseListener(new OkHttpClient.Builder(),listener).build();
    return builder
            .client(client)
            .build()
            .create(tClass);
}
```

####4. 使用
```java
private void download() {
    String url = "test.jpg";

    DownloadService downloadService = ServiceGenerator.createResponseService(DownloadService.class,this);

    //文件保存路径
    String savePath = getExternalFilesDir(null)+ File.separator+"img.jpg";

    Call<File> call = downloadService.download(url,savePath);


    call.enqueue(new Callback<File>() {
        @Override
        public void onResponse(Call<File> call, Response<File> response) {
            if(response.isSuccessful() && response.body() != null){
                Log.e("onResponse","file path:"+response.body().getPath());
            }

        }

        @Override
        public void onFailure(Call<File> call, Throwable t) {

        }
    });
}
```