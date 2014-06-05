package com.dcrux.blubber.abstr.command;

import com.dcrux.blubber.iface.command.ISingleReply;

import java.io.Serializable;

/**
 * Created by caelis on 17/05/14.
 */
public class SingleReply<TReply extends Serializable> implements ISingleReply<TReply> {

    private final TReply success;
    private final Exception exception;

    private SingleReply(TReply success, Exception exception) {
        this.success = success;
        this.exception = exception;
    }

    public static <TLocalReply extends Serializable> SingleReply<TLocalReply> success(TLocalReply reply) {
        return new SingleReply<TLocalReply>(reply, null);
    }

    public static <TLocalReply extends Serializable> SingleReply<TLocalReply> failure(Exception localException) {
        assert (localException != null);
        return new SingleReply<TLocalReply>(null, localException);
    }

    @Override
    public boolean isSuccess() {
        return this.exception == null;
    }

    @Override
    public TReply getSuccess() {
        return this.success;
    }

    @Override
    public Exception getFailure() {
        return this.exception;
    }
}
