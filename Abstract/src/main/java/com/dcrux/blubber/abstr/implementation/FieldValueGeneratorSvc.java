package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IBinaryValueSvc;
import com.dcrux.blubber.abstr.implementables.IFieldValueGeneratorSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.field.TypedValue;
import com.google.inject.Inject;

/**
 * Created by caelis on 29/05/14.
 */
public class FieldValueGeneratorSvc implements IFieldValueGeneratorSvc {

    @Inject
    private IBinaryValueSvc binaryValueSvc;

    @Override
    public ICallable<byte[]> generateValue(TypedValue typedValue) {
        switch (typedValue.getType()) {
            case string:
                return binaryValueSvc.fromString(typedValue.getString());
            case integer:
                return binaryValueSvc.fromLong(typedValue.getInteger());
            default:
                //TODO
                throw new IllegalStateException("NOT IMPLEMENTED");
        }
    }
}
