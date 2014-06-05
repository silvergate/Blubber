package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import com.dcrux.blubber.iface.itemdef.ItemDefId;

import java.util.Optional;

/**
 * Created by caelis on 23/05/14.
 */
public interface IItemDefIdFromHashSvc {
    /* Fails of not found */
    ICallable<ItemDefId> getItemDefId(ItemDefHash hash);

    /* Returns the ID by hash (optional!) */
    ICallable<Optional<ItemDefId>> getIdByHash(ItemDefHash hash);
}
