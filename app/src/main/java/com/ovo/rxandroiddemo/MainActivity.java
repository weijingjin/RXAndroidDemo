package com.ovo.rxandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ovo.network.utils.AppContext;
import com.ovo.network.utils.DefaultObserver;
import com.ovo.network.utils.ProgressUtils;
import com.ovo.rxandroiddemo.been.KLineInfo;
import com.ovo.rxandroiddemo.utils.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppContext.context = this;
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
//
//        RetrofitHelper.getApiServer().getKLine()
////                .compose(this.<String>bindToLifecycle())
//                .compose(ProgressUtils.applyProgressBar(MainActivity.this, "加载"))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<BaseResponse<ReqKLine>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<ReqKLine> response) {
//                        Log.e("====", response.toString() + "");
//                    }
//                });
//        RetrofitHelper.getApiServer().getStr("jmtest1", "110120")
////                .compose(this.<String>bindToLifecycle())
////                .compose(ProgressUtils.applyProgressBar(MainActivity.this, "加载"))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<BaseResponse<KLineInfo>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<KLineInfo> response) {
//                        Log.e("====", response.toString() + "");
//                    }
//                });
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
