package com.dcrux.blubber.iface.command;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
@Deprecated
public class BatchOrSingleCommand<TReply extends Serializable, TSingleCommand extends ICommand<TReply>> implements IBatchOrSingleCommand<TReply, TSingleCommand> {

    private final TSingleCommand single;
    private final IBatchCommand<TReply, TSingleCommand> multi;

    private BatchOrSingleCommand(TSingleCommand single, IBatchCommand<TReply, TSingleCommand> multi) {
        assert (single != null || multi != null);
        assert (single == null || multi == null);
        this.single = single;
        this.multi = multi;
    }

    public static <TReply extends Serializable, TSingleCommand extends ICommand<TReply>> BatchOrSingleCommand<TReply, TSingleCommand> single(TSingleCommand single) {
        assert (single != null);
        return new BatchOrSingleCommand<>(single, null);
    }

    public static <TReply extends Serializable, TSingleCommand extends ICommand<TReply>> BatchOrSingleCommand<TReply, TSingleCommand> multi(IBatchCommand<TReply, TSingleCommand> multi) {
        assert (multi != null);
        return new BatchOrSingleCommand<>(null, multi);
    }

    @Override
    public Type getType() {
        return this.multi == null ? Type.single : Type.multi;
    }

    @Override
    public TSingleCommand getSingle() {
        return this.single;
    }

    @Override
    public IBatchCommand<TReply, TSingleCommand> getMulti() {
        return this.multi;
    }
}
