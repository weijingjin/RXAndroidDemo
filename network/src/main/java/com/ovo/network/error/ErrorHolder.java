package com.ovo.network.error;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public abstract class ErrorHolder {
    public View mLoadingView;
    public View mEmptyView;
    public View mRetryView;
    public ErrorHolder(Context context){
        init(context);
    }

    private void init(Context context){
        mEmptyView = setEmpty(context);
        mRetryView = setRetry(context);
        mLoadingView = setLoading(context);
    }

    public abstract View setEmpty(Context context);

    public abstract View setRetry(Context context);

    public abstract View setLoading(Context context);
}
