package com.dpwn.smartscanus.newopsapi;

import com.google.inject.Inject;
import com.google.inject.Provider;

import retrofit.RestAdapter;

/**
 * Created by cekangak on 7/30/2015.
 */
public class SortingServiceProvider implements Provider<ISortingServiceApi> {
    @Inject
    NewOpsRestAdapter newOpsRestAdapter;

    @Override
    public ISortingServiceApi get() {
        RestAdapter restAdapter = newOpsRestAdapter.getRestAdapter();
        return restAdapter.create(ISortingServiceApi.class);
    }


}
