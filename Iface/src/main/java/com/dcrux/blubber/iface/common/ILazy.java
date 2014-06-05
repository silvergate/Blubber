package com.dcrux.blubber.iface.common;

import java.io.Serializable;

/**
 * Created by caelis on 21/05/14.
 */
public interface ILazy<TValue extends Serializable> {
    void get(ICallback<TValue> callback);
}
