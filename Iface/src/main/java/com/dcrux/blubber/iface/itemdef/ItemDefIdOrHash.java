package com.dcrux.blubber.iface.itemdef;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public class ItemDefIdOrHash implements Serializable {

    private ItemDefHash hash;
    private ItemDefId id;

    private ItemDefIdOrHash(ItemDefHash hash, ItemDefId id) {
        this.hash = hash;
        this.id = id;
    }

    public static ItemDefIdOrHash id(ItemDefId id) {
        return new ItemDefIdOrHash(null, id);
    }

    public static ItemDefIdOrHash hash(ItemDefHash hash) {
        return new ItemDefIdOrHash(hash, null);
    }

    public Type getType() {
        if (this.id != null)
            return Type.id;
        else
            return Type.hash;
    }

    public ItemDefHash getHash() {
        return hash;
    }

    public ItemDefId getId() {
        return id;
    }

    public enum Type {
        id,
        hash
    }
}
