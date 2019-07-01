package com.ovo.network.base;

/**
 * 服务器响应数据对应基类
 * @param <T>
 */
public class BaseResponse<T> {
    private int result;
    private String message;
    private T content;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
