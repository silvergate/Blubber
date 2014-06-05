package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.IUnleafeSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvRemoveIndex;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;
import com.google.inject.Inject;

/**
 * Created by caelis on 29/05/14.
 */
public class UnleafeSvc implements IUnleafeSvc {

    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IKvRemoveIndex kvRemoveIndex;

    @Override
    public ICallable<Void> unleafe(ItemId rootItem, ItemId itemId) {
        return executor -> {
            final byte[] leavesIndex = namesSvc.getLeavesIndex();
            final byte[] bucket = namesSvc.getDataBucket();
            executor.call(kvRemoveIndex.removeIndex(bucket, itemId.getId(), leavesIndex, rootItem.getId()));
            return null;
        };
    }
}
