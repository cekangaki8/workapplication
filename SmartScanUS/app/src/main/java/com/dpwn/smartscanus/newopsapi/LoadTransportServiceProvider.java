package com.dpwn.smartscanus.newopsapi;

import com.google.inject.Inject;
import com.google.inject.Provider;

import retrofit.RestAdapter;

/**
 * Created by cekangaki on 9/25/2015.
 */
public class LoadTransportServiceProvider implements Provider<ILoadTransportApi>{
    @Inject
    NewOpsRestAdapter newOpsRestAdapter;

    @Override
    public ILoadTransportApi get() {
        RestAdapter restAdapter = newOpsRestAdapter.getRestAdapter();
        return restAdapter.create(ILoadTransportApi.class);
    }
}
