package com.dcrux.blubber.iface.field.constraints;

import com.dcrux.blubber.iface.field.FieldType;

/**
 * Created by caelis on 25/05/14.
 */
public class IntegerConstraint extends AbstractOptionalConstraint {

    private final long minValue;
    private final long maxValue;

    public IntegerConstraint(boolean optional, long minValue, long maxValue) {
        super(optional);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public FieldType getCompatibleType() {
        return FieldType.integer;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public long getMinValue() {
        return minValue;
    }
}
