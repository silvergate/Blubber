package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

import java.util.Optional;

/**
 * Created by caelis on 25/05/14.
 */
public interface IKvRemoveAllIndex {
    ICallable<Void> removeAllIndex(byte[] bucket, byte[] key, Optional<byte[]> indexName);
}
