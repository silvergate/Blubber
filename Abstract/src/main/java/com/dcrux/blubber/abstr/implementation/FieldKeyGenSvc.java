package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IBinaryValueSvc;
import com.dcrux.blubber.abstr.implementables.IFieldKeyGenSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.google.inject.Inject;

/**
 * Created by caelis on 29/05/14.
 */
public class FieldKeyGenSvc implements IFieldKeyGenSvc {

    @Inject
    private IBinaryValueSvc binaryValueSvc;

    @Override
    public ICallable<byte[]> generateKey(ItemDef itemDef, ItemId itemId, FieldNameIndex name) {
        return executor -> {
            byte[] indexBinary;
            indexBinary = executor.call(this.binaryValueSvc.fromShort(name.getIndex()));
            Short nameIndex = itemDef.getFields().getIndexByName(name.getName());
            if (nameIndex == null)
                throw new IllegalArgumentException("Field named " + name.getName() + " was not found in item definition.");

            return executor.call(this.binaryValueSvc.concat(itemId.getId(), indexBinary, executor.call(this.binaryValueSvc.fromShort(nameIndex))));
        };
    }
}
