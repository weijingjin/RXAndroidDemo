package com.ovo.network.error;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class ErrorView extends LinearLayout {
    private final int KEY_NORMAL= 0, KEY_LOADING = 1, KEY_EMPTY= 2, KEY_RETRY = 3;
    private int mViewType = KEY_NORMAL;
    private ErrorHolder mHolder;

    public ErrorView(Context context) {
        super(context);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHolder(ErrorHolder holder){
        mHolder = holder;
        initView();
    }

    private void initView() {
        addView(mHolder.mLoadingView,0);
        addView(mHolder.mEmptyView,0);
        addView(mHolder.mRetryView,0);
        setShowOrHide();
        LayoutParams ll = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mHolder.mLoadingView.setLayoutParams(ll);
        mHolder.mEmptyView.setLayoutParams(ll);
        mHolder.mRetryView.setLayoutParams(ll);

    }
    public void showLoadingView(){
        if (mViewType == KEY_LOADING) return;
        mViewType = KEY_LOADING;
        setShowOrHide();
    }
    public void showEmptyView(){
        if (mViewType == KEY_EMPTY) return;
        mViewType = KEY_EMPTY;
        setShowOrHide();
    }
    public void showRetryView(){
        if (mViewType == KEY_RETRY) return;
        mViewType = KEY_RETRY;
        setShowOrHide();
    }
    public void showContentView(){
        if (mViewType == KEY_NORMAL) return;
        mViewType = KEY_NORMAL;
        setShowOrHide();
    }
    private void setShowOrHide(){
        if (mHolder == null){
            throw new RuntimeException("OtherView::请先设置OtherHolder");
        }
        mHolder.mLoadingView.setVisibility(mViewType == KEY_LOADING ? VISIBLE : GONE);
        mHolder.mEmptyView.setVisibility(mViewType == KEY_EMPTY ? VISIBLE : GONE);
        mHolder.mRetryView.setVisibility(mViewType == KEY_RETRY ? VISIBLE : GONE);
    }


}
