package com.dcrux.blubber.iface.field.constraints;

/**
 * Created by caelis on 25/05/14.
 */
public abstract class AbstractOptionalConstraint implements IOptionalConstraint {

    private final boolean optional;

    public AbstractOptionalConstraint(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean isOptional() {
        return this.optional;
    }

}
