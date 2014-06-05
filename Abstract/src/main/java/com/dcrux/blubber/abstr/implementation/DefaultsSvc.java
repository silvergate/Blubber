package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IDefaultsSvc;
import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 30/05/14.
 */
public class DefaultsSvc implements IDefaultsSvc {
    @Override
    public ICallable<Long> getDefaultExpirationPlusMs() {
        /* TODO: Use constants */
        return executor -> (long) 60 * 60 * 1000;
    }
}
