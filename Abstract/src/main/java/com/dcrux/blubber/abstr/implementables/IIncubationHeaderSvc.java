package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 21/05/14.
 */
public interface IIncubationHeaderSvc {
    /* Will fail if header not found */
    ICallable<IncubationItemHeader> getHeader(ItemId itemId);

    ICallable<ItemId> addHeaderAndExpiration(IncubationItemHeader header);

    ICallable<Void> removeExpirationFromIndex(ItemId itemId);
}
