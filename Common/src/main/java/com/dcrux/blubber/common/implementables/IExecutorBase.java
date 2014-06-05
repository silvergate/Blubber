package com.dcrux.blubber.common.implementables;

/**
 * Created by caelis on 30/05/14.
 */
public interface IExecutorBase {
    <T> T call(ICallable<T> callable) throws Exception;

    <T1, T2> IMVal2<T1, T2> call(ICallable<T1> callable1, ICallable<T2> callable2) throws Exception;

    <T1, T2, T3> IMVal3<T1, T2, T3> call(ICallable<T1> callable1, ICallable<T2> callable2, ICallable<T3> callable3) throws Exception;
}
