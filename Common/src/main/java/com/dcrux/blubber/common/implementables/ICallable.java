package com.dcrux.blubber.common.implementables;

/**
 * Created by caelis on 30/05/14.
 */
public interface ICallable<T> {
    T call(IExecutor executor) throws Exception;
}
