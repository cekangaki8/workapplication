package com.dpwn.smartscanus.newopsapi;

import android.content.SharedPreferences;

import com.dpwn.smartscanus.utils.PrefsConsts;
import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import com.squareup.okhttp.OkHttpClient;

import org.apache.commons.lang3.StringUtils;

import retrofit.Endpoint;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by cekangak on 7/30/2015.
 */
public class NewOpsRestAdapter {
    private String serverName;
    private String serverApp;
    private String serverPort;
    private String serverNames[];
    private boolean sslOn;
    private int currentIndex;

    @Inject
    private OkHttpClient okHttpClient;

    @Inject
    private SharedPreferences prefs;


    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public void setServerApp(String serverApp) {
        this.serverApp = serverApp;
    }


    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }


    public void setSSLIsOn(boolean sslOn) {
        this.sslOn = sslOn;
    }

    public void setServerNames(String[] serverNames) {
        this.serverNames = serverNames;
        if (serverNames.length >= 1) {
            currentIndex = 0;
            setServerName(serverNames[0]);
        }
    }

    public boolean isLastServerName() {
        return currentIndex == this.serverNames.length - 1;
    }

    public void setNextServerName() {
        currentIndex++;
        setServerName(serverNames[currentIndex]);
    }

    public RestAdapter getRestAdapter() {
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setRequestInterceptor( new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                        if (StringUtils.isNotBlank(prefs.getString(PrefsConsts.JSESSIONID, ""))) {
                            request.addHeader("COOKIE", String.format("JSESSIONID=%s", prefs.getString(PrefsConsts.JSESSIONID, "")));
                        }
                    }
                })
                .setEndpoint(new Endpoint() {
                    @Override
                    public String getUrl() {
                        String http;
                        if(sslOn){
                            http = "https://";
                        }else{
                            http = "http://";
                        }
                        return serverName + ":" + serverPort + "/" + serverApp;
                    }

                    @Override
                    public String getName() {
                        return serverName;
                    }
                })
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter;

    }

}
