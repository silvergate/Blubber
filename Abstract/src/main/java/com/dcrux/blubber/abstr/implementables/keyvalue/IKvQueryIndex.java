package com.dcrux.blubber.abstr.implementables.keyvalue;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 25/05/14.
 */
public interface IKvQueryIndex {
    ICallable<QueryResult> query(byte[] bucket, byte[] indexName, byte[] from, byte[] to, boolean fromIncluded, boolean toIncluded, int limit);
}
