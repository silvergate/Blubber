package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.abstr.data.LiveItemHeader;
import com.dcrux.blubber.abstr.implementables.*;
import com.dcrux.blubber.abstr.implementables.keyvalue.IKvSetValue;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;
import com.google.inject.Inject;

/**
 * Created by caelis on 24/05/14.
 */
public class CommitSvc implements ICommitSvc {

    @Inject
    private ICheckedIncubationHeader checkedIncubationHeader;
    @Inject
    private ITimeSvc timeSvc;
    @Inject
    private ICommitMetaIndexSvc commitMetaIndexSvc;
    @Inject
    private IKvSetValue kvSetValueSvc;
    @Inject
    private INamesSvc namesSvc;
    @Inject
    private IItemCleanup itemCleanup;
    @Inject
    private IUnleafeSvc unleafeSvc;

    private LiveItemHeader createLiveItemHeader(IncubationItemHeader incubationItemHeader) {
        LiveItemHeader header = new LiveItemHeader();
        header.setCommitTime(this.timeSvc.getCurrentTime());
        header.setUserId(incubationItemHeader.getUserId());
        header.setDomainId(incubationItemHeader.getDomainId());
        header.setRootItemId(incubationItemHeader.getRootItemId());
        header.setItemDefId(incubationItemHeader.getItemDefId());
        header.setVersion(incubationItemHeader.getVersion());
        header.setPredecessor(incubationItemHeader.getPredecessor());
        return header;
    }

    private ICallable<Void> commitWithHeader(ItemId itemId, IncubationItemHeader header) {
        return executor -> {
            final LiveItemHeader liveItemHeader = createLiveItemHeader(header);
            final byte[] bucketName = namesSvc.getDataBucket();
            final byte[] key = itemId.getId();
            final byte[] data = liveItemHeader.serialize();
            final ItemId root = header.getRootItemId() == null ? itemId : header.getRootItemId();

            executor.call(kvSetValueSvc.setValue(bucketName, key, data, true));
            executor.call(commitMetaIndexSvc.commitMetaIndex(itemId, liveItemHeader));
            if (header.getPredecessor() == null) {
                return null;
            } else {
                try {
                    executor.call(unleafeSvc.unleafe(root, new ItemId(header.getPredecessor())));
                    return null;
                } catch (Exception ex) {
                    /* Ok, we have a problem */
                    executor.call(itemCleanup.extinguishItem(itemId));
                    throw ex;
                }
            }
        };
    }

    @Override
    public ICallable<Void> commit(ItemId itemId) {
        return executor -> {
            IncubationItemHeader incubationItemHeader = executor.call(checkedIncubationHeader.getHeaderChecked(itemId));
            executor.call(commitWithHeader(itemId, incubationItemHeader));
            return null;
        };
    }
}
