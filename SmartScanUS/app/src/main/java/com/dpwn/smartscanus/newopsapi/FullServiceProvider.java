package com.dpwn.smartscanus.newopsapi;

import com.google.inject.Inject;
import com.google.inject.Provider;

import retrofit.RestAdapter;

/**
 * Created by cekangak on 7/30/2015.
 */
public class FullServiceProvider implements Provider<IFullServiceApi> {
    @Inject
    NewOpsRestAdapter newOpsRestAdapter;

    @Override
    public IFullServiceApi get() {
        RestAdapter restAdapter = newOpsRestAdapter.getRestAdapter();
        return restAdapter.create(IFullServiceApi.class);
    }


}
