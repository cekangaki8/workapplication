package com.dpwn.smartscanus.http;


import com.google.inject.Provider;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by fshamim on 08.10.14.
 */
public class HttpClientProvider implements Provider<OkHttpClient> {

    @Override
    public OkHttpClient get() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClient.setConnectTimeout(30,TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);

        return okHttpClient;
    }
}
