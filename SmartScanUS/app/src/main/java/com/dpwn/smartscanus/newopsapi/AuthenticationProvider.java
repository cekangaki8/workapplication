package com.dpwn.smartscanus.newopsapi;

import com.google.inject.Inject;
import com.google.inject.Provider;

import retrofit.RestAdapter;

/**
 * Created by cekangak on 7/30/2015.
 */
public class AuthenticationProvider implements Provider<IAuthenticationApi> {

    @Inject
    NewOpsRestAdapter newOpsRestAdapter;

    @Override
    public IAuthenticationApi get() {
        RestAdapter restAdapter = newOpsRestAdapter.getRestAdapter();
        return restAdapter.create(IAuthenticationApi.class);
    }

}
