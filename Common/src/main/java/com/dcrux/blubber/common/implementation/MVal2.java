package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.IMVal2;

/**
 * Created by caelis on 30/05/14.
 */
public class MVal2<T1, T2> implements IMVal2<T1, T2> {
    private final T1 value1;
    private final T2 value2;

    public MVal2(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public T1 get1() {
        return this.value1;
    }

    @Override
    public T2 get2() {
        return this.value2;
    }
}
