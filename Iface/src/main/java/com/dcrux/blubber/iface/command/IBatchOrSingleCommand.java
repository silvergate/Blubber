package com.dcrux.blubber.iface.command;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
@Deprecated
public interface IBatchOrSingleCommand<TReply extends Serializable, TSingleCommand extends ICommand<TReply>> {
    Type getType();

    TSingleCommand getSingle();

    IBatchCommand<TReply, TSingleCommand> getMulti();

    public enum Type {
        single,
        multi
    }
}
