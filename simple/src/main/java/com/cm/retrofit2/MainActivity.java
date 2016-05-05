package com.cm.retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cm.retrofit2.converter.file.body.ProgressResponseListener;
import com.cm.retrofit2.service.DownloadService;
import com.cm.retrofit2.service.ServiceGenerator;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ProgressResponseListener {


    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.download).setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View v) {

        download();
    }

    private void download() {
        String url = "1f178a82b9014a90b04cc438ae773912b21beec1.jpg";

        DownloadService downloadService = ServiceGenerator.createResponseService(DownloadService.class,this);

        String savePath = getExternalFilesDir(null)+ File.separator+"img.jpg";

        Call<File> call = downloadService.download(url,savePath);

        mProgressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<File>() {
            @Override
            public void onResponse(Call<File> call, Response<File> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.e("onResponse","file path:"+response.body().getPath());
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<File> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
        mProgressBar.setProgress((int)(1.0f*bytesRead/contentLength*100));
    }
}
