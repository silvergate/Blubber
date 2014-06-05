package com.dcrux.blubber.iface.command;

import com.dcrux.blubber.iface.common.ICallback;

import java.io.Serializable;

/**
 * Created by caelis on 16/05/14.
 */
@Deprecated
public interface IBatchCommandHandler<TState, TReply extends Serializable, TCommand extends ICommand<TReply>> {
    Class<TCommand> getCommandClass();

    void handle(TState state, IBatchCommand<TReply, TCommand> command, ICallback<IReplyList<TReply>> callback);
}
