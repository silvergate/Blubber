package com.dcrux.blubber.iface.common;

/**
 * Created by caelis on 14/05/14.
 */
public interface ICallback<TValue> {
    void success(TValue reply);

    void failure(Exception exception);
}
