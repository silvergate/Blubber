package com.dcrux.blubber.iface.security;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
public class DomainId implements Serializable {

    public static final DomainId DEFAULT = new DomainId(null);

    private final byte[] id;

    public DomainId(byte[] id) {
        /*assert(id!=null);
        assert(id.length>0);*/
        assert (id.length <= 64);
        this.id = id;
    }

    @Nullable
    public byte[] getId() {
        return id;
    }
}
