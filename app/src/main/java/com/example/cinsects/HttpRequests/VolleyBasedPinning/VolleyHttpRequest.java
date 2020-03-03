package com.example.cinsects.HttpRequests.VolleyBasedPinning;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cinsects.HttpRequests.PinningBrokenException;

import java.util.function.BiFunction;

import javax.net.ssl.SSLSocketFactory;
import static com.example.cinsects.OwnUtils.BackgroundToast.backgroundToast;

public class VolleyHttpRequest {
    private RequestQueue pinningQueue;
    private RequestQueue normalQueue;
    private Context mCtx;


    public VolleyHttpRequest(Context context) {
        mCtx = context;
        normalQueue = getRequestQueue(false);
        pinningQueue = getRequestQueue(true);
    }


    /**
     * Returns the single request queue
     */
    private RequestQueue getRequestQueue(boolean pinning) {
        if (pinning) {
            try {
                SSLSocketFactory sslSF = SSLSocketFactoryPinning.getSSLSocketFactory(mCtx);
                pinningQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HurlStack(null, sslSF));
                Log.i("SSLSecurity", "certificate pinning ok");

            } catch (Exception e) {
                e.printStackTrace();
                Log.w("SSLSecurity", "no certificate pinning, but was requested");
                backgroundToast(mCtx, "Error creating volley request queue with certificate pinning");
            }
            return pinningQueue;
        }
        else {
            Log.i("SSLSecurity", "no certificate pinning ok");
            return Volley.newRequestQueue(mCtx.getApplicationContext());
        }

    }

    /**
     * Adds a request to the single request queue
     * @param req The request
     * @param <T> The type of the request
     */
    public <T> void addToRequestQueue(Request<T> req, boolean pinning) {
        if (pinning) {
            pinningQueue.add(req);
        } else {
            normalQueue.add(req);
        }
    }
}
