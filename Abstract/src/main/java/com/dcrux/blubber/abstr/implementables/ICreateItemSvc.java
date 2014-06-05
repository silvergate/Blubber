package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDefIdOrHash;
import com.dcrux.blubber.iface.security.DomainId;

/**
 * Created by caelis on 23/05/14.
 */
public interface ICreateItemSvc {
    ICallable<ItemId> createItem(ItemDefIdOrHash itemDefId, DomainId domainId);
}
