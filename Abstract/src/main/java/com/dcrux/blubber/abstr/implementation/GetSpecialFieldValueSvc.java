package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.LiveItemHeader;
import com.dcrux.blubber.abstr.implementables.IGetSpecialFieldValueSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;
import com.dcrux.blubber.iface.field.TypedValue;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 02/06/14.
 */
public class GetSpecialFieldValueSvc implements IGetSpecialFieldValueSvc {
    @Override
    public ICallable<NullableValue> getValue(ItemId itemId, LiveItemHeader liveItemHeader, FieldNameIndex name) {
        return executor -> {
            if (name.equals(FieldNameIndex.ITEM_DEF_ID)) {
                return new NullableValue(TypedValue.binary(liveItemHeader.getItemDefId().getId()));
            } else if (name.equals(FieldNameIndex.USER_ID)) {
                return new NullableValue(TypedValue.string(liveItemHeader.getUserId()));
            }
            throw new IllegalStateException("NOT IMPLEMENTED. IMPLEMENT OTHER SPECIAL FIELDS");
        };
    }
}
