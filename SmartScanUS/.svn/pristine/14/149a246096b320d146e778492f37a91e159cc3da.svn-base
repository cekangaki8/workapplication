package com.dpwn.smartscanus.security.ssl;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.net.ssl.SSLSocketFactory;

import com.dpwn.smartscanus.R;

/**
 * Created by fshamim on 29.09.14.
 */
public class CIServerSSLSocketFactoryProvider implements Provider<SSLSocketFactory> {

    @Inject
    private Context context;

    @Override
    public SSLSocketFactory get() {
        return SSLSocketFactoryBuilder.getSSLSocketFactory(context,R.raw.mockupserverkeystore,"abcd1234");
    }

}
