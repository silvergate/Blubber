package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.abstr.data.LiveItemHeader;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.item.ItemId;

import java.util.Optional;

/**
 * Created by caelis on 29/05/14.
 */
public interface IGetLiveHeaderSvc {
    ICallable<Optional<LiveItemHeader>> getLiveHeader(ItemId itemId);

    ICallable<LiveItemHeader> getLiveHeaderRequired(ItemId itemId);
}
