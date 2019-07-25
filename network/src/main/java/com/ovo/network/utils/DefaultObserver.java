package com.ovo.network.utils;

import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.ovo.network.R;
import com.ovo.network.error.ErrorView;
import com.ovo.network.error.ErrorViewHolder;
import com.ovo.network.error.impl.ReloadrListener;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class DefaultObserver<T> implements Observer<T> {

    private ErrorViewHolder errorHolder;
    public void initError(ErrorView errorView, ReloadrListener listener){
        if (errorView != null && listener != null){
            errorHolder = new ErrorViewHolder(AppContext.context);
            errorView.setHolder(errorHolder);
            errorHolder.setOnListener(listener);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        }else if(e instanceof NullPointerException){
            onException(ExceptionReason.NULL_POINT_ERROR);
        }else if(e instanceof ServerResponseException){
            onFail(e.getMessage());
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFinish();
    }

    @Override
    public void onComplete() {

    }
    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    public void onFail(String message) {
        ToastUtils.show(message);
    }

    public void onFinish(){}

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show(R.string.connect_error, Toast.LENGTH_SHORT);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show(R.string.connect_timeout, Toast.LENGTH_SHORT);
                break;

            case BAD_NETWORK:
                ToastUtils.show(R.string.bad_network, Toast.LENGTH_SHORT);
                break;

            case PARSE_ERROR:
                ToastUtils.show(R.string.parse_error, Toast.LENGTH_SHORT);
                break;

            case NULL_POINT_ERROR:
                ToastUtils.show(R.string.null_point_error, Toast.LENGTH_SHORT);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.show(R.string.unknown_error, Toast.LENGTH_SHORT);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 空指针
         */
        NULL_POINT_ERROR,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
