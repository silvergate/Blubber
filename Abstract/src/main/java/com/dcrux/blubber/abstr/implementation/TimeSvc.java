package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.ITimeSvc;

/**
 * Created by caelis on 28/05/14.
 */
public class TimeSvc implements ITimeSvc {
    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
