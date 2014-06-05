package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;

/**
 * Created by caelis on 25/05/14.
 */
public interface IDefinitionHashGenSvc {
    ICallable<ItemDefHash> getHash(ItemDef itemDef);
}
