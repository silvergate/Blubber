package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IItemDefIdFromHashSvc;
import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvQueryIndex;
import com.dcrux.blubber.abstr.implementables.keyvalue.QueryResult;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import com.google.inject.Inject;

import java.util.Optional;

/**
 * Created by caelis on 25/05/14.
 */
public class ItemDefIdFromHashSvc implements IItemDefIdFromHashSvc {

    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IKvQueryIndex kvQueryIndex;

    @Override
    public ICallable<ItemDefId> getItemDefId(ItemDefHash hash) {
        return executor -> {
            Optional<ItemDefId> reply = executor.call(getIdByHash(hash));
            if (reply.isPresent()) {
                return reply.get();
            } else {
                throw new LightException("Item ID for hash " + hash + " not found.");
            }
        };
    }

    @Override
    public ICallable<Optional<ItemDefId>> getIdByHash(ItemDefHash hash) {
        return executor -> {
            byte[] bucket = this.namesSvc.getDefinitionBucket();
            byte[] index = this.namesSvc.getDefinitionHashIndex();
            final QueryResult queryResult = executor.call(kvQueryIndex.query(bucket, index, hash.getHash(), hash.getHash(), true, true, 1));
            if (queryResult.getKeys().size() == 0) {
                return Optional.empty();
            } else {
                return Optional.of(new ItemDefId(queryResult.getKeys().get(0)));
            }
        };
    }
}
