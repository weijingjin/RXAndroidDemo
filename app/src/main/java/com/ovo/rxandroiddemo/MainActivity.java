package com.ovo.rxandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ovo.network.utils.AppContext;
import com.ovo.network.utils.DefaultObserver;
import com.ovo.network.utils.ProgressUtils;
import com.ovo.rxandroiddemo.been.KLineInfo;
import com.ovo.rxandroiddemo.utils.RetrofitHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends Activity {

    /****
     * 上拉刷新
     */
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppContext.context = this;

        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("RefreshLoadMore", "--------------onRefresh");
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();//恢复上拉状态
                    }
                },2000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("RefreshLoadMore", "--------------onLoadMore");
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (false){//没有更多数据
                            //设置之后，将不会再触发加载事件
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }else {
                            refreshLayout.finishLoadMore();
                        }
                    }
                },2000);
            }
        });

//        test();
//        testJust();
//        testMap();

        RetrofitHelper.getApiServer().getKLine1()
//                .compose(this.<String>bindToLifecycle())
                .compose(ProgressUtils.applyProgressBar(MainActivity.this, "加载"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<KLineInfo>() {
                    @Override
                    public void onSuccess(KLineInfo response) {
                        Log.e("====", response.toString() + "");
                    }
                });
    }

    private void test(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String a = "123";
                emitter.onNext(a);
                Thread.sleep(6*1000);
                emitter.onNext(a);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("-------", "onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("-------", s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("-------", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("-------", "onComplete");
                    }
                });

    }

    /**
     * 上传图片
     */
    private void upload(){
        File file = new File("");
        RequestBody requestBody = RequestBody.create(MediaType.parse(""), file);
        MultipartBody.Part body = MultipartBody.Part
                .createFormData("image", file.getName(),requestBody);
        RetrofitHelper.getApiServer().upload(body)
                .compose(ProgressUtils.applyProgressBar(MainActivity.this, "上传"))
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("-------", "上传成功");
                    }
                });
    }

    private void testJust(){
        Observable.just("123").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("-------", s);
                    }
                });
    }

    /**操作符 map*/
    public void testMap(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("123");
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.valueOf(s);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("====", integer + "");
                    }
                });
    }
}
