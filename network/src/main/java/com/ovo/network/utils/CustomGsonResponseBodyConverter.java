package com.ovo.network.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ovo.network.base.BaseResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        try {
            BaseResponse response = (BaseResponse) adapter.fromJson(value.charStream());
            if (response.getResult() == 0) {
                return (T) response.getContent();
            } else {
                // 特定 API 的错误，在相应的 DefaultObserver 的 onError 的方法中进行处理
                throw new ServerResponseException(response.getResult()+"");
            }
        } finally {
            value.close();
        }
    }
}
