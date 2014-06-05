package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDef;

/**
 * Created by caelis on 29/05/14.
 */
public interface IFieldKeyGenSvc {
    ICallable<byte[]> generateKey(ItemDef itemDef, ItemId itemId, FieldNameIndex name);
}
