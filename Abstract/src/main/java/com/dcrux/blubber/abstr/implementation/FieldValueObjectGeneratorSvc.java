package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IBinaryValueSvc;
import com.dcrux.blubber.abstr.implementables.IFieldValueObjectGeneratorSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.FieldType;
import com.dcrux.blubber.iface.field.TypedValue;
import com.google.inject.Inject;

/**
 * Created by caelis on 31/05/14.
 */
public class FieldValueObjectGeneratorSvc implements IFieldValueObjectGeneratorSvc {

    @Inject
    private IBinaryValueSvc binaryValueSvc;

    @Override
    public ICallable<TypedValue> generateValue(FieldType type, byte[] value) {
        return executor -> {
            switch (type) {
                case integer:
                    return TypedValue.integer(executor.call(binaryValueSvc.toLong(value)));
                case string:
                    return TypedValue.string(executor.call(binaryValueSvc.toString(value)));
                default:
                    throw new IllegalStateException("NOT IMPLEMENTED");
            }
        };
    }
}
