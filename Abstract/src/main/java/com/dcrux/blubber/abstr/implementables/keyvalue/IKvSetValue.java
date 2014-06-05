package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 23/05/14.
 */
public interface IKvSetValue {
    /* Might or might not remove indexes */
    ICallable<Void> setValue(byte[] bucket, byte[] key, byte[] value, boolean keepIndexes);
}
