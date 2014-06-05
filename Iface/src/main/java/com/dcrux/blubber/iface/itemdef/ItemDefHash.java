package com.dcrux.blubber.iface.itemdef;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public class ItemDefHash implements Serializable {
    private byte[] hash;

    public ItemDefHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getHash() {
        return hash;
    }
}
