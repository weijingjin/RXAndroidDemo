package com.ovo.network.error;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ovo.network.R;
import com.ovo.network.error.impl.ReloadrListener;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class ErrorViewHolder extends ErrorHolder {
    private Context mContext;
    private ReloadrListener mListener;
    public ErrorViewHolder(Context context) {
        super(context);
        mContext = context;
    }
    public void setOnListener(ReloadrListener listener){
        mListener = listener;
    }

    @Override
    public View setEmpty(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.empty, null);
    }

    @Override
    public View setRetry(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.reload, null);
        view.findViewById(R.id.bt_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onReload();
                }
            }
        });
        return view;
    }

    @Override
    public View setLoading(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.loading, null);
    }

}
