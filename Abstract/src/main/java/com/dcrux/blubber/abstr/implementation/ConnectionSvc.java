package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IConnectionSvc;
import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 30/05/14.
 */
public class ConnectionSvc implements IConnectionSvc {

    private static final String CONNECTION_ID = "connection";


    @Override
    public ICallable<Void> setConnectionId(byte[] connectionId) {
        return executor -> {
            executor.state().set(ConnectionSvc.class, CONNECTION_ID, connectionId);
            return null;
        };
    }

    @Override
    public ICallable<byte[]> getConnectionId() {
        return executor -> {
            byte[] connectionId = (byte[]) executor.state().get(ConnectionSvc.class, CONNECTION_ID);
            if (connectionId == null)
                throw new IllegalStateException("Connection ID has not been set for this connection");
            return connectionId;
        };
    }
}
