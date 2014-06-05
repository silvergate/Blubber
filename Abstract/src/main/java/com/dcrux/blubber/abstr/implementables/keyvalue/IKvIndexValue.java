package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 23/05/14.
 */
public interface IKvIndexValue {
    ICallable<Void> addIndex(byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue);
}
