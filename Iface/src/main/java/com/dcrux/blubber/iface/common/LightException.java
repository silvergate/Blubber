package com.dcrux.blubber.iface.common;

/**
 * Created by caelis on 24/05/14.
 */
public class LightException extends Exception {
    public LightException() {
        super("", null, true, false);
    }

    public LightException(String message) {
        super(message, null, true, false);
    }

    public LightException(Throwable t) {
        super(t);
    }
}
