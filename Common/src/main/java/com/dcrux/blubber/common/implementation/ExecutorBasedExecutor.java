package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.*;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by caelis on 30/05/14.
 */
public class ExecutorBasedExecutor implements IExecutor {

    @Nullable
    private ForkJoinPool executorService;

    public ExecutorBasedExecutor(@Nullable IExecutorCache cache) {
        this.executorService = new ForkJoinPool();
    }


    private IExecutorCache cache;

    @Override
    public <T> T call(ICallable<T> callable) throws Exception {
        RecursiveTask2 task = new RecursiveTask2(new ICallable[]{callable});
        Object[] value = this.executorService.invoke(task);
        return task.toValue(value);
    }

    @Override
    public <T1, T2> IMVal2<T1, T2> call(ICallable<T1> callable1, ICallable<T2> callable2) throws Exception {
        RecursiveTask2 task = new RecursiveTask2(new ICallable[]{callable1, callable2});
        Object[] value = this.executorService.invoke(task);
        return task.toValues2(value);
    }

    @Override
    public <T1, T2, T3> IMVal3<T1, T2, T3> call(ICallable<T1> callable1, ICallable<T2> callable2, ICallable<T3> callable3) throws Exception {
        RecursiveTask2 task = new RecursiveTask2(new ICallable[]{callable1, callable2, callable3});
        Object[] value = this.executorService.invoke(task);
        return task.toValues3(value);
    }

    @Override
    public IState state() {
        return null;
    }

    private static class ThrowableMarker {
        Throwable throwable;

        public ThrowableMarker(Throwable throwable) {
            this.throwable = throwable;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }

    private Object evaluate(ICallable callable) {
        try {
            return callable.call(this);
        } catch (Throwable t) {
            return new ThrowableMarker(t);
        }
    }

    public class RecursiveTask2 extends RecursiveTask<Object[]> {

        public RecursiveTask2(ICallable[] callables) {
            this(callables, 0, callables.length - 1);
        }

        public RecursiveTask2(ICallable[] callables, int sliceBeginInclusive, int sliceEndInclusive) {
            this.callables = callables;
            this.sliceBeginInclusive = sliceBeginInclusive;
            this.sliceEndInclusive = sliceEndInclusive;
        }

        private ICallable[] callables;
        private int sliceBeginInclusive;
        private int sliceEndInclusive;

        @Override
        protected Object[] compute() {
            Object[] results = new Object[sliceEndInclusive - sliceBeginInclusive + 1];
            if (sliceBeginInclusive != sliceEndInclusive) {
                RecursiveTask2 right = new RecursiveTask2(this.callables, sliceBeginInclusive + 1, sliceEndInclusive);
                right.fork();
                results[0] = evaluate(this.callables[sliceBeginInclusive]);
                Object[] rightResult = right.join();
                System.arraycopy(rightResult, 0, results, 1, rightResult.length);
            } else {
                results[0] = evaluate(this.callables[sliceBeginInclusive]);
            }
            return results;
        }

        private <T> T toSingleValue(Object value) throws Exception {
            if (value instanceof ThrowableMarker) {
                ThrowableMarker throwableMarker = (ThrowableMarker) value;
                if (throwableMarker.getThrowable() instanceof Exception)
                    throw (Exception) throwableMarker.getThrowable();
                else
                    throw new RuntimeException(throwableMarker.getThrowable());
            } else {
                return (T) value;
            }
        }

        public <T> T toValue(Object[] values) throws Exception {
            return toSingleValue(values[0]);
        }

        public <T1, T2> IMVal2<T1, T2> toValues2(Object[] values) throws Exception {
            MVal2<T1, T2> mval = new MVal2<>(this.<T1>toSingleValue(values[0]), this.<T2>toSingleValue(values[1]));
            return mval;
        }

        public <T1, T2, T3> IMVal3<T1, T2, T3> toValues3(Object[] values) throws Exception {
            MVal3<T1, T2, T3> mval = new MVal3<>(this.<T1>toSingleValue(values[0]), this.<T2>toSingleValue(values[1]), this.<T3>toSingleValue(values[2]));
            return mval;
        }
    }
}
