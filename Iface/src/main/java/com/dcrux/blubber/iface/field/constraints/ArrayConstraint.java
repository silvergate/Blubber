package com.dcrux.blubber.iface.field.constraints;

import com.dcrux.blubber.iface.field.FieldType;

/**
 * Created by caelis on 25/05/14.
 */
public class ArrayConstraint implements IConstraint {

    private IConstraint elementConstraint;
    private int numberOfElements;

    private FieldType getFieldType() {
        return this.elementConstraint.getCompatibleType().getArrayVersion();
    }

    @Override
    public FieldType getCompatibleType() {
        return getFieldType();
    }

    public ArrayConstraint(IConstraint elementConstraint, int numberOfElements) {
        this.elementConstraint = elementConstraint;
        this.numberOfElements = numberOfElements;
    }
}
