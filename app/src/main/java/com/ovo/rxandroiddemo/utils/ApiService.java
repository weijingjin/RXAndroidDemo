package com.ovo.rxandroiddemo.utils;

import com.ovo.network.base.BaseResponse;
import com.ovo.rxandroiddemo.been.KLineInfo;
import com.ovo.rxandroiddemo.been.ReqKLine;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 接口API
 */
public interface ApiService {
    @GET("get")
    Call<DataInfo> getStr(@Query("param")String param);

    @GET("login/loginMobile.do")//http://120.24.66.218:80/login/loginMobile.do?account=jmtest&password=120110
    Observable<BaseResponse<KLineInfo>> getStr(@Query("account") String account, @Query("password") String password);

    @GET("klinevtwo/con")//http://120.24.66.218:80/login/loginMobile.do?account=jmtest&password=120110
    Observable<BaseResponse<ReqKLine>> getKLine();

    @GET("klinevtwo/con")//http://120.24.66.218:80/login/loginMobile.do?account=jmtest&password=120110
    Observable<KLineInfo> getKLine1();

    @GET//http://120.24.66.218:80/login/loginMobile.do?account=jmtest&password=120110
    Observable<DataInfo> getStr2(@Url String path);

    @FormUrlEncoded
    @POST("login/loginMobile.do")
    Observable<DataInfo> postStr(@Field("account") String account, @Field("password") String password);

    //上传图片
    @FormUrlEncoded
    @POST("post")
    Observable<String> upload(@Part MultipartBody.Part body);
}
