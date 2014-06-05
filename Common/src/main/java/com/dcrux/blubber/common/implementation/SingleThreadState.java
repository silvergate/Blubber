package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.IState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by caelis on 31/05/14.
 */
public class SingleThreadState implements IState {

    private final Map<ClassWithKey, Object> values = new HashMap<>();

    @Nullable
    @Override
    public Object get(Class<?> clazz, Object key) {
        return this.values.get(new ClassWithKey(clazz, key));
    }

    @Nullable
    @Override
    public Object setGet(Class<?> clazz, Object key, Callable<Object> setter) throws Exception {
        final ClassWithKey classWithKey = new ClassWithKey(clazz, key);
        Object existingValue = this.values.get(classWithKey);
        if (existingValue != null) {
            return existingValue;
        } else {
            /* Create a new value */
            final Object newValue = setter.call();
            this.values.put(classWithKey, newValue);
            return newValue;
        }
    }

    @Override
    public void set(Class<?> clazz, Object key, Object value) {
        final ClassWithKey classWithKey = new ClassWithKey(clazz, key);
        this.values.put(classWithKey, value);
    }

    public void reset() {
        this.values.clear();
    }
}
