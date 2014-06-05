package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;

/**
 * Created by caelis on 18/05/14.
 */
public interface IDefaultsSvc {
    ICallable<Long> getDefaultExpirationPlusMs();
}
