package com.example.cinsects.OwnUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Class to make Toasts, even if the current thread is in the background
 */
public class BackgroundToast {
    public static void backgroundToast(final Context context, final String msg, final int toastDuration){
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, toastDuration).show();
                }
            });
        }
    }
    public static void backgroundToast(final Context context, final String msg) {
        backgroundToast(context, msg, Toast.LENGTH_SHORT);
    }
}
