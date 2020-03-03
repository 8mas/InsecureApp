package com.example.cinsects.HttpRequests.OkHttpBasedPinning;

import java.io.IOException;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpRequest {
    private OkHttpClient normalClient = new OkHttpClient();
    private OkHttpClient pinningClient;

    public OkHttpRequest(String url, String sha256Key){
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(url, sha256Key)
                .build();

        pinningClient = new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();
    }

    public String request(String url, boolean pinning) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = pinning ? pinningClient : normalClient;
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        throw new NullPointerException();
    }
}
