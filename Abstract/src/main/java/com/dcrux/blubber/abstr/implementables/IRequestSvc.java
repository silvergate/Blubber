package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.iface.common.ICallback;
import com.dcrux.blubber.iface.common.ILazy;

import java.io.Serializable;

/**
 * Created by caelis on 21/05/14.
 */
@Deprecated //TODO: OK?
public interface IRequestSvc {
    <TValue extends Serializable> void get(Object key, ILazy<TValue> value, ICallback<TValue> callback);
}
