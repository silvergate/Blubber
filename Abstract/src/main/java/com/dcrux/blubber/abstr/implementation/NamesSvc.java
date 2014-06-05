package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.INamesSvc;

/**
 * Created by caelis on 29/05/14.
 */
public class NamesSvc implements INamesSvc {
    @Override
    public byte[] getDataBucket() {
        return new byte[]{0};
    }

    @Override
    public byte[] getDefinitionBucket() {
        return new byte[]{1};
    }

    @Override
    public byte[] getExpirationIndex() {
        return new byte[]{1};
    }

    @Override
    public byte[] getLeavesIndex() {
        return new byte[]{2};
    }

    @Override
    public byte[] getSuccessorsIndex() {
        return new byte[]{3};
    }

    @Override
    public byte[] getDefinitionHashIndex() {
        return new byte[]{4};
    }
}
