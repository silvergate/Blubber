package com.dcrux.blubber.iface.field.constraints;

import com.dcrux.blubber.iface.field.FieldType;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;

/**
 * Created by caelis on 02/06/14.
 */
public class CompositionConstraint extends AbstractOptionalConstraint {

    private final ItemDefHash itemDefHash;

    public CompositionConstraint(boolean optional, ItemDefHash itemDefHash) {
        super(optional);
        this.itemDefHash = itemDefHash;
    }

    public ItemDefHash getItemDefHash() {
        return itemDefHash;
    }

    @Override
    public FieldType getCompatibleType() {
        return FieldType.composition;
    }
}
