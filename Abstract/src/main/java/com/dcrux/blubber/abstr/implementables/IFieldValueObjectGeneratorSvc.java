package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldType;
import com.dcrux.blubber.iface.field.TypedValue;

/**
 * Created by caelis on 29/05/14.
 */
public interface IFieldValueObjectGeneratorSvc {
    ICallable<TypedValue> generateValue(FieldType type, byte[] value);
}
