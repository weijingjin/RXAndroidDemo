package com.ovo.network.utils;

import retrofit2.Retrofit;

public class IdeaApi {
    public static <T> T getServerApi(Class<T> cls, String baseUrl){
        Retrofit retrofit = RetrofitUtils.getRetrofitBuilder(baseUrl).build();
        return retrofit.create(cls);
    }
}
