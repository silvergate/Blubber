package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.LiveItemHeader;
import com.dcrux.blubber.abstr.implementables.IGetLiveHeaderSvc;
import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvGetValue;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.item.ItemId;
import com.google.inject.Inject;
import org.apache.commons.lang3.SerializationException;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by caelis on 30/05/14.
 */
public class GetLiveHeaderSvc implements IGetLiveHeaderSvc {

    private final Logger LOG = Logger.getLogger(GetLiveHeaderSvc.class.getName());

    @Inject
    private INamesSvc namesProvider;
    @Inject
    private IKvGetValue kvGetValue;

    @Override
    public ICallable<Optional<LiveItemHeader>> getLiveHeader(ItemId itemId) {
        return executor -> {
            final byte[] bucketName = namesProvider.getDataBucket();
            final byte[] key = itemId.getId();
            byte[] value = executor.call(kvGetValue.getValue(bucketName, key));
            if (value == null) {
                    /* Not found */
                return Optional.empty();
            } else {
                try {
                    LiveItemHeader liveItemHeader = LiveItemHeader.deserialize(value);
                    return Optional.of(liveItemHeader);
                } catch (ClassCastException | SerializationException cce) {
                    LOG.info("Tried to access an item that's not been committed");
                    return Optional.empty();
                }
            }

        };
    }

    @Override
    public ICallable<LiveItemHeader> getLiveHeaderRequired(ItemId itemId) {
        return executor -> {
            Optional<LiveItemHeader> header = executor.call(getLiveHeader(itemId));
            if (header.isPresent()) {
                return header.get();
            } else {
                throw new LightException("Item with id not found or has not been committed: " + itemId);
            }
        };
    }
}
