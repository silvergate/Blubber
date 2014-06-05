package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.command.IReplyList;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
public class ReadKvEntryCmdReply implements Serializable {
    private final IReplyList<Serializable> operationsReply;

    public ReadKvEntryCmdReply(IReplyList<Serializable> operationsReply) {

        this.operationsReply = operationsReply;
    }

    public IReplyList<Serializable> getOperationsReply() {
        return operationsReply;
    }
}
