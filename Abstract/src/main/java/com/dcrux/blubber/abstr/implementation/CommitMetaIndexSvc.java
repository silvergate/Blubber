package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.LiveItemHeader;
import com.dcrux.blubber.abstr.implementables.ICommitMetaIndexSvc;
import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvIndexValue;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;
import com.google.inject.Inject;

/**
 * Created by caelis on 25/05/14.
 */
public class CommitMetaIndexSvc implements ICommitMetaIndexSvc {

    @Inject
    private IKvIndexValue kvIndexValue;
    @Inject
    private INamesSvc namesSvc;

    @Override
    public ICallable<Void> commitMetaIndex(ItemId itemId, LiveItemHeader header) {
        return executor -> {
            final byte[] key = itemId.getId();
            final byte[] bucketName = namesSvc.getDataBucket();
            final byte[] leavesIndex = namesSvc.getLeavesIndex();
            final byte[] successorsIndex = namesSvc.getSuccessorsIndex();

            final byte[] leavesValue;
            final byte[] successorValue;
            if (header.getRootItemId() == null) {
                 /* Item itself is the root */
                leavesValue = itemId.getId();
            } else {
                leavesValue = header.getRootItemId().getId();
            }

            if (header.getPredecessor() == null) {
                successorValue = null;
            } else {
                successorValue = itemId.getId();
            }

            executor.call(kvIndexValue.addIndex(bucketName, key, leavesIndex, leavesValue));

            if (successorValue == null) {

            } else {
                executor.call(kvIndexValue.addIndex(bucketName, header.getPredecessor(), successorsIndex, successorValue));
            }
            return null;
        };
    }
}
