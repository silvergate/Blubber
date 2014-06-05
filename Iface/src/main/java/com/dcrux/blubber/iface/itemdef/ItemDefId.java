package com.dcrux.blubber.iface.itemdef;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by caelis on 13/05/14.
 */
public class ItemDefId implements Serializable {
    private byte[] id;

    public ItemDefId(byte[] id) {
        this.id = id;
    }

    public byte[] getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDefId itemDefId = (ItemDefId) o;

        if (!Arrays.equals(id, itemDefId.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }

    @Override
    public String toString() {
        return "ItemDefId{" +
                "id=" + Arrays.toString(id) +
                '}';
    }
}
