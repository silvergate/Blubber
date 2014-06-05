package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.abstr.implementables.IBinaryValueSvc;
import com.dcrux.blubber.abstr.implementables.IDefaultsSvc;
import com.dcrux.blubber.abstr.implementables.IIncubationHeaderSvc;
import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.*;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.item.ItemId;
import com.google.inject.Inject;
import org.apache.commons.lang3.SerializationException;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by caelis on 21/05/14.
 */
public class IncubationHeaderSvc implements IIncubationHeaderSvc {

    private final Logger LOG = Logger.getLogger(IncubationHeaderSvc.class.getName());

    @Inject
    private INamesSvc namesProvider;
    @Inject
    private IDefaultsSvc defaultsProvider;
    @Inject
    private IBinaryValueSvc binaryValueProvider;
    @Inject
    private IKvCreateValue kvCreateValue;
    @Inject
    private IKvIndexValue kvIndexValue;
    @Inject
    private IKvDeleteValue kvDeleteValue;
    @Inject
    private IKvGetValue kvGetValue;
    @Inject
    private IKvRemoveAllIndex kvRemoveAllIndex;

    @Override
    public ICallable<IncubationItemHeader> getHeader(ItemId itemId) {
        return executor -> {
            final byte[] bucketName = namesProvider.getDataBucket();
            final byte[] key = itemId.getId();
            byte[] value = executor.call(kvGetValue.getValue(bucketName, key));
            if (value == null) {
                throw new LightException("Key " + itemId + " not found.");
            } else {
                try {
                    return IncubationItemHeader.deserialize(value);
                } catch (ClassCastException | SerializationException cce) {
                    LOG.info("Incubation header deserializable. Trying to access a live header?");
                    throw cce;
                }
            }
        };
    }

    @Override
    public ICallable<ItemId> addHeaderAndExpiration(IncubationItemHeader header) {
        return executor -> {
            final byte[] bucketName = namesProvider.getDataBucket();
            final long expirationTime = header.getIncubationTime() + executor.call(defaultsProvider.getDefaultExpirationPlusMs());
            final byte[] data = header.serialize();
            final byte[] expirationIndex = namesProvider.getExpirationIndex();
            final byte[] expirationTimeBytes = executor.call(this.binaryValueProvider.fromLong(expirationTime));

            byte[] createdKey = executor.call(kvCreateValue.createValue(bucketName, data));
            /* Add index */
            try {
                executor.call(kvIndexValue.addIndex(bucketName, createdKey, expirationIndex, expirationTimeBytes));
                return new ItemId(createdKey);
            } catch (Exception ex) {
                LOG.info("Could create the key but failed to index.");
                executor.call(kvDeleteValue.deleteValue(bucketName, createdKey));
                throw ex;
            }
        };
    }

    @Override
    public ICallable<Void> removeExpirationFromIndex(ItemId itemId) {
        return executor -> {
            final byte[] bucketName = namesProvider.getDataBucket();
            final byte[] expirationIndex = namesProvider.getExpirationIndex();
            final byte[] key = itemId.getId();
            executor.call(kvRemoveAllIndex.removeAllIndex(bucketName, key, Optional.of(expirationIndex)));
            return null;
        };
    }
}
