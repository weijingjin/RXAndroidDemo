package com.ovo.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit);
        Retrofit retrofit = new Retrofit.Builder()
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //使用rxjava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //设置网络请求的Url地址
                .baseUrl("www.baidu.com")
                .build();
        // 创建网络请求接口的实例
        Api api = retrofit.create(Api.class);
        //对发送请求进行封装 传参
        Call<String> str = api.getStr("");
        //发起网络请求
        str.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        //rxjava post请求
        api.postStr("")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("--", "");
                    }
                });
    }
}
