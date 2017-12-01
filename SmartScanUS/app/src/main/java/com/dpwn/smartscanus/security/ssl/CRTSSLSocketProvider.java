package com.dpwn.smartscanus.security.ssl;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by cekangak on 10/19/2015.
 */
public class CRTSSLSocketProvider implements Provider<SSLSocketFactory> {

    @Inject
    private Context context;

    @Override
    public SSLSocketFactory get() {
        return SSLSocketFactoryCRTBuilder.getSSLSocketFactory(context, "ditDen");
    }
}
