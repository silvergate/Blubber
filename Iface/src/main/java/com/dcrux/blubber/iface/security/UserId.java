package com.dcrux.blubber.iface.security;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
//TODO: Rename to UserId
public class UserId implements Serializable {
    private final String id;

    public UserId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
