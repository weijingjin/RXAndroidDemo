package com.ovo.network.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ProgressUtils {
    public static <T> ObservableTransformer<T, T> applyProgressBar
            (final Activity activity, String msg){
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final ProgressDialog dialog = new ProgressDialog(activityWeakReference.get());
        dialog.setMessage(msg);
        dialog.show();
        return new ObservableTransformer<T, T>(){

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Activity context;
                        if ((context = activityWeakReference.get()) != null && !context.isFinishing()){
                            dialog.dismiss();
                        }
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        return applyProgressBar(activity, "");
    }
}
