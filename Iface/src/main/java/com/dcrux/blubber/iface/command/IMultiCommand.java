package com.dcrux.blubber.iface.command;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caelis on 18/05/14.
 */
public interface IMultiCommand<TSingleCommand extends ICommand> extends ICommand<IReplyList<Serializable>> {
    Execution getExecution();

    List<TSingleCommand> getCommands();

    public enum Execution {
        inOrderStopOnFailure,
        inOrderDoNotStopOnFailure,
        parallel
    }
}
