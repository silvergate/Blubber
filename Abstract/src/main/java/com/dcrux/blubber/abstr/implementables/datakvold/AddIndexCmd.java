package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.common.VoidReply;

/**
 * Created by caelis on 18/05/14.
 */
public class AddIndexCmd implements IKvOperationCommand<VoidReply> {
    private final byte[] index;
    private final byte[] value;

    public AddIndexCmd(byte[] index, byte[] value) {
        this.index = index;
        this.value = value;
    }

    public byte[] getIndex() {
        return index;
    }

    public byte[] getValue() {
        return value;
    }
}
