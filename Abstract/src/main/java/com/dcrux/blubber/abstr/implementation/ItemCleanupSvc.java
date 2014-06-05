package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IItemCleanup;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 29/05/14.
 */
public class ItemCleanupSvc implements IItemCleanup {
    @Override
    public ICallable<Void> extinguishItem(ItemId itemId) {
        throw new IllegalStateException("IMPLEMENT ME");
    }
}
