package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.abstr.implementables.*;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvDeleteValue;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvSetValue;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.google.inject.Inject;

/**
 * Created by caelis on 29/05/14.
 */
public class SetFieldValueSvc implements ISetFieldValueSvc {

    @Inject
    private ICheckedIncubationHeader checkedIncubationHeader;
    @Inject
    private IValidateFieldValueSvc validateFieldValueSvc;
    @Inject
    private IFieldKeyGenSvc fieldKeyGenSvc;
    @Inject
    private IFieldValueGeneratorSvc fieldValueGeneratorSvc;
    @Inject
    private IKvDeleteValue kvDeleteValue;
    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IKvSetValue kvSetValue;
    @Inject
    private IDefinitionByIdSvc definitionByIdSvc;

    @Override
    public ICallable<Void> setValue(ItemId itemId, FieldNameIndex name, NullableValue value) {
        return executor -> {
            final IncubationItemHeader header = executor.call(checkedIncubationHeader.getHeaderChecked(itemId));
            final ItemDef reply = executor.call(definitionByIdSvc.getDefinitionById(header.getItemDefId()));
            final boolean isValid = validateFieldValueSvc.isValid(reply, name, value);
            if (isValid) {
                final byte[] key = executor.call(fieldKeyGenSvc.generateKey(reply, itemId, name));
                final byte[] dataBucket = namesSvc.getDataBucket();
                if (value.isNull()) {
                    executor.call(kvDeleteValue.deleteValue(dataBucket, key));
                } else {
                    final byte[] binaryValue = executor.call(fieldValueGeneratorSvc.generateValue(value.getTypedValue()));
                    executor.call(kvSetValue.setValue(dataBucket, key, binaryValue, false));
                }
                return null;
            } else {
                throw new LightException("Invalid field value (invalid type or constraint)");
            }
        };
    }
}
