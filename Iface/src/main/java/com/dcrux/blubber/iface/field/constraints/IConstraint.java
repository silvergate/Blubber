package com.dcrux.blubber.iface.field.constraints;

import com.dcrux.blubber.iface.field.FieldType;

import java.io.Serializable;

/**
 * Created by caelis on 25/05/14.
 */
public interface IConstraint extends Serializable {
    FieldType getCompatibleType();
}
