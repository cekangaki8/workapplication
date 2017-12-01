package com.dpwn.smartscanus.bluetooth;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.squareup.otto.Bus;

/**
 * Created by fshamim on 14.10.14.
 */
public class ConnectionHandlerProvider implements Provider<ConnectionHandler> {
    private static ConnectionHandler connectionHandler;

    @Inject
    private Bus bus;

    @Override
    public ConnectionHandler get() {
        if(connectionHandler == null){
            connectionHandler = new ConnectionHandler(bus);
        }
        return connectionHandler;
    }
}
