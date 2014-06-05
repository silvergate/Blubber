package com.dcrux.blubber.abstr.implementables;

import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.security.UserId;

/**
 * Created by caelis on 24/05/14.
 */
public interface IUserSvc {
    ICallable<UserId> getRequestUser();

    ICallable<Void> setRequestUser(UserId user);
}
