package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.*;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by caelis on 31/05/14.
 */
@Deprecated
public abstract class AbstractExecutor implements IExecutor {

    /* nullable */
    private final IExecutorCache cache;

    protected AbstractExecutor(@Nullable IExecutorCache cache) {
        this.cache = cache;
        if (cache != null) {
            this.cache.assign(new IExecutorCache.IExecutorCallback() {
                @Override
                public <T> Future<T> call(ICallable<T> callable) {
                    return getFuture(callable, true);
                }
            });
        }
    }

    protected abstract <T> Future<T> getFuture(ICallable<T> callable, boolean single);

    protected <T> T waitFor(Future<T> future) throws Exception {
        try {
            return future.get();
        } catch (ExecutionException ee) {
            if (ee.getCause() instanceof Exception) {
                throw (Exception) ee.getCause();
            } else {
                throw new RuntimeException(ee.getCause());
            }
        }
    }

    protected void beforeExecutionOnThisThread() {
    }

    protected <T> T callCached(ICallable<T> callable) throws Exception {
        if (cache != null) {
            return this.cache.callCached(callable);
        } else {
            //return waitFor(getFuture(callable));
            return null;
        }
    }

    public void callVoid(ICallable<Void> callable) throws Exception {
        callCached(callable);
    }

    @Override
    public <T> T call(ICallable<T> callable) throws Exception {
        return callCached(callable);
    }

    @Override
    public <T1, T2> IMVal2<T1, T2> call(ICallable<T1> callable1, ICallable<T2> callable2) throws Exception {

        return combine(callable1, callable2).call(this);
    }

    @Override
    public <T1, T2, T3> IMVal3<T1, T2, T3> call(ICallable<T1> callable1, ICallable<T2> callable2, ICallable<T3> callable3) throws Exception {
        return combine(callable1, callable2, callable3).call(this);
    }

    public <T1, T2> ICallable<IMVal2<T1, T2>> combine(ICallable<T1> callable1, ICallable<T2> callable2) {
        return executor -> {
            Future<T1> f1 = getFuture(callable1, true);
            Future<T2> f2 = getFuture(callable2, false);
            return new MVal2<>(waitFor(f1), waitFor(f2));
        };
    }

    protected <T> Future<T> callableToFuture(ICallable<T> callable) {
        try {
            T value = callable.call(this);
            return new ResolvedFuture<>(value);
        } catch (Throwable e) {
            return new FailedFuture<>(e);
        }
    }


    public <T1, T2, T3> ICallable<IMVal3<T1, T2, T3>> combine(ICallable<T1> callable1, ICallable<T2> callable2, ICallable<T3> callable3) {
        return executor -> {
            return null;
        };
    }


    public void shutdown() {
    }
}
