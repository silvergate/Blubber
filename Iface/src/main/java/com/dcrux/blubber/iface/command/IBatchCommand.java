package com.dcrux.blubber.iface.command;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caelis on 16/05/14.
 */
// Use multi command and check if all are the same type
@Deprecated
public interface IBatchCommand<TReply extends Serializable, TSingleCommand extends ICommand<TReply>> extends ICommand<IReplyList<TReply>> {
    Execution getExecution();

    Class<TSingleCommand> getCommandClass();

    List<TSingleCommand> getCommands();

    public enum Execution {
        inOrderStopOnFailure,
        inOrderDoNotStopOnFailure,
        parallel
    }
}
