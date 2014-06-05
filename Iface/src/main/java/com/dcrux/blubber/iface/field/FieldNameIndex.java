package com.dcrux.blubber.iface.field;

import org.jetbrains.annotations.Nullable;

/**
 * Created by caelis on 21/05/14.
 */
public class FieldNameIndex {
    private final FieldName name;
    private final Short index;

    public static final Short DEFAULT_INDEX = 0;

    /* Pseudo fields */

    public static final FieldNameIndex USER_ID = new FieldNameIndex(new FieldName("_userId"), (short) 0, true);
    public static final FieldNameIndex COMMIT_TIME = new FieldNameIndex(new FieldName("_commitTime"), (short) 0, true);
    public static final FieldNameIndex ITEM_DEF_ID = new FieldNameIndex(new FieldName("_itemDefId"), (short) 0, true);
    public static final FieldNameIndex VERSION = new FieldNameIndex(new FieldName("_version"), (short) 0, true);
    public static final FieldNameIndex PREDECESSOR = new FieldNameIndex(new FieldName("_predecessor"), (short) 0, true);
    public static final FieldNameIndex ROOT = new FieldNameIndex(new FieldName("_root"), (short) 0, true);

    public FieldNameIndex(String name, @Nullable Short index) {
        this(new FieldName(name), index);
    }

    public FieldNameIndex(FieldName name, @Nullable Short index) {
        this(name, index, false);
    }

    private FieldNameIndex(FieldName name, @Nullable Short index, boolean allowReserved) {
        if (!allowReserved && name.isReserved())
            throw new IllegalArgumentException("Field names starting with _ are reserved.");
        this.name = name;
        if (index != null)
            this.index = index;
        else
            this.index = DEFAULT_INDEX;
    }

    public FieldNameIndex(FieldName name) {
        this(name, null);
    }

    public FieldNameIndex(String name) {
        this(new FieldName(name), null);
    }

    public FieldName getName() {
        return name;
    }

    public Short getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldNameIndex that = (FieldNameIndex) o;

        if (!index.equals(that.index)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + index.hashCode();
        return result;
    }
}
