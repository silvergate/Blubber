package com.dcrux.blubber.iface.field;

import com.dcrux.blubber.iface.common.ValidationException;
import com.dcrux.blubber.iface.field.constraints.IConstraint;

import java.io.Serializable;

/**
 * Created by caelis on 25/05/14.
 */
public class FieldDef implements Serializable {
    private FieldName name;
    private FieldType type;
    private IConstraint constraint;

    public FieldDef(FieldName name, FieldType type, IConstraint constraint) {
        this.name = name;
        this.type = type;
        this.constraint = constraint;
    }

    private void validate() {
        if (this.constraint.getCompatibleType() != this.type) {
            throw new ValidationException("Wrong constraint type");
        }
    }

    public FieldName getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public IConstraint getConstraint() {
        return constraint;
    }
}
