package com.dcrux.blubber.abstr.implementables.datakvold;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Created by caelis on 21/05/14.
 */
public class GetValueCmdReply implements Serializable {
    @Nullable
    private byte[] value;

    public GetValueCmdReply(@Nullable byte[] value) {
        this.value = value;
    }

    @Nullable
    public byte[] getValue() {
        return value;
    }
}
