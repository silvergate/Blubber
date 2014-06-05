package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;
import com.dcrux.blubber.iface.itemdef.ItemDef;

/**
 * Created by caelis on 29/05/14.
 */
public interface IValidateFieldValueSvc {
    boolean isValid(ItemDef def, FieldNameIndex name, NullableValue value);
}
