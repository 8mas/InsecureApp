package com.example.cinsects.HttpRequests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.cinsects.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

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
                SSLSocketFactory sslSF = getSSLSocketFactory();
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

    /**
     * Used for certificate pinning
     */
    private SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream isHttpbin = mCtx.getResources().openRawResource(R.raw.httpbinorg);
        Certificate caHttpbin = cf.generateCertificate(isHttpbin);


        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("caHttpbin", caHttpbin);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}
