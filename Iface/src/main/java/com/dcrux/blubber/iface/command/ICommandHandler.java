package com.dcrux.blubber.iface.command;

import com.dcrux.blubber.iface.common.ICallback;

import java.io.Serializable;

/**
 * Created by caelis on 14/05/14.
 */
public interface ICommandHandler<TState, TReply extends Serializable, TCommand extends ICommand<TReply>> {
    Class<TCommand> getCommandClass();

    void handle(ICommandProcessor self, TState state, TCommand command, ICallback<TReply> callback);
}
