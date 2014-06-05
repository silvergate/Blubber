package com.dcrux.blubber.iface.field;

import java.io.Serializable;

/**
 * Created by caelis on 21/05/14.
 */
public class FieldName implements Serializable {
    private final String name;

    public FieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isReserved() {
        return getName().startsWith("_");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldName fieldName = (FieldName) o;

        if (!name.equals(fieldName.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
