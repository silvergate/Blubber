package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.command.IReplyList;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
public class CreateKvEntryCmdReply implements Serializable {
    private final byte[] key;
    private final IReplyList<Serializable> operationsReply;

    public CreateKvEntryCmdReply(byte[] key, IReplyList<Serializable> operationsReply) {
        this.key = key;
        this.operationsReply = operationsReply;
    }

    public byte[] getKey() {
        return key;
    }

    public IReplyList<Serializable> getOperationsReply() {
        return operationsReply;
    }
}
