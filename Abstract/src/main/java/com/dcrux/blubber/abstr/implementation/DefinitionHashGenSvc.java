package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IDefinitionHashGenSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import org.apache.commons.lang3.SerializationUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by caelis on 25/05/14.
 */
public class DefinitionHashGenSvc implements IDefinitionHashGenSvc {
    @Override
    public ICallable<ItemDefHash> getHash(ItemDef itemDef) {
        return executor -> {
            //TODO: Need something non-java-specific here
            byte[] serialized = SerializationUtils.serialize(itemDef);

            try {
                final MessageDigest md = MessageDigest.getInstance("SHA-256");
                final byte[] hash = md.digest(serialized);
                return new ItemDefHash(hash);

            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
        };
    }
}
