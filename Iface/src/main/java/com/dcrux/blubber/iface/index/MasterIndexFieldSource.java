package com.dcrux.blubber.iface.index;

import com.dcrux.blubber.iface.field.FieldName;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Created by caelis on 02/06/14.
 */
public class MasterIndexFieldSource implements Serializable {
    @Nullable
    private FieldName composition;
    @Nullable
    private FieldIndexRange compositionRange;

    private FieldName fieldName;
    private FieldIndexRange range;
}
