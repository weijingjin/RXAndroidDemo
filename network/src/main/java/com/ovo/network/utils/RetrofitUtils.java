package com.ovo.network.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit工具类
 * 对OKHTTP进行配置
 */
public class RetrofitUtils {

    public static OkHttpClient.Builder getOkHttpClientBuilder(){
        HttpLoggingInterceptor loggingInterceptor=
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                try {
                    Log.e("===1===RetrofitUtils", URLDecoder.decode(s, "utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("===2===RetrofitUtils", s);
                }
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(AppContext.context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024*1024*100);
        return new OkHttpClient.Builder()
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(new HttpHeaderInterceptor())
//                .addNetworkInterceptor(new HttpCacheInterceptor())
                // https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
//                .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())
//                .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache);
    }

    public static Retrofit.Builder getRetrofitBuilder(String baseUrl){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = RetrofitUtils.getOkHttpClientBuilder().build();
        return new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(CustomGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * 自定义adapter，解决由于数据类型为Int,实际传过来的值为Float，导致解析出错的问题
     * 目前的解决方案为将所有Int类型当成Double解析，再强制转换为Int
     */
    public static TypeAdapter<ResponseBody> INTEGER = new TypeAdapter<ResponseBody>() {
        @Override
        public void write(JsonWriter out, ResponseBody value) throws IOException {

        }

        @Override
        public ResponseBody read(JsonReader in) throws IOException {
            return null;
        }
    };
}
