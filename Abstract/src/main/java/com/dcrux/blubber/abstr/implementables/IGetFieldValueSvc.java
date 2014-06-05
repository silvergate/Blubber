package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 29/05/14.
 */
public interface IGetFieldValueSvc {
    /* Throws if itemid not found. Has to be committed */
    ICallable<NullableValue> getValue(ItemId itemId, FieldNameIndex name);
}
