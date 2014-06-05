package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IBinaryValueSvc;
import com.dcrux.blubber.common.implementables.ICallable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by caelis on 26/05/14.
 */
public class BinaryValueSvc implements IBinaryValueSvc {
    @Override
    public ICallable<byte[]> fromLong(long value) {
        return executor -> {
            ByteBuffer bb = ByteBuffer.allocate(8);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.putLong(value);
            return bb.array();
        };
    }

    @Override
    public ICallable<byte[]> fromShort(short value) {
        return executor -> {
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.putShort(value);
            return bb.array();
        };
    }

    @Override
    public ICallable<byte[]> fromString(String value) {
        return executor -> value.getBytes("UTF-8");
    }

    @Override
    public ICallable<byte[]> concat(byte[]... values) {
        return executor -> {
            int size = 0;
            for (int i = 0; i < values.length; i++)
                size += values[i].length;
            ByteBuffer bb = ByteBuffer.allocate(size);
            for (int i = 0; i < values.length; i++)
                bb.put(values[i]);
            return bb.array();
        };
    }

    @Override
    public ICallable<Long> toLong(byte[] value) {
        return executor -> {
            ByteBuffer bb = ByteBuffer.wrap(value);
            bb.order(ByteOrder.BIG_ENDIAN);
            return bb.getLong();
        };
    }

    @Override
    public ICallable<String> toString(byte[] value) {
        return executor -> new String(value, "UTF-8");
    }
}
