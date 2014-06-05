package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.abstr.implementables.*;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.common.implementables.IExecutor;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import com.dcrux.blubber.iface.itemdef.ItemDefIdOrHash;
import com.dcrux.blubber.iface.security.DomainId;
import com.dcrux.blubber.iface.security.UserId;
import com.google.inject.Inject;

/**
 * Created by caelis on 23/05/14.
 */
public class CreateItemSvc implements ICreateItemSvc {

    @Inject
    private ITimeSvc timeProvider;
    @Inject
    private IConnectionSvc connectionIdProvider;
    @Inject
    private IIncubationHeaderSvc incubationHeaderProvider;
    @Inject
    private IUserSvc userSvc;
    @Inject
    private IItemDefIdFromHashSvc itemFromHashSvc;

    private IncubationItemHeader createHeader(IExecutor executor, final UserId userIdId, final ItemDefId itemDefId, DomainId domainId) throws Exception {
        IncubationItemHeader incubationItemHeader = new IncubationItemHeader();
        incubationItemHeader.setDomainId(domainId);
        incubationItemHeader.setVersion(0);
        incubationItemHeader.setIncubationTime(timeProvider.getCurrentTime());
        incubationItemHeader.setItemDefId(itemDefId);
        incubationItemHeader.setRootItemId(null);
        incubationItemHeader.setUserId(userIdId.getId());
        incubationItemHeader.setConnectionId(executor.call(this.connectionIdProvider.getConnectionId()));
        return incubationItemHeader;
    }

    private ICallable<ItemId> withUserIdAndDefId(final UserId userIdId, final ItemDefId itemDefId, DomainId domainId) {
        return executor -> {
            final IncubationItemHeader header = createHeader(executor, userIdId, itemDefId, domainId);
            return executor.call(incubationHeaderProvider.addHeaderAndExpiration(header));
        };
    }

    private ICallable<ItemId> withUserId(ItemDefIdOrHash itemDefId, DomainId domainId, final UserId userIdId) {
        return executor -> {
            final ItemDefId finalItemDefId;
            switch (itemDefId.getType()) {
                case hash:
                    finalItemDefId = executor.call(itemFromHashSvc.getItemDefId(itemDefId.getHash()));
                    break;
                case id:
                    finalItemDefId = itemDefId.getId();
                    break;
                default:
                    throw new IllegalStateException("Unknown definition id type");
            }
            return executor.call(withUserIdAndDefId(userIdId, finalItemDefId, domainId));
        };
    }

    private ICallable<ItemId> start(ItemDefIdOrHash itemDefId, DomainId domainId) {
        return new ICallable<ItemId>() {
            @Override
            public ItemId call(IExecutor executor) throws Exception {
                UserId userId = executor.call(userSvc.getRequestUser());
                return executor.call(withUserId(itemDefId, domainId, userId));
            }
        };
    }

    @Override
    public ICallable<ItemId> createItem(ItemDefIdOrHash itemDefId, DomainId domainId) {
        return start(itemDefId, domainId);
    }
}
