package com.dcrux.blubber.common.implementation;

import com.dcrux.blubber.common.implementables.IMVal3;

/**
 * Created by caelis on 30/05/14.
 */
public class MVal3<T1, T2, T3> extends MVal2<T1, T2> implements IMVal3<T1, T2, T3> {
    private final T3 value3;

    public MVal3(T1 value1, T2 value2, T3 value3) {
        super(value1, value2);
        this.value3 = value3;
    }

    @Override
    public T3 get3() {
        return this.value3;
    }
}
