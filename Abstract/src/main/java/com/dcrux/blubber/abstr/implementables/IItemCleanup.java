package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 24/05/14.
 */
public interface IItemCleanup {
    /* A fatal error occured, remove the entire item (all values including the indexes) ... */
    ICallable<Void> extinguishItem(ItemId itemId);
}
