package com.dcrux.blubber.common.implementables;

/**
 * Created by caelis on 30/05/14.
 */

public interface IConstCallable<T> extends ICallable<T> {
    Object identifier();

    default boolean isCacheFailures() {
        return false;
    }
}
