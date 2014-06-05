package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IDefinitionStorageSvc;
import com.dcrux.blubber.abstr.implementables.INamesSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.*;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import com.google.inject.Inject;
import org.apache.commons.lang3.SerializationUtils;

import java.util.logging.Logger;

/**
 * Created by caelis on 25/05/14.
 */
public class DefinitionStorageSvc implements IDefinitionStorageSvc {

    private static final Logger LOG = Logger.getLogger(DefinitionStorageSvc.class.getName());

    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IKvQueryIndex kvQueryIndex;
    @Inject
    private IKvCreateValue kvCreateValue;
    @Inject
    private IKvIndexValue kvIndexValue;
    @Inject
    private IKvDeleteValue kvDeleteValue;

    private ICallable<ItemDefId> setDefinitionNotYetStored(ItemDefHash hash, ItemDef def) {
        return executor -> {
            final byte[] bucket = namesSvc.getDefinitionBucket();
            final byte[] index = namesSvc.getDefinitionHashIndex();
            final byte[] value = SerializationUtils.serialize(def);
            final byte[] indexValue = hash.getHash();

            byte[] key = executor.call(kvCreateValue.createValue(bucket, value));
            try {
                executor.call(kvIndexValue.addIndex(bucket, key, index, indexValue));
                return new ItemDefId(key);
            } catch (Exception ex) {
                /* Could add the item but not index. Rollback. */
                executor.call(kvDeleteValue.deleteValue(bucket, key));
                throw ex;
            }

        };
    }

    @Override
    public ICallable<ItemDefId> setDefinition(ItemDefHash hash, ItemDef def) {
        return executor -> {
            byte[] bucket = this.namesSvc.getDefinitionBucket();
            byte[] index = this.namesSvc.getDefinitionHashIndex();

        /* Check if we already have the item */
            QueryResult result = executor.call(kvQueryIndex.query(bucket, index, hash.getHash(), hash.getHash(), true, true, 1));
            if (result.getKeys().size() == 0) {
                /* Not stored, store it now */
                return executor.call(setDefinitionNotYetStored(hash, def));
            } else {
                if (result.isHasMoreResults()) {
                    LOG.warning("Item definition is stored more than once");
                }
                return new ItemDefId(result.getKeys().get(0));
            }
        };
    }
}
