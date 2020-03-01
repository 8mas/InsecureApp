package com.example.cinsects.HttpRequests.VolleyBasedPinning;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import javax.net.ssl.SSLSocketFactory;
import static com.example.cinsects.OwnUtils.BackgroundToast.backgroundToast;

public class VolleyRequestQueue {
    private RequestQueue mRequestQueue;
    private static Context mCtx;


    public VolleyRequestQueue(Context context, boolean usePinning) {
        mCtx = context;
        mRequestQueue = getRequestQueue(usePinning);
    }


    /**
     * Returns the single request queue
     */
    private RequestQueue getRequestQueue(boolean pinning) {
        if (pinning) {
            try {
                SSLSocketFactory sslSF = SSLSocketFactoryPinning.getSSLSocketFactory(mCtx);
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HurlStack(null, sslSF));
                Log.i("SSLSecurity", "App uses certificate pinning");

            } catch (Exception e) {
                e.printStackTrace();
                Log.w("SSLSecurity", "App does not use certificate pinning");
                backgroundToast(mCtx, "Error creating volley request queue with certificate pinning");
            }
            return mRequestQueue;
        }
        else {
            return Volley.newRequestQueue(mCtx.getApplicationContext());
        }

    }

    /**
     * Adds a request to the single request queue
     * @param req The request
     * @param <T> The type of the request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

}
