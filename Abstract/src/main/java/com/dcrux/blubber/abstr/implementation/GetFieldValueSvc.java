package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.LiveItemHeader;
import com.dcrux.blubber.abstr.implementables.*;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvGetValue;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.field.FieldDef;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;
import com.dcrux.blubber.iface.field.TypedValue;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.google.inject.Inject;

import java.util.Optional;

/**
 * Created by caelis on 30/05/14.
 */
public class GetFieldValueSvc implements IGetFieldValueSvc {

    @Inject
    private IGetLiveHeaderSvc liveHeaderSvc;
    @Inject
    private IDefinitionByIdSvc definitionByIdSvc;
    @Inject
    private IFieldKeyGenSvc fieldKeyGenSvc;
    @Inject
    private IKvGetValue kvGetValue;
    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IFieldValueObjectGeneratorSvc fieldValueObjectGeneratorSvc;
    @Inject
    private IGetFieldDefSvc fieldDefSvc;
    @Inject
    private IGetSpecialFieldValueSvc getSpecialFieldValueSvc;

    private ICallable<NullableValue> getValue(ItemId itemId, LiveItemHeader liveItemHeader, ItemDef itemDef, FieldNameIndex name) {
        if (name.getName().isReserved()) {
            return getSpecialFieldValueSvc.getValue(itemId, liveItemHeader, name);
        } else {
            return getValueNonSpecial(itemId, liveItemHeader, itemDef, name);
        }
    }

    private ICallable<NullableValue> getValueNonSpecial(ItemId itemId, LiveItemHeader liveItemHeader, ItemDef itemDef, FieldNameIndex name) {
        return executor -> {
            byte[] fieldKey = executor.call(this.fieldKeyGenSvc.generateKey(itemDef, itemId, name));
            byte[] bucket = this.namesSvc.getDataBucket();
            byte[] value = executor.call(this.kvGetValue.getValue(bucket, fieldKey));
            if (value == null) {
                return NullableValue.empty();
            } else {
                final Optional<FieldDef> fieldDef = executor.call(fieldDefSvc.getFieldDef(itemDef, name));
                if (fieldDef.isPresent()) {
                    TypedValue fieldObject = executor.call(fieldValueObjectGeneratorSvc.generateValue(fieldDef.get().getType(), value));
                    return new NullableValue(fieldObject);
                } else {
                    throw new LightException("Field definition not found for given index");
                }
            }

        };
    }

    @Override
    public ICallable<NullableValue> getValue(ItemId itemId, FieldNameIndex name) {
        return executor -> {
            LiveItemHeader itemHeader = executor.call(liveHeaderSvc.getLiveHeaderRequired(itemId));
            ItemDef itemDef = executor.call(definitionByIdSvc.getDefinitionById(itemHeader.getItemDefId()));
            return executor.call(getValue(itemId, itemHeader, itemDef, name));
        };
    }
}
