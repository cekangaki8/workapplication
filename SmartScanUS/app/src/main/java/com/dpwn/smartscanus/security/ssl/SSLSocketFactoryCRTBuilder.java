package com.dpwn.smartscanus.security.ssl;

import android.content.Context;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.logging.L;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by cekangak on 10/19/2015.
 */
public class SSLSocketFactoryCRTBuilder {

    public static SSLSocketFactory getSSLSocketFactory(Context context, String serverName) {
        try {


            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            int resourceId = returnResoureId(serverName);
            if (resourceId == -1) {
                return null;
            }
            InputStream caInput = context.getResources().openRawResource(resourceId);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                L.d("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext.getSocketFactory();

        } catch (Exception ex) {
            L.e("ssl", ex.getMessage(), ex);
        }

        return null;
    }

    private static int returnResoureId(String serverName) {
        switch (serverName.toLowerCase()) {
            case "ditden":
                return R.raw.ditden;
            default:
                return -1;
        }

    }
}
