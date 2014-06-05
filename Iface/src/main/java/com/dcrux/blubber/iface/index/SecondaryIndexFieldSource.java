package com.dcrux.blubber.iface.index;

import com.dcrux.blubber.iface.field.FieldNameIndex;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Created by caelis on 02/06/14.
 */
public class SecondaryIndexFieldSource implements Serializable {
    @Nullable
    private FieldNameIndex composition;

    private FieldNameIndex fieldName;

    public SecondaryIndexFieldSource(@Nullable FieldNameIndex composition, FieldNameIndex fieldName) {
        this.composition = composition;
        this.fieldName = fieldName;
    }

    @Nullable
    public FieldNameIndex getComposition() {
        return composition;
    }

    public FieldNameIndex getFieldName() {
        return fieldName;
    }
}
