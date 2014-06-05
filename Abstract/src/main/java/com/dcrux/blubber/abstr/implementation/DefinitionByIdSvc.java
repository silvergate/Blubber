package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IDefinitionByIdSvc;
import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvGetValue;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import com.google.inject.Inject;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Created by caelis on 30/05/14.
 */
public class DefinitionByIdSvc implements IDefinitionByIdSvc {

    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IKvGetValue kvGetValue;

    @Override
    public ICallable<ItemDef> getDefinitionById(ItemDefId id) {
        return executor -> {
            byte[] bucket = namesSvc.getDefinitionBucket();
            byte[] value = executor.call(kvGetValue.getValue(bucket, id.getId()));

            if (value == null) {
                throw new LightException("Item definition with ID" + id + " not found.");
            } else {
                ItemDef itemDef = null;
                try {
                    itemDef = (ItemDef) SerializationUtils.deserialize(value);
                } catch (Exception ex) {
                    throw ex;
                }

                if (itemDef != null) {
                    return itemDef;
                }
            }

            throw new LightException("Item definition not found");
        };
    }

}
