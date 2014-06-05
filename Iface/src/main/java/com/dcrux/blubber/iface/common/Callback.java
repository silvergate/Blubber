package com.dcrux.blubber.iface.common;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by caelis on 18/05/14.
 */
public class Callback {

    private static final Logger LOG = Logger.getLogger(Callback.class.getName());

    private Callback() {
    }

    private static final ICallback NULL_CALLBACK = new ICallback() {
        @Override
        public void success(Object reply) {

        }

        @Override
        public void failure(Exception exception) {
            LOG.warning("A failure in a null callback: " + exception);
        }
    };

    public <TValue extends Serializable> void success(final TValue value, ICallback<TValue> callback) {
        callback.success(value);
    }

    public static <TValue extends Serializable> ICallback<TValue> nullCallback() {
        return NULL_CALLBACK;
    }

}
