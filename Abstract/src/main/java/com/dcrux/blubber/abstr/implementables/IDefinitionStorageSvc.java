package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import com.dcrux.blubber.iface.itemdef.ItemDefId;

/**
 * Created by caelis on 25/05/14.
 */
public interface IDefinitionStorageSvc {
    /* Will replace existing (CAN return the same ID as in the last call -> but it's not required to) */
    ICallable<ItemDefId> setDefinition(ItemDefHash hash, ItemDef def);
}
