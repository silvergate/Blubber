package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefId;

/**
 * Created by caelis on 30/05/14.
 */
public interface IDefinitionByIdSvc {
    /* Will fail if not found */
    ICallable<ItemDef> getDefinitionById(ItemDefId id);
}
