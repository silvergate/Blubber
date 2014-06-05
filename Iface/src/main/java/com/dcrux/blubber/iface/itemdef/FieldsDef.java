package com.dcrux.blubber.iface.itemdef;

import com.dcrux.blubber.iface.field.FieldDef;
import com.dcrux.blubber.iface.field.FieldName;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caelis on 25/05/14.
 */
public class FieldsDef implements Serializable {
    private final Map<Short, FieldDef> fieldsDef = new HashMap<>();
    private final Map<String, Short> nameToIndex = new HashMap<>();

    public void addFieldDef(short index, FieldDef def) {
        final FieldDef oldFieldDef = this.fieldsDef.put(index, def);
        final Short oldEntry = this.nameToIndex.put(def.getName().getName(), index);
        if (oldEntry != null || oldFieldDef != null)
            throw new IllegalArgumentException("Entry (index or name) already existent.");
    }

    @Nullable
    public FieldDef getFieldDef(short index) {
        return this.fieldsDef.get(index);
    }

    @Nullable
    public Short getIndexByName(FieldName fieldName) {
        return this.nameToIndex.get(fieldName.getName());
    }
}
