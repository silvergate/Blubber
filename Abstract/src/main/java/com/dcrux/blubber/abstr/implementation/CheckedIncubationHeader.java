package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.data.IncubationItemHeader;
import com.dcrux.blubber.abstr.implementables.ICheckedIncubationHeader;
import com.dcrux.blubber.abstr.implementables.IConnectionSvc;
import com.dcrux.blubber.abstr.implementables.IIncubationHeaderSvc;
import com.dcrux.blubber.abstr.implementables.IUserSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.common.implementables.IMVal2;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.security.UserId;
import com.google.inject.Inject;

import java.util.Arrays;

/**
 * Created by caelis on 24/05/14.
 */
public class CheckedIncubationHeader implements ICheckedIncubationHeader {

    @Inject
    private IUserSvc userSvc;
    @Inject
    private IConnectionSvc connectionSvc;
    @Inject
    private IIncubationHeaderSvc incubationHeaderSvc;

    @Override
    public ICallable<IncubationItemHeader> getHeaderChecked(ItemId itemId) {
        return executor -> {
            IMVal2<IncubationItemHeader, byte[]> multi = executor.call(incubationHeaderSvc.getHeader(itemId), connectionSvc.getConnectionId());

            final IncubationItemHeader header = multi.get1();
            final byte[] connectionId = multi.get2();

            if (!Arrays.equals(connectionId, header.getConnectionId())) {
                throw new LightException("Different connection IDs. Can only modify incubation data from same connection.");
            } else {
                final UserId user = executor.call(userSvc.getRequestUser());
                if (!user.getId().equals(header.getUserId())) {
                    throw new LightException("Wrong user. A incubation item belongs to one single user.");
                } else {
                    return header;
                }
            }
        };
    }
}
