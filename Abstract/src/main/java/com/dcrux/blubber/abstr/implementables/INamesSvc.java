package com.dcrux.blubber.abstr.implementables;

/**
 * Created by caelis on 18/05/14.
 */
public interface INamesSvc {
    byte[] getDataBucket();

    byte[] getDefinitionBucket();

    byte[] getExpirationIndex();

    byte[] getLeavesIndex();

    byte[] getSuccessorsIndex();

    byte[] getDefinitionHashIndex();
}
