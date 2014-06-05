package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.item.ItemVersion;

/**
 * Created by caelis on 13/05/14.
 */
public class UpdateItemCmd implements ICreateUpdateItem, IBaseCommand<ItemId> {
    private ItemId itemId;
    private ItemVersion version;
}
