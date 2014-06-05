package com.dcrux.blubber.common.implementation;

/**
 * Created by caelis on 31/05/14.
 */
public class ClassWithKey {
    private final Class<?> clazz;
    private final Object key;

    public ClassWithKey(Class<?> clazz, Object key) {
        this.clazz = clazz;
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassWithKey that = (ClassWithKey) o;

        if (!clazz.equals(that.clazz)) return false;
        if (!key.equals(that.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clazz.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }
}
