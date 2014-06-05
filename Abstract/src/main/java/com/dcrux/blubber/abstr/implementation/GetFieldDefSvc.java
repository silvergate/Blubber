package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IGetFieldDefSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldDef;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Created by caelis on 30/05/14.
 */
public class GetFieldDefSvc implements IGetFieldDefSvc {
    @Nullable
    @Override
    public ICallable<Optional<FieldDef>> getFieldDef(ItemDef itemDef, FieldNameIndex name) {
        return executor -> {
            Short nameIndex = itemDef.getFields().getIndexByName(name.getName());
            if (nameIndex == null)
                return Optional.empty();
            return Optional.of(itemDef.getFields().getFieldDef(nameIndex));
        };
    }
}
