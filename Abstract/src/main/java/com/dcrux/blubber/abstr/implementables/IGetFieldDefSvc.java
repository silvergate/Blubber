package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldDef;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.itemdef.ItemDef;

import java.util.Optional;

/**
 * Created by caelis on 30/05/14.
 */
public interface IGetFieldDefSvc {
    ICallable<Optional<FieldDef>> getFieldDef(ItemDef itemDef, FieldNameIndex name);
}
