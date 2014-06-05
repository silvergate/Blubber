package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 23/05/14.
 */
public interface IKvRemoveIndex {
    /* Removes a single index */
    ICallable<Void> removeIndex(byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue);
}
