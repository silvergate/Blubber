package com.dcrux.blubber.iface.item;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by caelis on 13/05/14.
 */
public class ItemId implements Serializable {
    private byte[] id;

    public ItemId(byte[] id) {
        assert (id != null);
        this.id = id;
    }

    public byte[] getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemId itemId = (ItemId) o;

        if (!Arrays.equals(id, itemId.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }

    @Override
    public String toString() {
        return "ItemId{" +
                "id=" + Arrays.toString(id) +
                '}';
    }
}
