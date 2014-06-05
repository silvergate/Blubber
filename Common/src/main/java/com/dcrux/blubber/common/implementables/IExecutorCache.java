package com.dcrux.blubber.common.implementables;

import java.util.concurrent.Future;

/**
 * Created by caelis on 31/05/14.
 */
@Deprecated //Need to completely different interfaces for multithreaded and singlethreaded!
public interface IExecutorCache {
    static interface IExecutorCallback {
        <T> Future<T> call(ICallable<T> callable);
    }

    void assign(IExecutorCallback executorCallback);

    <T> T callCached(ICallable<T> callable) throws Exception;
}
