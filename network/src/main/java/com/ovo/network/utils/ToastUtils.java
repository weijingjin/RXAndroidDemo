package com.ovo.network.utils;

import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static CharSequence info = "";
    private static int infos = -1;

    /***显示*/
    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.context, text, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (info.equals(text)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                info = text;
                toast.setText(text);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void show(int text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.context, text, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (infos == text) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                infos = text;
                toast.setText(text);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

}
