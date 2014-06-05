package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IValidateFieldValueSvc;
import com.dcrux.blubber.iface.field.FieldDef;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;
import com.dcrux.blubber.iface.itemdef.ItemDef;

import java.util.logging.Logger;

/**
 * Created by caelis on 29/05/14.
 */
public class ValidateFieldValueSvc implements IValidateFieldValueSvc {

    private static final Logger LOG = Logger.getLogger(ValidateFieldValueSvc.class.getName());

    @Override
    public boolean isValid(ItemDef def, FieldNameIndex name, NullableValue value) {
        final Short fieldDefShort = def.getFields().getIndexByName(name.getName());
        if (fieldDefShort == null) {
            LOG.info("Field not found");
            return false;
        }
        final FieldDef fieldDef = def.getFields().getFieldDef(fieldDefShort);
        if (fieldDef == null) {
            LOG.info("Field not found");
            return false;
        }

        if (value.getTypedValue() != null && fieldDef.getType() != value.getTypedValue().getType()) {
            LOG.info("Wrong type");
            return false;
        }

        LOG.warning("TODO: Check constraints");

        return true;
    }
}
