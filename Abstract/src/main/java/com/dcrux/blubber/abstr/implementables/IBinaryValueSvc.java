package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 18/05/14.
 */
public interface IBinaryValueSvc {
    ICallable<byte[]> fromLong(long value);

    ICallable<byte[]> fromShort(short value);

    ICallable<byte[]> fromString(String value);

    ICallable<byte[]> concat(byte[]... values);

    ICallable<Long> toLong(byte[] value);

    ICallable<String> toString(byte[] value);
}
