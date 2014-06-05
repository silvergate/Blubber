package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 24/05/14.
 */
public interface ICommitSvc {
    ICallable<Void> commit(ItemId itemId);
}
