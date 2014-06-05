package com.dcrux.blubber.common.implementables;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Callable;

/**
 * Created by caelis on 31/05/14.
 */
public interface IState {
    @Nullable
    Object get(Class<?> clazz, Object key);

    /* Sets the value if currently is 'null' (=missing) */
    @Nullable
    Object setGet(Class<?> clazz, Object key, Callable<Object> setter) throws Exception;

    void set(Class<?> clazz, Object key, @Nullable Object value);
}
