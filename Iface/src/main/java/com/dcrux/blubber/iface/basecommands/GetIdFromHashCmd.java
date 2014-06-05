package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import com.dcrux.blubber.iface.itemdef.ItemDefId;

/**
 * Created by caelis on 17/05/14.
 */
public class GetIdFromHashCmd implements IBaseCommand<ItemDefId> {
    private final ItemDefHash itemDefHash;

    public GetIdFromHashCmd(ItemDefHash itemDefHash) {
        this.itemDefHash = itemDefHash;
    }
}
