package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 23/05/14.
 */
public interface IKvCreateValue {
    ICallable<byte[]> createValue(byte[] bucket, byte[] value);
}
