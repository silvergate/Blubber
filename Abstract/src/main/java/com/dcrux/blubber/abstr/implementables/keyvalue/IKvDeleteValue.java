package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 23/05/14.
 */
public interface IKvDeleteValue {
    /* Will remove indexes too */
    ICallable<Void> deleteValue(byte[] bucket, byte[] key);
}
