package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.*;

/**
 * Created by caelis on 30/05/14.
 */
public class SingleThreadExecutor implements IExternalExecutor {

    private SingleThreadState state = new SingleThreadState();

    private IExecutor executor = new IExecutor() {
        @Override
        public IState state() {
            return SingleThreadExecutor.this.state;
        }

        @Override
        public <T> T call(ICallable<T> callable) throws Exception {
            return callWithoutReset(callable);
        }

        @Override
        public <T1, T2> IMVal2<T1, T2> call(ICallable<T1> callable1, ICallable<T2> callable2) throws Exception {
            return new MVal2<>(callWithoutReset(callable1), callWithoutReset(callable2));
        }

        @Override
        public <T1, T2, T3> IMVal3<T1, T2, T3> call(ICallable<T1> callable1, ICallable<T2> callable2, ICallable<T3> callable3) throws Exception {
            return new MVal3<>(callWithoutReset(callable1), callWithoutReset(callable2), callWithoutReset(callable3));
        }
    };

    private void resetState() {
        this.state.reset();
    }

    private <T> T callWithoutReset(ICallable<T> callable) throws Exception {
        return callable.call(this.executor);
    }

    @Override
    public <T> T call(ICallable<T> callable) throws Exception {
        resetState();
        return this.executor.call(callable);
    }

    @Override
    public <T1, T2> IMVal2<T1, T2> call(ICallable<T1> callable1, ICallable<T2> callable2) throws Exception {
        resetState();
        return this.executor.call(callable1, callable2);
    }

    @Override
    public <T1, T2, T3> IMVal3<T1, T2, T3> call(ICallable<T1> callable1, ICallable<T2> callable2, ICallable<T3> callable3) throws Exception {
        resetState();
        return this.executor.call(callable1, callable2, callable3);
    }

    @Override
    public void shutdown() {
    }
}
