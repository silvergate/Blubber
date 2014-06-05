package com.dcrux.blubber.iface.command;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caelis on 16/05/14.
 */
public interface IReplyList<TReply extends Serializable> extends Serializable {
    boolean isAllSuccess();

    List<ISingleReply<TReply>> getReplies();

    /* Will throw if isAllSuccess = true */
    Exception getException();

    /* Throws an exception if getReplies().size!=1 */
    ISingleReply<TReply> getSingle();

    /* Throws an exception if getReplies().size!=1 or isAllSuccess()==false */
    TReply getSingleSuccess();
}
