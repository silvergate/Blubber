package com.dcrux.blubber.iface.field;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;

/**
 * Created by caelis on 19/05/14.
 */
public class NullableValue implements Serializable {

    private static final NullableValue NULL = new NullableValue(null);

    private final TypedValue typedValue;

    public NullableValue(@Nullable TypedValue typedValue) {
        this.typedValue = typedValue;
    }

    public static NullableValue empty() {
        return NULL;
    }

    @Nullable
    public TypedValue getTypedValue() {
        return typedValue;
    }

    public boolean isNull() {
        return this.typedValue == null;
    }
}
