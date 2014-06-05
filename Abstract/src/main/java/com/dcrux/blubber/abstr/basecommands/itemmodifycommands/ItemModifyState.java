package com.dcrux.blubber.abstr.basecommands.itemmodifycommands;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import org.jetbrains.annotations.Nullable;

/**
 * Created by caelis on 21/05/14.
 */
@Deprecated
public class ItemModifyState {
    private final byte[] itemId;
    @Nullable
    private ItemDef itemDef;
    @Nullable
    private IncubationItemHeader incubationItemHeader;

    public ItemModifyState(boolean isIncubationChecked, byte[] itemId, ItemDefId itemDefId, @Nullable ItemDef itemDef) {
        this.itemId = itemId;
        this.itemDef = itemDef;
    }

    public byte[] getItemId() {
        return itemId;
    }

    @Nullable
    public ItemDef getItemDef() {
        return itemDef;
    }

    public void setItemDef(@Nullable ItemDef itemDef) {
        this.itemDef = itemDef;
    }
}
