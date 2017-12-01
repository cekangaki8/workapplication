package com.dpwn.smartscanus.newopsapi;

import com.google.inject.Inject;
import com.google.inject.Provider;

import retrofit.RestAdapter;

/**
 * Created by cekangaki on 11/30/2017.
 */
public class DeliveryNetworkProvider implements Provider<IDeliveryNetworkApi>{
    @Inject
    NewOpsRestAdapter newOpsRestAdapter;

    @Override
    public IDeliveryNetworkApi get() {
        RestAdapter restAdapter = newOpsRestAdapter.getRestAdapter();
        return restAdapter.create(IDeliveryNetworkApi.class);
    }
}
