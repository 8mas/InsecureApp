package com.example.cinsects.HttpRequests.VolleyBasedPinning;

import android.content.Context;

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

public class SSLSocketFactoryPinning {
    /**
     * Cert Pinning with a SSLSocketFactory
     */
    public static SSLSocketFactory getSSLSocketFactory(Context ctx)
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream isHttpbin = ctx.getResources().openRawResource(R.raw.httpbinorg);
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
