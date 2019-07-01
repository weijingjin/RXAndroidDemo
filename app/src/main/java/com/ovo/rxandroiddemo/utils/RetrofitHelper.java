package com.ovo.rxandroiddemo.utils;

import com.ovo.network.utils.IdeaApi;

public class RetrofitHelper {
    private static ApiService apiService;
//    private static String SERVER = "http://120.24.66.218:80/";
    private static String SERVER = "http://13.230.33.97:8080/";
    static {
        apiService = IdeaApi.getServerApi(ApiService.class, SERVER);
    }

    public static ApiService getApiServer(){
        return apiService;
    }
}
