package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 21/05/14.
 */
public interface IConnectionSvc {
    /**
     * The connection ID makes sure - together with the user ID - that the combination of those two can never occur concurrently.
     *
     * @return
     */
    ICallable<byte[]> getConnectionId();

    ICallable<Void> setConnectionId(byte[] connectionId);
}
