package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 25/05/14.
 */
public interface IUnleafeSvc {
    ICallable<Void> unleafe(ItemId rootItem, ItemId itemId);
}
