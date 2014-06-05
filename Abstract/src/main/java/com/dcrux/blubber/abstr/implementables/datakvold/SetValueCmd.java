package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.common.VoidReply;

/**
 * Created by caelis on 18/05/14.
 */
public class SetValueCmd implements IKvOperationCommand<VoidReply> {
    private final byte[] value;

    public SetValueCmd(byte[] value) {
        this.value = value;
    }
}
