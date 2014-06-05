package com.dcrux.blubber.iface.command;

import java.io.Serializable;

/**
 * Created by caelis on 16/05/14.
 */
public interface ISingleReply<TReply extends Serializable> {
    boolean isSuccess();

    TReply getSuccess(); //nullable

    Exception getFailure(); //nullable
}
