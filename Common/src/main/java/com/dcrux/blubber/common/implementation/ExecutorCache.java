package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.common.implementables.IConstCallable;
import com.dcrux.blubber.common.implementables.IExecutorCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by caelis on 31/05/14.
 */
public class ExecutorCache implements IExecutorCache {

    private IExecutorCallback executorCallback;
    private final ConcurrentMap<ClassIdentifierIdentifier, Future<Object>> evaluatedResults = new ConcurrentHashMap<>();

    @Override
    public void assign(IExecutorCallback executorCallback) {
        if (this.executorCallback != null)
            throw new IllegalStateException("This cache has already been assigned to a executor.");
        this.executorCallback = executorCallback;
    }

    public <T> T callCached(ICallable<T> callable) throws ExecutionException, InterruptedException {
        if (callable instanceof IConstCallable) {
            IConstCallable constCallable = (IConstCallable) callable;
            /* Note: Implement some cache rules, e.g. execution time... Number of times executed, ... */
            final ClassIdentifierIdentifier identifierIdentifier = new ClassIdentifierIdentifier(constCallable.getClass(), constCallable.identifier());
            boolean[] didEvaluate = new boolean[]{false};
            Future<Object> value = evaluatedResults.computeIfAbsent(identifierIdentifier, classIdentifierIdentifier -> {
                didEvaluate[1] = true;
                return executorCallback.call(constCallable);
            });
            final boolean storeException = constCallable.isCacheFailures();
            try {
                Object evaluatedValue = value.get();
                return (T) evaluatedValue;
            } catch (ExecutionException | InterruptedException ee) {
                /* Got an exception in cache. This might be ok... */
                if (storeException) {
                    /* Yes, ok */
                    throw ee;
                } else {
                    if (didEvaluate[1]) {
                     /* This is not OK, remove the value from the cache and throw... */
                        evaluatedResults.remove(identifierIdentifier);
                        throw ee;
                    } else {
                        /* This is not OK, remove the value from the cache again and re-run (someone else wrote that result into the cache)... */
                        evaluatedResults.remove(identifierIdentifier);
                        return callCached(callable);
                    }
                }
            }
        } else {
            /* Cannot cache */
            return executorCallback.call(callable).get();
        }
    }

    public static final class ClassIdentifierIdentifier {
        private final Class<?> callable;
        private final Object identifier;

        public ClassIdentifierIdentifier(Class<?> callable, Object identifier) {
            this.callable = callable;
            this.identifier = identifier;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassIdentifierIdentifier that = (ClassIdentifierIdentifier) o;

            if (!callable.equals(that.callable)) return false;
            if (!identifier.equals(that.identifier)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = callable.hashCode();
            result = 31 * result + identifier.hashCode();
            return result;
        }
    }
}
