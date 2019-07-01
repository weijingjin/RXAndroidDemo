package com.ovo.retrofitdemo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("get")
    Call<String> getStr(@Query("param")String param);

    @FormUrlEncoded
    @POST("get")
    Observable<String> postStr(@Field("param")String param);
}
